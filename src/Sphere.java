public class Sphere extends Shape{

    float radius;

    float posX;
    float posY;
    float posZ;

    Shader shader;

    public Sphere(float posX, float posY, float posZ, float radius, Shader shader){
        super(shader);

        this.radius = radius;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.shader = shader;
    }

    @Override
    public void intersect(Ray ray) {

        float x = posX;
        float y = posY;
        float z = posZ;

        // solve quadratic equation

        float rayDir_dot_rayDir = ray.dirX * ray.dirX + ray.dirY * ray.dirY + ray.dirZ * ray.dirZ;
        float cameraPos_minus_Center_X = ray.posX - x;
        float cameraPos_minus_Center_Y = ray.posY - y;
        float cameraPos_minus_Center_Z = ray.posZ - z;

        float B = ray.dirX * cameraPos_minus_Center_X + ray.dirY * cameraPos_minus_Center_Y + ray.dirZ * cameraPos_minus_Center_Z;

        float cameraSphere_dotProduct = cameraPos_minus_Center_X * cameraPos_minus_Center_X + cameraPos_minus_Center_Y * cameraPos_minus_Center_Y + cameraPos_minus_Center_Z * cameraPos_minus_Center_Z;

        float discriminant = B * B - rayDir_dot_rayDir * (cameraSphere_dotProduct - radius * radius);
        if (discriminant >= 0.0f) {//idk why but discriminant is always negative which is preventing the ray from hitting the object
            float sqrtDiscr = (float) Math.sqrt(discriminant);
            float t1 = (-B + sqrtDiscr) / rayDir_dot_rayDir;
            float t2 = (-B - sqrtDiscr) / rayDir_dot_rayDir;

            float minT = Math.min(t1, t2);
            float maxT = Math.max(t1, t2);
            float closestT = (minT >= 1.0f) ? minT : (maxT >= 1.0f ? maxT : Float.MAX_VALUE);

            if (closestT < ray.t) {
                //System.out.println("test");
                ray.t = closestT;
                ray.rayHitAnObject = true;

                ray.intersect_PointX = ray.posX + t * ray.dirX;
                ray.intersect_PointY = ray.posY + t * ray.dirY;
                ray.intersect_PointZ = ray.posZ + t * ray.dirZ;

                ray.intersect_NormalX = ray.intersect_PointX - x;
                ray.intersect_NormalY = ray.intersect_PointY - y;
                ray.intersect_NormalZ = ray.intersect_PointZ - z;
                float vectorLength = (float) Math.sqrt(ray.intersect_NormalX * ray.intersect_NormalX +
                        ray.intersect_NormalY * ray.intersect_NormalY +
                        ray.intersect_NormalZ * ray.intersect_NormalZ);
                ray.intersect_NormalX /= vectorLength;
                ray.intersect_NormalY /= vectorLength;
                ray.intersect_NormalZ /= vectorLength;
            }
        }

    }
}
