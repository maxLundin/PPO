package ru.lundin.GraphDrawer;

import ru.lundin.GraphDrawer.Apps.JavaAWTApp;
import ru.lundin.GraphDrawer.Apps.JavaFXApp;
import ru.lundin.GraphDrawer.Apps.MyDrawApp;

public class Application {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Illegal arguments");
            System.exit(1);
        }
        int graphType = 0;
        int drawType = 0;
        try {
            graphType = Integer.parseInt(args[0]);
            drawType = Integer.parseInt(args[1]);
            if ((graphType != 1 && graphType != 2) || (drawType != 1 && drawType != 2)) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong argument format: ");
            System.out.println("arguments: a b");
            System.out.println("a = 1 -> matrix");
            System.out.println("a = 2 -> edges");
            System.out.println("b = 1 -> javafx");
            System.out.println("b = 2 -> java.awt");
            System.exit(1);
        }

        MyDrawApp app;
        final long WIDTH = 1000;
        final long HEIGHT = 1000;
        if (drawType == 2) {
            app = new JavaAWTApp(HEIGHT, WIDTH, graphType);
        } else {
            app = new JavaFXApp(HEIGHT, WIDTH, graphType);
        }
        app.run();
    }
}
