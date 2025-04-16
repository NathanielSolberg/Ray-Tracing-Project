public class Ray extends Position{
    float dirX = 0.0f;
    float dirY = 0.0f;
    float dirZ = 0.0f;

    float intersect_NormalX = 0.0f;
    float intersect_NormalY = 0.0f;
    float intersect_NormalZ = 0.0f;

    float intersect_PointX = 0.0f;
    float intersect_PointY = 0.0f;
    float intersect_PointZ = 0.0f;

    boolean rayHitAnObject = false;

    float t = Float.MAX_VALUE;
}
