import java.util.ArrayList;

public class Orthographic extends Camera{

    public Orthographic(PNGImageData image, ArrayList<Shape> shapes,
                       float posX,
                       float posY,
                       float posZ,
                       float dir_U_x,
                       float dir_U_y,
                       float dir_U_z,
                       float dir_V_x,
                       float dir_V_y,
                       float dir_V_z,
                       float dir_W_x,
                       float dir_W_y,
                       float dir_W_z) {
        super(image, shapes, posX, posY, posZ,
                dir_U_x,
                dir_U_y,
                dir_U_z,
                dir_V_x,
                dir_V_y,
                dir_V_z,
                dir_W_x,
                dir_W_y,
                dir_W_z);
        this.image = image;
        this.shapes = shapes;

        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.dir_U_x = dir_U_x;
        this.dir_U_y = dir_U_y;
        this.dir_U_z = dir_U_z;
        this.dir_V_x = dir_V_x;
        this.dir_V_y = dir_V_y;
        this.dir_V_z = dir_V_z;
        this.dir_W_x = dir_W_x;
        this.dir_W_y = dir_W_y;
        this.dir_W_z = dir_W_z;
    }

    @Override
    public void takeSnapshot() {
        for (int row = 0; row < image.getImageHeight(); row++) {
            for (int col = 0; col < image.getImageWidth(); col++) {

                imageplane_height = 8.0f;
                imageplane_width = imageplane_height * aspectRatio;

                float l = -imageplane_width / 2.0f;
                float r = imageplane_width / 2.0f;
                float b = -imageplane_height / 2.0f;
                float t = imageplane_height / 2.0f;

                float u = l + (r - l) * col / (float) image.getImageWidth();
                float v = b + (t - b) * row / (float) image.getImageHeight();

                ray.dirX = -dir_W_x;
                ray.dirY = -dir_W_y;
                ray.dirZ = -dir_W_z;

                ray.posX = posX + u * dir_U_x + v * dir_V_x;
                ray.posY = posY + u * dir_U_y + v * dir_V_y;
                ray.posZ = posZ + u * dir_U_z + v * dir_V_z;

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
                    shapes.get(idx).intersect(ray);
                    if(t > shapes.get(idx).getT()) {
                        t = shapes.get(idx).getT();
                        closest = shapes.get(idx);
                    }

                }

                int red = 255;
                int green = 255;
                int blue = 255;

                if (ray.rayHitAnObject && t > 1.0f && closest != null) {
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
