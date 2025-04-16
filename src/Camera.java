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

    public Camera(PNGImageData image, ArrayList<Shape> shapes) {
        focalLength = 1.0f;
        posX = 0.0f;
        posY = 0.0f;
        posZ = 0.0f;
        
        dir_U_x = 1.0f;
        dir_U_y = 0.0f;
        dir_U_z = 0.0f;
        dir_V_x = 0.0f;
        dir_V_y = 1.0f;
        dir_V_z = 0.0f;
        dir_W_x = 0.0f;
        dir_W_y = 0.0f;
        dir_W_z = 1.0f;

        myCamera_type = "Perspective";

        aspectRatio = image.getImageWidth() / (float) image.getImageHeight();

    }

    public void takeSnapshot() {

    }
}
