import static java.lang.Math.floor;

public class Normal extends Shader {


    public Normal(float r, float g, float b) {
        super(new Color());
        super.r = r;
        super.g = g;
        super.b = b;
    }

    @Override
    public void render() {
        // convert the normal vector into a color - normal needs to be normalized before this step
        float normal_ColorRed = (intersect_NormalX + 1.0f) / 2.0f;
        float normal_ColorGreen = (intersect_NormalY + 1.0f) / 2.0f;
        float normal_ColorBlue = (intersect_NormalZ + 1.0f) / 2.0f;

        red = (int) floor(normal_ColorRed * 255);
        green = (int) floor(normal_ColorGreen * 255);
        blue = (int) floor(normal_ColorBlue * 255);
    }

}
