package ru.lundin.GraphDrawer.Apps;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.lundin.GraphDrawer.Drawing.JavafxDrawingApi;
import ru.lundin.GraphDrawer.Graph.Graph;

public class JavaFXApp extends Application implements MyDrawApp {

    private static long height;
    private static long width;
    private static int graphType;

    public JavaFXApp() {
    }

    public JavaFXApp(long height, long width, int graphType) {
        JavaFXApp.height = height;
        JavaFXApp.width = width;
        JavaFXApp.graphType = graphType;
    }

    @Override
    public void start(Stage stage) {
        JavafxDrawingApi api = new JavafxDrawingApi(height, width, stage);
        Graph graph = GraphGetter.getGraph(graphType, api);
        graph.drawGraph();
        api.show();
    }

    @Override
    public void run() {
        launch();
    }
}
