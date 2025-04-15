public abstract class Shape {
    Float posX, posY, posZ;

    String shaderType;
    Float shaderR, shaderG, shaderB;

    public Shape() {
        posX = 0.0f;
        posY = 0.0f;
        posZ = 0.0f;

        shaderType = "Normal Shader";
        shaderR = 0.0f;
        shaderG = 0.0f;
        shaderB = 0.0f;
    }
}
