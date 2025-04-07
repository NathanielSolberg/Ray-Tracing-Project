import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Main {
    public static void main(String[] args) {

        //
        PNGImageData image = new PNGImageData(800, 500);

        Camera camera = new Camera();

        float aspectRatio = image.getImageWidth() / (float) image.getImageHeight();

        ArrayList<Light> light = new ArrayList<>();

        // create my objects using some weirdo set of arraylists to hold
        // all of the data - this is really not a great representation for
        // this data but oh well... I don't know better
        ArrayList<String> object_Type = new ArrayList<>();
        ArrayList<Float> object_PosX = new ArrayList<>();
        ArrayList<Float> object_PosY = new ArrayList<>();
        ArrayList<Float> object_PosZ = new ArrayList<>();
        ArrayList<Float> object_Dim1 = new ArrayList<>();
        ArrayList<String> object_ShaderType = new ArrayList<>();
        ArrayList<Float> object_ShaderMaterialR = new ArrayList<>();
        ArrayList<Float> object_ShaderMaterialG = new ArrayList<>();
        ArrayList<Float> object_ShaderMaterialB = new ArrayList<>();

        Sphere sphere = new Sphere();

        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(sphere);

        // Make a sphere with radius 1.0 at position 0.0, 0.0, -5.0
        object_Type.add(new String("Sphere"));
        object_PosX.add(0.0f);
        object_PosY.add(0.0f);
        object_PosZ.add(-5.0f);
        object_Dim1.add(1.0f);
        object_ShaderType.add(new String("Normal Shader"));
        object_ShaderMaterialR.add(-1.0f); // these don't mean anything to the normal shader
        object_ShaderMaterialG.add(-1.0f); // but due to poor representing of data
        object_ShaderMaterialB.add(-1.0f); // we have to put in stub values... :(

        // Make another sphere
        object_Type.add(new String("Sphere"));
        object_PosX.add(-2.0f);
        object_PosY.add(1.0f);
        object_PosZ.add(-10.0f);
        object_Dim1.add(3.0f);
        object_ShaderType.add(new String("Lambertian Shader"));
        object_ShaderMaterialR.add(0.4235f);
        object_ShaderMaterialG.add(0.2314f);
        object_ShaderMaterialB.add(0.6667f);

        // Make another sphere
        object_Type.add(new String("Sphere"));
        object_PosX.add(1.0f);
        object_PosY.add(-1.0f);
        object_PosZ.add(-4.0f);
        object_Dim1.add(0.5f);
        object_ShaderType.add(new String("Lambertian Shader"));
        object_ShaderMaterialR.add(1.0f);
        object_ShaderMaterialG.add(0.5490f);
        object_ShaderMaterialB.add(0.0f);

        // Make another sphere without a shader we've implemented yet
        object_Type.add(new String("Sphere"));
        object_PosX.add(-3.0f);
        object_PosY.add(-3.0f);
        object_PosZ.add(-10.0f);
        object_Dim1.add(0.5f);
        object_ShaderType.add(new String("Blinn-Phong Shader"));
        object_ShaderMaterialR.add(0.0f);
        object_ShaderMaterialG.add(0.0f);
        object_ShaderMaterialB.add(1.0f);

        //
        // finally, the main loop of this program, which is
        // to generate the image
        //
        for (int row = 0; row < image.getImageHeight(); row++) {
            for (int col = 0; col < image.getImageWidth(); col++) {

                float ray_PosX = 0.0f;
                float ray_PosY = 0.0f;
                float ray_PosZ = 0.0f;

                float ray_DirX = 0.0f;
                float ray_DirY = 0.0f;
                float ray_DirZ = 0.0f;

                float ray_t = 0.0f;

                if (camera.myCamera_type == "Perspective") {

                    camera.myCamera_focalLength = 0.75f;

                    camera.imageplane_height = 1.0f;
                    camera.imageplane_width = camera.imageplane_height * aspectRatio;

                    float l = -camera.imageplane_width / 2.0f;
                    float r = camera.imageplane_width / 2.0f;
                    float b = -camera.imageplane_height / 2.0f;
                    float t = camera.imageplane_height / 2.0f;

                    float u = l + (r - l) * col / (float) image.getImageWidth();
                    float v = b + (t - b) * row / (float) image.getImageHeight();

                    ray_PosX = camera.myCamera_PosX;
                    ray_PosY = camera.myCamera_PosY;
                    ray_PosZ = camera.myCamera_PosZ;

                    ray_DirX = -camera.myCamera_focalLength * camera.myCamera_W_x + u * camera.myCamera_U_x + v * camera.myCamera_V_x;
                    ray_DirY = -camera.myCamera_focalLength * camera.myCamera_W_y + u * camera.myCamera_U_y + v * camera.myCamera_V_y;
                    ray_DirZ = -camera.myCamera_focalLength * camera.myCamera_W_z + u * camera.myCamera_U_z + v * camera.myCamera_V_z;

                } else if (camera.myCamera_type == "Orthographic") {

                    camera.imageplane_height = 8.0f;
                    camera.imageplane_width = camera.imageplane_height * aspectRatio;

                    float l = -camera.imageplane_width / 2.0f;
                    float r = camera.imageplane_width / 2.0f;
                    float b = -camera.imageplane_height / 2.0f;
                    float t = camera.imageplane_height / 2.0f;

                    float u = l + (r - l) * col / (float) image.getImageWidth();
                    float v = b + (t - b) * row / (float) image.getImageHeight();

                    ray_DirX = -camera.myCamera_W_x;
                    ray_DirY = -camera.myCamera_W_y;
                    ray_DirZ = -camera.myCamera_W_z;

                    ray_PosX = camera.myCamera_PosX + u * camera.myCamera_U_x + v * camera.myCamera_V_x;
                    ray_PosY = camera.myCamera_PosY + u * camera.myCamera_U_y + v * camera.myCamera_V_y;
                    ray_PosZ = camera.myCamera_PosZ + u * camera.myCamera_U_z + v * camera.myCamera_V_z;
                }

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

                // we assume we don't hit an object when we enter into the
                // next code set, so reset the information before we check
                // the objects
                boolean rayHitAnObject = false;
                float t = Float.MAX_VALUE;
                int closestObjectIdx = -1;

                for (int idx = 0; idx < shapes.size(); ++idx) {

                    if (shapes.get(idx).equals(sphere)) { //I want to change this to see the object type not that specific object

                        Sphere sphere1 = (Sphere) shapes.get(idx);

                        float x = sphere1.posX;
                        float y = sphere1.posY;
                        float z = sphere1.posZ;
                        float radius = sphere1.radius;

                        // solve quadratic equation

                        float rayDir_dot_rayDir = ray_DirX * ray_DirX + ray_DirY * ray_DirY + ray_DirZ * ray_DirZ;
                        float cameraPos_minus_Center_X = ray_PosX - x;
                        float cameraPos_minus_Center_Y = ray_PosY - y;
                        float cameraPos_minus_Center_Z = ray_PosZ - z;

                        float B = ray_DirX * cameraPos_minus_Center_X + ray_DirY * cameraPos_minus_Center_Y + ray_DirZ * cameraPos_minus_Center_Z;

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
                                rayHitAnObject = true;
                                closestObjectIdx = idx;

                                intersect_PointX = ray_PosX + t * ray_DirX;
                                intersect_PointY = ray_PosY + t * ray_DirY;
                                intersect_PointZ = ray_PosZ + t * ray_DirZ;

                                intersect_NormalX = intersect_PointX - x;
                                intersect_NormalY = intersect_PointY - y;
                                intersect_NormalZ = intersect_PointZ - z;
                                float vectorLength = (float) Math.sqrt(intersect_NormalX * intersect_NormalX +
                                        intersect_NormalY * intersect_NormalY +
                                        intersect_NormalZ * intersect_NormalZ);
                                intersect_NormalX /= vectorLength;
                                intersect_NormalY /= vectorLength;
                                intersect_NormalZ /= vectorLength;
                            }
                        }

                    } else if (object_Type.get(idx) == "Triangle") {
                        // process triangle ray intersection
                    } else if (object_Type.get(idx) == "Plane") {
                        // if we had a plane we could do that here..
                    }
                }

                int red = 255;
                int green = 255;
                int blue = 255;

                if (rayHitAnObject && t > 1.0f && closestObjectIdx != -1) {

                    if (object_ShaderType.get(closestObjectIdx).equals("Intersection Shader")) {
                        // Simply set the color to be the object's shader color
                        // we'll see it in its color
                        red = (int) floor(object_ShaderMaterialR.get(closestObjectIdx) * 255);
                        green = (int) floor(object_ShaderMaterialG.get(closestObjectIdx) * 255);
                        blue = (int) floor(object_ShaderMaterialB.get(closestObjectIdx) * 255);
                    } else if (object_ShaderType.get(closestObjectIdx).equals("Normal Shader")) {
                        // convert the normal vector into a color - normal needs to be normalized before this step
                        float normal_ColorRed = (intersect_NormalX + 1.0f) / 2.0f;
                        float normal_ColorGreen = (intersect_NormalY + 1.0f) / 2.0f;
                        float normal_ColorBlue = (intersect_NormalZ + 1.0f) / 2.0f;

                        red = (int) floor(normal_ColorRed * 255);
                        green = (int) floor(normal_ColorGreen * 255);
                        blue = (int) floor(normal_ColorBlue * 255);
                    } else if (object_ShaderType.get(closestObjectIdx).equals("Lambertian Shader")) {
                        // only 1 light for now - if I had lots of lights in that array, I could
                        // loop over them and add their contributions together...
                        float light_DirX = light.get(0).posX - intersect_PointX;
                        float light_DirY = light.get(0).posY - intersect_PointY;
                        float light_DirZ = light.get(0).posZ - intersect_PointZ;
                        float lightDirLength = (float) Math.sqrt(light_DirX * light_DirX + light_DirY * light_DirY + light_DirZ * light_DirZ);
                        light_DirX /= lightDirLength;
                        light_DirY /= lightDirLength;
                        light_DirZ /= lightDirLength;

                        float normalDotLightDir = (float) Math.max(intersect_NormalX * light_DirX + intersect_NormalY * light_DirY + intersect_NormalZ * light_DirZ, 0.0f);

                        float matR = object_ShaderMaterialR.get(closestObjectIdx) * light.get(0).colorR * normalDotLightDir;
                        float matG = object_ShaderMaterialG.get(closestObjectIdx) * light.get(0).colorG * normalDotLightDir;
                        float matB = object_ShaderMaterialB.get(closestObjectIdx) * light.get(0).colorB * normalDotLightDir;

                        red = (int) Math.floor(matR * 255);
                        green = (int) Math.floor(matG * 255);
                        blue = (int) Math.floor(matB * 255);
                    } else if (object_ShaderType.get(closestObjectIdx).equals("Blinn-Phong Shader")) {
                        // only 1 light for now - if I had lots of lights in that array, I could
                        // loop over them and add their contributions together...
                        float light_DirX = light.get(0).posX - intersect_PointX;
                        float light_DirY = light.get(0).posY - intersect_PointY;
                        float light_DirZ = light.get(0).posZ - intersect_PointZ;
                        float lightDirLength = (float) Math.sqrt(light_DirX * light_DirX + light_DirY * light_DirY + light_DirZ * light_DirZ);
                        light_DirX /= lightDirLength;
                        light_DirY /= lightDirLength;
                        light_DirZ /= lightDirLength;

                        float normalDotLightDir = (float) Math.max(intersect_NormalX * light_DirX + intersect_NormalY * light_DirY + intersect_NormalZ * light_DirZ, 0.0f);

                        float diffuseComponent_R = object_ShaderMaterialR.get(closestObjectIdx) * light.get(0).colorR * normalDotLightDir;
                        float diffuseComponent_G = object_ShaderMaterialG.get(closestObjectIdx) * light.get(0).colorG * normalDotLightDir;
                        float diffuseComponent_B = object_ShaderMaterialB.get(closestObjectIdx) * light.get(0).colorB * normalDotLightDir;

                        // acquire specular component of the shading
                        float view_DirX = -1.0f * ray_DirX;
                        float view_DirY = -1.0f * ray_DirY;
                        float view_DirZ = -1.0f * ray_DirZ;
                        float viewDirLength = (float) Math.sqrt(view_DirX * view_DirX + view_DirY * view_DirY + view_DirZ * view_DirZ);
                        view_DirX /= viewDirLength;
                        view_DirY /= viewDirLength;
                        view_DirZ /= viewDirLength;

                        float halfVec_DirX = view_DirX + light_DirX;
                        float halfVec_DirY = view_DirY + light_DirY;
                        float halfVec_DirZ = view_DirZ + light_DirZ;
                        float halfVecLength = (float) Math.sqrt(halfVec_DirX * halfVec_DirX + halfVec_DirY * halfVec_DirY + halfVec_DirZ * halfVec_DirZ);
                        halfVec_DirX /= halfVecLength;
                        halfVec_DirY /= halfVecLength;
                        halfVec_DirZ /= halfVecLength;

                        float normalDotHalfVec = (float) Math.max(intersect_NormalX * halfVec_DirX + intersect_NormalY * halfVec_DirY + intersect_NormalZ * halfVec_DirZ, 0.0);

                        float phongExp = 16.0f;
                        float specularComponent_R = (float) Math.pow(normalDotHalfVec, phongExp);
                        float specularComponent_G = (float) Math.pow(normalDotHalfVec, phongExp);
                        float specularComponent_B = (float) Math.pow(normalDotHalfVec, phongExp);

                        float matR = diffuseComponent_R + specularComponent_R;
                        float matG = diffuseComponent_G + specularComponent_G;
                        float matB = diffuseComponent_B + specularComponent_B;

                        red = (int) Math.floor(matR * 255);
                        green = (int) Math.floor(matG * 255);
                        blue = (int) Math.floor(matB * 255);
                    } else {
                        // unknown shader so just set the color to something bright
                        // also means we hit an object so we should color it something
                        // other than the background, like bright pink
                        red = 255;
                        green = 105;
                        blue = 180;
                    }
                }
                else {
                    // no objects were intersected with our viewing ray so set
                    // the pixel to the background color
                    // something like sky-blue like
                    red = 207;
                    green = 236;
                    blue = 247;
                }

                // We can't have values larger than 255 or less than 0 in a
                // PNG image, so clamp these values before we set them
                // in the PNG image
                red = Math.clamp(red, 0, 255);
                green = Math.clamp(green, 0, 255);
                blue = Math.clamp(blue, 0, 255);

                // Set RGB values directly at the column and row
                // Need to flip the height since 0,0 is in upper left of PNG image
                image.setPixel(col, (image.getImageHeight() - 1) - row, red, green, blue);
            }
        }

        image.writeData("raytrace_Image.png");
    }
}
