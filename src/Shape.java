public abstract class Shape extends Position {


    float t = Float.MAX_VALUE;
    Shader shader;

    public Shape(Position p, Shader s) {
        this.posX = p.posX;
        this.posY = p.posY;
        this.posZ = p.posZ;

        s.r = 0.0f;
        s.g = 0.0f;
        s.b = 0.0f;

        this.shader = s;
    }

    public void render() {

    }
}