import java.util.ArrayList;

public class Perspective extends Camera{

    PNGImageData image;
    Ray ray = new Ray();
    ArrayList<Shape> shapes = new ArrayList<>();

    public Perspective(PNGImageData image, ArrayList<Shape> shapes) {
        super(image, shapes);
        this.image = image;
        this.shapes = shapes;
    }
    
    @Override
    public void takeSnapshot() {
        for (int row = 0; row < image.getImageHeight(); row++) {
            for (int col = 0; col < image.getImageWidth(); col++) {

                //camera type specific code-------------------------------
                focalLength = 0.75f;

                imageplane_height = 1.0f;
                imageplane_width = imageplane_height * aspectRatio;

                float l = -imageplane_width / 2.0f;
                float r = imageplane_width / 2.0f;
                float b = -imageplane_height / 2.0f;
                float t = imageplane_height / 2.0f;

                float u = l + (r - l) * col / (float) image.getImageWidth();
                float v = b + (t - b) * row / (float) image.getImageHeight();

                ray.posX = posX;
                ray.posY = posY;
                ray.posZ = posZ;

                ray.dirX = -focalLength * dir_W_x + u * dir_U_x + v * dir_V_x;
                ray.dirY = -focalLength * dir_W_y + u * dir_U_y + v * dir_V_y;
                ray.dirZ = -focalLength * dir_W_z + u * dir_U_z + v * dir_V_z;

                //-----------------------------------------------------------

                // walk over all of the object types and determine
                // if any object is hit by the ray that we just generated
                // if so, we'll need to know where it hit and the surface information (normal)
                // at that point
                ray.intersect_NormalX = 0.0f;
                ray.intersect_NormalY = 0.0f;
                ray.intersect_NormalZ = 0.0f;

                ray.intersect_PointX = 0.0f;
                ray.intersect_PointY = 0.0f;
                ray.intersect_PointZ = 0.0f;

                t = Float.MAX_VALUE;

                for (int idx = 0; idx < shapes.size(); ++idx) {
                    shapes.get(idx).intersect(ray, t);
                    if(t > shapes.get(idx).getT()) {
                        t = shapes.get(idx).getT();
                        closest = shapes.get(idx);
                    }

                }

                int red = 255;
                int green = 255;
                int blue = 255;

                if (ray.rayHitAnObject && t > 1.0f) {
                    closest.shader.render();
                    red = closest.shader.getRed();
                    green = closest.shader.getGreen();
                    blue = closest.shader.getBlue();
                }
                else {
                    // no objects were intersected with our viewing ray so set
                    // the pixel to the background color
                    // something like sky-blue like
                    red = 207;
                    green = 236;
                    blue = 247;
                }
                red = Math.clamp(red, 0, 255);
                green = Math.clamp(green, 0, 255);
                blue = Math.clamp(blue, 0, 255);

                image.setPixel(col, (image.getImageHeight() - 1) - row, red, green, blue);
            }
        }
        image.writeData("raytrace_Image.png");
    }
}
