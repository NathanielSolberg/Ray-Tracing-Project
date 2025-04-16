import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Main {
    public static void main(String[] args) {


        PNGImageData image = new PNGImageData(800, 500);



        ArrayList<Light> light = new ArrayList<>();

        Intersection shader = new Intersection(100, 100, 100);


        Sphere sphere = new Sphere(0.0f, 0.0f, 0.0f, 1.0f, shader);

        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(sphere);

        Perspective camera = new Perspective(image, shapes);

        camera.takeSnapshot();


        // finally, the main loop of this program, which is
        // to generate the image
        //
//        for (int row = 0; row < image.getImageHeight(); row++) {
//            for (int col = 0; col < image.getImageWidth(); col++) {
//
//                float ray_PosX = 0.0f;
//                float ray_PosY = 0.0f;
//                float ray_PosZ = 0.0f;
//
//                float ray_DirX = 0.0f;
//                float ray_DirY = 0.0f;
//                float ray_DirZ = 0.0f;
//
//                float ray_t = 0.0f;
//
////                if (camera.myCamera_type == "Perspective") {
////
////                    camera.focalLength = 0.75f;
////
////                    camera.imageplane_height = 1.0f;
////                    camera.imageplane_width = camera.imageplane_height * aspectRatio;
////
////                    float l = -camera.imageplane_width / 2.0f;
////                    float r = camera.imageplane_width / 2.0f;
////                    float b = -camera.imageplane_height / 2.0f;
////                    float t = camera.imageplane_height / 2.0f;
////
////                    float u = l + (r - l) * col / (float) image.getImageWidth();
////                    float v = b + (t - b) * row / (float) image.getImageHeight();
////
////                    ray_PosX = camera.posX;
////                    ray_PosY = camera.posY;
////                    ray_PosZ = camera.posZ;
////
////                    ray_DirX = -camera.focalLength * camera.dir_W_x + u * camera.dir_U_x + v * camera.dir_V_x;
////                    ray_DirY = -camera.focalLength * camera.dir_W_y + u * camera.dir_U_y + v * camera.dir_V_y;
////                    ray_DirZ = -camera.focalLength * camera.dir_W_z + u * camera.dir_U_z + v * camera.dir_V_z;
////
////                } else if (camera.myCamera_type == "Orthographic") {
////
////                    camera.imageplane_height = 8.0f;
////                    camera.imageplane_width = camera.imageplane_height * aspectRatio;
////
////                    float l = -camera.imageplane_width / 2.0f;
////                    float r = camera.imageplane_width / 2.0f;
////                    float b = -camera.imageplane_height / 2.0f;
////                    float t = camera.imageplane_height / 2.0f;
////
////                    float u = l + (r - l) * col / (float) image.getImageWidth();
////                    float v = b + (t - b) * row / (float) image.getImageHeight();
////
////                    ray_DirX = -camera.dir_W_x;
////                    ray_DirY = -camera.dir_W_y;
////                    ray_DirZ = -camera.dir_W_z;
////
////                    ray_PosX = camera.posX + u * camera.dir_U_x + v * camera.dir_V_x;
////                    ray_PosY = camera.posY + u * camera.dir_U_y + v * camera.dir_V_y;
////                    ray_PosZ = camera.posZ + u * camera.dir_U_z + v * camera.dir_V_z;
////                }
//
//                // walk over all of the object types and determine
//                // if any object is hit by the ray that we just generated
//                // if so, we'll need to know where it hit and the surface information (normal)
//                // at that point
//                float intersect_NormalX = 0.0f;
//                float intersect_NormalY = 0.0f;
//                float intersect_NormalZ = 0.0f;
//
//                float intersect_PointX = 0.0f;
//                float intersect_PointY = 0.0f;
//                float intersect_PointZ = 0.0f;
//
//                // we assume we don't hit an object when we enter into the
//                // next code set, so reset the information before we check
//                // the objects
//                boolean rayHitAnObject = false;
//                //float t = Float.MAX_VALUE;
//                int closestObjectIdx = -1;
//
//                for (int idx = 0; idx < shapes.size(); ++idx) {
//
//                    if (shapes.get(idx).equals(sphere)) { //I want to change this to see the object type not that specific object
//
//                        Sphere sphere1 = (Sphere) shapes.get(idx);
//
//                        float x = sphere1.posX;
//                        float y = sphere1.posY;
//                        float z = sphere1.posZ;
//                        float radius = sphere1.radius;
//
//                        // solve quadratic equation
//
//                        float rayDir_dot_rayDir = ray_DirX * ray_DirX + ray_DirY * ray_DirY + ray_DirZ * ray_DirZ;
//                        float cameraPos_minus_Center_X = ray_PosX - x;
//                        float cameraPos_minus_Center_Y = ray_PosY - y;
//                        float cameraPos_minus_Center_Z = ray_PosZ - z;
//
//                        float B = ray_DirX * cameraPos_minus_Center_X + ray_DirY * cameraPos_minus_Center_Y + ray_DirZ * cameraPos_minus_Center_Z;
//
//                        float cameraSphere_dotProduct = cameraPos_minus_Center_X * cameraPos_minus_Center_X +
//                                cameraPos_minus_Center_Y * cameraPos_minus_Center_Y +
//                                cameraPos_minus_Center_Z * cameraPos_minus_Center_Z;
//
//                        float discriminant = B * B - rayDir_dot_rayDir * (cameraSphere_dotProduct - radius * radius);
//                        if (discriminant >= 0.0f) {
//                            float sqrtDiscr = (float) Math.sqrt(discriminant);
//                            float t1 = (-B + sqrtDiscr) / rayDir_dot_rayDir;
//                            float t2 = (-B - sqrtDiscr) / rayDir_dot_rayDir;
//
//                            float minT = Math.min(t1, t2);
//                            float maxT = Math.max(t1, t2);
//                            float closestT = (minT >= 1.0f) ? minT : (maxT >= 1.0f ? maxT : Float.MAX_VALUE);
//
//                            if (closestT < t) {
//                                t = closestT;
//                                rayHitAnObject = true;
//                                closestObjectIdx = idx;
//
//                                intersect_PointX = ray_PosX + t * ray_DirX;
//                                intersect_PointY = ray_PosY + t * ray_DirY;
//                                intersect_PointZ = ray_PosZ + t * ray_DirZ;
//
//                                intersect_NormalX = intersect_PointX - x;
//                                intersect_NormalY = intersect_PointY - y;
//                                intersect_NormalZ = intersect_PointZ - z;
//                                float vectorLength = (float) Math.sqrt(intersect_NormalX * intersect_NormalX +
//                                        intersect_NormalY * intersect_NormalY +
//                                        intersect_NormalZ * intersect_NormalZ);
//                                intersect_NormalX /= vectorLength;
//                                intersect_NormalY /= vectorLength;
//                                intersect_NormalZ /= vectorLength;
//                            }
//                        }
//
//                    }
////                    else if (object_Type.get(idx) == "Triangle") {
////                        // process triangle ray intersection
////                    } else if (object_Type.get(idx) == "Plane") {
////                        // if we had a plane we could do that here..
////                    }
//                }
//
//                int red = 255;
//                int green = 255;
//                int blue = 255;
//
//                if (rayHitAnObject && t > 1.0f && closestObjectIdx != -1) {
//
//                    if (shapes.get(closestObjectIdx).shaderType.equals("Intersection Shader")) {
//                        // Simply set the color to be the object's shader color
//                        // we'll see it in its color
//                        red = (int) floor(shapes.get(closestObjectIdx).shaderR * 255);
//                        green = (int) floor(shapes.get(closestObjectIdx).shaderG * 255);
//                        blue = (int) floor(shapes.get(closestObjectIdx).shaderB * 255);
//                    } else if (shapes.get(closestObjectIdx).shaderType.equals("Normal Shader")) {
//                        // convert the normal vector into a color - normal needs to be normalized before this step
//                        float normal_ColorRed = (intersect_NormalX + 1.0f) / 2.0f;
//                        float normal_ColorGreen = (intersect_NormalY + 1.0f) / 2.0f;
//                        float normal_ColorBlue = (intersect_NormalZ + 1.0f) / 2.0f;
//
//                        red = (int) floor(normal_ColorRed * 255);
//                        green = (int) floor(normal_ColorGreen * 255);
//                        blue = (int) floor(normal_ColorBlue * 255);
//                    } else if (shapes.get(closestObjectIdx).shaderType.equals("Lambertian Shader")) {
//                        // only 1 light for now - if I had lots of lights in that array, I could
//                        // loop over them and add their contributions together...
//                        float light_DirX = light.get(0).posX - intersect_PointX;
//                        float light_DirY = light.get(0).posY - intersect_PointY;
//                        float light_DirZ = light.get(0).posZ - intersect_PointZ;
//                        float lightDirLength = (float) Math.sqrt(light_DirX * light_DirX + light_DirY * light_DirY + light_DirZ * light_DirZ);
//                        light_DirX /= lightDirLength;
//                        light_DirY /= lightDirLength;
//                        light_DirZ /= lightDirLength;
//
//                        float normalDotLightDir = (float) Math.max(intersect_NormalX * light_DirX + intersect_NormalY * light_DirY + intersect_NormalZ * light_DirZ, 0.0f);
//
//                        float matR = shapes.get(closestObjectIdx).shaderR * light.get(0).colorR * normalDotLightDir;
//                        float matG = shapes.get(closestObjectIdx).shaderG * light.get(0).colorG * normalDotLightDir;
//                        float matB = shapes.get(closestObjectIdx).shaderB * light.get(0).colorB * normalDotLightDir;
//
//                        red = (int) Math.floor(matR * 255);
//                        green = (int) Math.floor(matG * 255);
//                        blue = (int) Math.floor(matB * 255);
//                    } else if (shapes.get(closestObjectIdx).shaderType.equals("Blinn-Phong Shader")) {
//                        // only 1 light for now - if I had lots of lights in that array, I could
//                        // loop over them and add their contributions together...
//                        float light_DirX = light.get(0).posX - intersect_PointX;
//                        float light_DirY = light.get(0).posY - intersect_PointY;
//                        float light_DirZ = light.get(0).posZ - intersect_PointZ;
//                        float lightDirLength = (float) Math.sqrt(light_DirX * light_DirX + light_DirY * light_DirY + light_DirZ * light_DirZ);
//                        light_DirX /= lightDirLength;
//                        light_DirY /= lightDirLength;
//                        light_DirZ /= lightDirLength;
//
//                        float normalDotLightDir = (float) Math.max(intersect_NormalX * light_DirX + intersect_NormalY * light_DirY + intersect_NormalZ * light_DirZ, 0.0f);
//
//                        float diffuseComponent_R = shapes.get(closestObjectIdx).shaderR * light.get(0).colorR * normalDotLightDir;
//                        float diffuseComponent_G = shapes.get(closestObjectIdx).shaderG * light.get(0).colorG * normalDotLightDir;
//                        float diffuseComponent_B = shapes.get(closestObjectIdx).shaderB * light.get(0).colorB * normalDotLightDir;
//
//                        // acquire specular component of the shading
//                        float view_DirX = -1.0f * ray_DirX;
//                        float view_DirY = -1.0f * ray_DirY;
//                        float view_DirZ = -1.0f * ray_DirZ;
//                        float viewDirLength = (float) Math.sqrt(view_DirX * view_DirX + view_DirY * view_DirY + view_DirZ * view_DirZ);
//                        view_DirX /= viewDirLength;
//                        view_DirY /= viewDirLength;
//                        view_DirZ /= viewDirLength;
//
//                        float halfVec_DirX = view_DirX + light_DirX;
//                        float halfVec_DirY = view_DirY + light_DirY;
//                        float halfVec_DirZ = view_DirZ + light_DirZ;
//                        float halfVecLength = (float) Math.sqrt(halfVec_DirX * halfVec_DirX + halfVec_DirY * halfVec_DirY + halfVec_DirZ * halfVec_DirZ);
//                        halfVec_DirX /= halfVecLength;
//                        halfVec_DirY /= halfVecLength;
//                        halfVec_DirZ /= halfVecLength;
//
//                        float normalDotHalfVec = (float) Math.max(intersect_NormalX * halfVec_DirX + intersect_NormalY * halfVec_DirY + intersect_NormalZ * halfVec_DirZ, 0.0);
//
//                        float phongExp = 16.0f;
//                        float specularComponent_R = (float) Math.pow(normalDotHalfVec, phongExp);
//                        float specularComponent_G = (float) Math.pow(normalDotHalfVec, phongExp);
//                        float specularComponent_B = (float) Math.pow(normalDotHalfVec, phongExp);
//
//                        float matR = diffuseComponent_R + specularComponent_R;
//                        float matG = diffuseComponent_G + specularComponent_G;
//                        float matB = diffuseComponent_B + specularComponent_B;
//
//                        red = (int) Math.floor(matR * 255);
//                        green = (int) Math.floor(matG * 255);
//                        blue = (int) Math.floor(matB * 255);
//                    } else {
//                        // unknown shader so just set the color to something bright
//                        // also means we hit an object so we should color it something
//                        // other than the background, like bright pink
//                        red = 255;
//                        green = 105;
//                        blue = 180;
//                    }
//                }
//                else {
//                    // no objects were intersected with our viewing ray so set
//                    // the pixel to the background color
//                    // something like sky-blue like
//                    red = 207;
//                    green = 236;
//                    blue = 247;
//                }
//
//                // We can't have values larger than 255 or less than 0 in a
//                // PNG image, so clamp these values before we set them
//                // in the PNG image
//                red = Math.clamp(red, 0, 255);
//                green = Math.clamp(green, 0, 255);
//                blue = Math.clamp(blue, 0, 255);
//
//                // Set RGB values directly at the column and row
//                // Need to flip the height since 0,0 is in upper left of PNG image
//                image.setPixel(col, (image.getImageHeight() - 1) - row, red, green, blue);
//            }
//        }

        image.writeData("raytrace_Image.png");
    }
}
