import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.floor;

public abstract class Camera extends Position {

    float myCamera_focalLength;

    String myCamera_type;

    float ray_PosX = 0.0f;
    float ray_PosY = 0.0f;
    float ray_PosZ = 0.0f;

    float ray_DirX = 0.0f;
    float ray_DirY = 0.0f;
    float ray_DirZ = 0.0f;

    int red = 255;
    int green = 255;
    int blue = 255;

    PNGImageData image;

    ArrayList<Shape> shapes;


    public Camera(float imageplane_height, float imageplane_width, PNGImageData image, ArrayList<Shape> shapes) {
        this.shapes = shapes;
        this.image = image;
    }

    public void render() {
        for (int row = 0; row < image.getImageHeight(); row++) {
            for (int col = 0; col < image.getImageWidth(); col++) {



                // we assume we don't hit an object when we enter into the
                // next code set, so reset the information before we check
                // the objects
                boolean rayHitAnObject = false;
                float t = Float.MAX_VALUE;
                int closestObjectIdx = -1;

                for (int idx = 0; idx < shapes.size(); ++idx) {
                    Shape shape = shapes.get(idx);
                    shape.render();
                    if (shape.shader.rayHitAnObject) {
                        rayHitAnObject = true;
                    }
                    red = shape.shader.red;
                }
                red = 255;
                green = 255;
                blue = 255;

                if (rayHitAnObject && t > 1.0f && closestObjectIdx != -1) {

                    shapes.get(closestObjectIdx).shader.render();

//                    if (object_ShaderType.get(closestObjectIdx).equals("Intersection Shader")) {
//                        // Simply set the color to be the object's shader color
//                        // we'll see it in its color
//                        red = (int) floor(object_ShaderMaterialR.get(closestObjectIdx) * 255);
//                        green = (int) floor(object_ShaderMaterialG.get(closestObjectIdx) * 255);
//                        blue = (int) floor(object_ShaderMaterialB.get(closestObjectIdx) * 255);
//                    } else if (object_ShaderType.get(closestObjectIdx).equals("Normal Shader")) {
//                        // convert the normal vector into a color - normal needs to be normalized before this step
//                        float normal_ColorRed = (intersect_NormalX + 1.0f) / 2.0f;
//                        float normal_ColorGreen = (intersect_NormalY + 1.0f) / 2.0f;
//                        float normal_ColorBlue = (intersect_NormalZ + 1.0f) / 2.0f;
//
//                        red = (int) floor(normal_ColorRed * 255);
//                        green = (int) floor(normal_ColorGreen * 255);
//                        blue = (int) floor(normal_ColorBlue * 255);
//                    } else if (object_ShaderType.get(closestObjectIdx).equals("Lambertian Shader")) {
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
//                        float matR = object_ShaderMaterialR.get(closestObjectIdx) * light.get(0).colorR * normalDotLightDir;
//                        float matG = object_ShaderMaterialG.get(closestObjectIdx) * light.get(0).colorG * normalDotLightDir;
//                        float matB = object_ShaderMaterialB.get(closestObjectIdx) * light.get(0).colorB * normalDotLightDir;
//
//                        red = (int) Math.floor(matR * 255);
//                        green = (int) Math.floor(matG * 255);
//                        blue = (int) Math.floor(matB * 255);
//                    } else if (object_ShaderType.get(closestObjectIdx).equals("Blinn-Phong Shader")) {
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
//                        float diffuseComponent_R = object_ShaderMaterialR.get(closestObjectIdx) * light.get(0).colorR * normalDotLightDir;
//                        float diffuseComponent_G = object_ShaderMaterialG.get(closestObjectIdx) * light.get(0).colorG * normalDotLightDir;
//                        float diffuseComponent_B = object_ShaderMaterialB.get(closestObjectIdx) * light.get(0).colorB * normalDotLightDir;
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
