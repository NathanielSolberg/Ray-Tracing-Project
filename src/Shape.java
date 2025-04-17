public abstract class Shape extends Position{

    String shaderType;

    Shader shader;

    float t;

    public Shape(Shader shader) {
        this.shader = shader;
    }

    public void intersect(Ray ray) {

    }

    public void resetT() { t = Float.MAX_VALUE; }
    public float getT() {
        return t;
    }

}
