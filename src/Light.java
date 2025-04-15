public class Light extends Position {

    float colorR, colorG, colorB;

    public Light(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        colorR = 1.0f;
        colorG = 1.0f;
        colorB = 1.0f;
    }
}
