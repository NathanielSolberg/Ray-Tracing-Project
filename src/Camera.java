import java.util.ArrayList;

public abstract class Camera extends Position{

    float focalLength;

    // set the default parameters for the image plan size
    // this is like the "film" of the camera
    float imageplane_height;
    float imageplane_width;

    String myCamera_type;

    float aspectRatio;

    Shape closest;

    public Camera(PNGImageData image, ArrayList<Shape> shapes,
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

        aspectRatio = image.getImageWidth() / (float) image.getImageHeight();

    }

    public void takeSnapshot() {

    }
}
