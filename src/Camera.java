public class Camera {

    float myCamera_focalLength;
    float myCamera_PosX;
    float myCamera_PosY;
    float myCamera_PosZ;
    // the following are the camera's 3D coordinate orientation
    // represented by U, V and W vectors
    float myCamera_U_x;
    float myCamera_U_y;
    float myCamera_U_z;
    float myCamera_V_x;
    float myCamera_V_y;
    float myCamera_V_z;
    float myCamera_W_x;
    float myCamera_W_y;
    float myCamera_W_z;

    // set the default parameters for the image plan size
    // this is like the "film" of the camera
    float imageplane_height;
    float imageplane_width;

    String myCamera_type;

    public Camera() {
        myCamera_focalLength = 1.0f;
        myCamera_PosX = 0.0f;
        myCamera_PosY = 0.0f;
        myCamera_PosZ = 0.0f;
        myCamera_U_x = 1.0f;
        myCamera_U_y = 0.0f;
        myCamera_U_z = 0.0f;
        myCamera_V_x = 0.0f;
        myCamera_V_y = 1.0f;
        myCamera_V_z = 0.0f;
        myCamera_W_x = 0.0f;
        myCamera_W_y = 0.0f;
        myCamera_W_z = 1.0f;

        myCamera_type = "Perspective";

        // set the default parameters for the image plan size
        // this is like the "film" of the camera
        imageplane_height = 1.0f;
        imageplane_width = 1.0f;
    }
}
