import static java.lang.Math.floor;

public class Intersection extends Shader{

    public Intersection(int r, int g, int b) {
        super(r, g, b);
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public void render() {
        // Simply set the color to be the object's shader color
        // we'll see it in its color
        r = (int) floor(r * 255);
        g = (int) floor(g * 255);
        b = (int) floor(b * 255);
    }
}
