import java.util.ArrayList;

import static java.lang.Math.floor;

public class Perspective extends Camera{

    public Perspective(PNGImageData image, ArrayList<Shape> shapes,
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

        // Why repeat what the super class is already doing for you?
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        // Why repeat what the super class is already doing for you?
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

                // this is a check for each ray across
                // all shapes
                ray.t = Float.MAX_VALUE;

                // reset this variable...
                ray.rayHitAnObject = false;

                // the "t" you have below is confused with the "t" in the camera (l, r, b, t).

                float currentClosest_t = Float.MAX_VALUE;
                closest = null;


                for (int idx = 0; idx < shapes.size(); ++idx) {
                    shapes.get(idx).intersect(ray);
                    if(currentClosest_t > shapes.get(idx).getT()) {
                        //System.out.println("test");
                        currentClosest_t = ray.t;
                        closest = shapes.get(idx);
                    }

                }

                // t = closest.getT();

                int red = 255;
                int green = 255;
                int blue = 255;
                //System.out.println(t);
                if (ray.rayHitAnObject && currentClosest_t > 1.0f) {
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
