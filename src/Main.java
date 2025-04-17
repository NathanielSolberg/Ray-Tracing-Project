import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Main {
    public static void main(String[] args) {

        //I have made changes after the due date feel free to grade based on the version pushed before

        //create images here
        PNGImageData image = new PNGImageData(800, 500);


        //set up shaders here
        Intersection shader = new Intersection(100, 0, 100);

        //create shapes here
        Sphere sphere = new Sphere(0.0f, 0.0f, -10.0f, 4.0f, shader);

        //array list of shapes
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(sphere); //add each shape to the array list

        //add any cameras here
        // not a very fun constructor to use... -Pete
        Perspective camera = new Perspective(image, shapes, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f);

        //take a picture with .takeSnapshot method
        camera.takeSnapshot();

        //not entirely complete but you can see above what the organization will look like
        //lights don't really do anything right now and I still need to add in the logic for all the different shader types and maybe add some more shapes

        //this could theoretically create frames for an animation of sorts if you wanted to make a loop that moves objects around and takes snapshots
    }
}
