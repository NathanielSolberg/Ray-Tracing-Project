import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Main {
    public static void main(String[] args) {

        //create 1 image
        PNGImageData image = new PNGImageData(800, 500);

        //make your lights here
        Light light1 = new Light(0.0f, 0.0f, 0.0f);

        ArrayList<Light> light = new ArrayList<>();
        light.add(light1);

        //make shaders here
        Normal shader1 = new Normal(1.0f, 1.0f, 1.0f);

        //make shapes here
        Sphere sphere = new Sphere(1.0f, 0.0f, 0.0f, 0.0f, shader1);

        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(sphere);

        Perspective camera = new Perspective(800, 500, image, shapes);

        camera.takeSnapShot();
    }
}
