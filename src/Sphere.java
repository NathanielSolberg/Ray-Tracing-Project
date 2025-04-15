public class Sphere extends Shape{

    float radius;

    public Sphere(float radius, float posX, float posY, float posZ, Shader shader) {
        super(new Position(),shader);

        super.posX = posX;
        super.posY = posY;
        super.posZ = posZ;

        this.radius = radius;
    }

    @Override
    public void render() {

        // solve quadratic equation

        float rayDir_dot_rayDir = shader.ray_DirX * shader.ray_DirX + shader.ray_DirY * shader.ray_DirY + shader.ray_DirZ * shader.ray_DirZ;
        float cameraPos_minus_Center_X = shader.ray_PosX - posX;
        float cameraPos_minus_Center_Y = shader.ray_PosY - posY;
        float cameraPos_minus_Center_Z = shader.ray_PosZ - posZ;

        float B = shader.ray_DirX * cameraPos_minus_Center_X + shader.ray_DirY * cameraPos_minus_Center_Y + shader.ray_DirZ * cameraPos_minus_Center_Z;

        float cameraSphere_dotProduct = cameraPos_minus_Center_X * cameraPos_minus_Center_X +
                cameraPos_minus_Center_Y * cameraPos_minus_Center_Y +
                cameraPos_minus_Center_Z * cameraPos_minus_Center_Z;

        float discriminant = B * B - rayDir_dot_rayDir * (cameraSphere_dotProduct - radius * radius);
        if (discriminant >= 0.0f) {
            float sqrtDiscr = (float) Math.sqrt(discriminant);
            float t1 = (-B + sqrtDiscr) / rayDir_dot_rayDir;
            float t2 = (-B - sqrtDiscr) / rayDir_dot_rayDir;

            float minT = Math.min(t1, t2);
            float maxT = Math.max(t1, t2);
            float closestT = (minT >= 1.0f) ? minT : (maxT >= 1.0f ? maxT : Float.MAX_VALUE);

            if (closestT < t) {
                t = closestT;
                shader.rayHitAnObject = true;

                shader.intersect_PointX = shader.ray_PosX + t * shader.ray_DirX;
                shader.intersect_PointY = shader.ray_PosY + t * shader.ray_DirY;
                shader.intersect_PointZ = shader.ray_PosZ + t * shader.ray_DirZ;

                shader.intersect_NormalX = shader.intersect_PointX - posX;
                shader.intersect_NormalY = shader.intersect_PointY - posY;
                shader.intersect_NormalZ = shader.intersect_PointZ - posZ;
                float vectorLength = (float) Math.sqrt(shader.intersect_NormalX * shader.intersect_NormalX +
                        shader.intersect_NormalY * shader.intersect_NormalY +
                        shader.intersect_NormalZ * shader.intersect_NormalZ);
                shader.intersect_NormalX /= vectorLength;
                shader.intersect_NormalY /= vectorLength;
                shader.intersect_NormalZ /= vectorLength;
            }
        }
    }

}
