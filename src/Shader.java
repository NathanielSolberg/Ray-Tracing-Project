public abstract class Shader extends Color{

    Color shaderColor = new Color();

    float ray_PosX = 0.0f;
    float ray_PosY = 0.0f;
    float ray_PosZ = 0.0f;

    float ray_DirX = 0.0f;
    float ray_DirY = 0.0f;
    float ray_DirZ = 0.0f;


    // walk over all of the object types and determine
    // if any object is hit by the ray that we just generated
    // if so, we'll need to know where it hit and the surface information (normal)
    // at that point
    float intersect_NormalX = 0.0f;
    float intersect_NormalY = 0.0f;
    float intersect_NormalZ = 0.0f;

    float intersect_PointX = 0.0f;
    float intersect_PointY = 0.0f;
    float intersect_PointZ = 0.0f;

    boolean rayHitAnObject = false;

    int red = 255;
    int green = 255;
    int blue = 255;

    public Shader (Color color) {

    }

    public void render() {

    }
}
