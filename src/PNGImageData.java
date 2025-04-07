import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class PNGImageData {

    private int width;
    private int height;

    private BufferedImage myimage;
    private WritableRaster raster;

    public PNGImageData() {
        this(100,100);
    }

    public PNGImageData( int w, int h ) {
        this.width = w;
        this.height = h;
        myimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        raster = myimage.getRaster();
        // clear to black
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                int red = 0;
                int green = 0;
                int blue = 0;

                // Set RGB values directly at the row and column.
                // We need to provide the raster with a Java array that contains the
                // red, green and blue values at this pixel.
                raster.setPixel(col, row, new int[]{red, green, blue});
            }
        }
    }
    public int getImageWidth() { return width; }
    public int getImageHeight() { return height; }

    public void setPixel( int col, int row, int red, int green, int blue) {
        int[] pixelColor = new int[]{red, green, blue};
        raster.setPixel(col, row, pixelColor);
    }

    public void writeData( String filename ) {
        // Save as a PNG file
        File output = new File(filename);
        try {
            ImageIO.write(myimage, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
