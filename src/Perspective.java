import java.util.ArrayList;

public class Perspective extends Camera{

    float imageplane_height;
    float imageplane_width;
    float aspectRatio;

    PNGImageData image;
    
    
    public Perspective(float imageplane_height, float imageplane_width, PNGImageData image, ArrayList<Shape> shapes) {
        super(imageplane_height, imageplane_width, image, shapes);
        aspectRatio = imageplane_height/imageplane_width;
        this.imageplane_height = imageplane_height;
        this.imageplane_width = imageplane_width;
        this.image = image;
    }

    public void takeSnapShot() {

        for (int row = 0; row < image.getImageHeight(); row++) {
            for (int col = 0; col < image.getImageWidth(); col++) {
                myCamera_focalLength = 0.75f;

                float l = -imageplane_width / 2.0f;
                float r = imageplane_width / 2.0f;
                float b = -imageplane_height / 2.0f;
                float t = imageplane_height / 2.0f;

                float u = l + (r - l) * col / (float) image.getImageWidth();
                float v = b + (t - b) * row / (float) image.getImageHeight();

                ray_PosX = posX;
                ray_PosY = posY;
                ray_PosZ = posZ;

                ray_DirX = -myCamera_focalLength * direction_W_x + u * direction_U_x + v * direction_V_x;
                ray_DirY = -myCamera_focalLength * direction_W_y + u * direction_U_y + v * direction_V_y;
                ray_DirZ = -myCamera_focalLength * direction_W_z + u * direction_U_z + v * direction_V_z;

                render();
            }
        }


    }
}
