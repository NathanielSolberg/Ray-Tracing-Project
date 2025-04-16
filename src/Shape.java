public abstract class Shape extends Position{

    String shaderType;

    Shader shader;

    float t;

    public Shape(Shader shader) {
        this.shader = shader;
    }

    public void intersect(Ray ray) {

    }

    public float getT() {
        return t;
    }

}
