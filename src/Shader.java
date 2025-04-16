public abstract class Shader {

    int r;
    int g;
    int b;

    public Shader (int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void render() {

    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }
}
