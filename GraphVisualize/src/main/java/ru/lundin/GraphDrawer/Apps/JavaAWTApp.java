package ru.lundin.GraphDrawer.Apps;


import ru.lundin.GraphDrawer.Drawing.JavaAWTDrawingApi;
import ru.lundin.GraphDrawer.Graph.Graph;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JavaAWTApp extends Frame implements MyDrawApp {

    static long height;
    static long width;
    static int graphType;

    public JavaAWTApp() {

    }

    public JavaAWTApp(long height, long width, int graphType) {
        JavaAWTApp.height = height;
        JavaAWTApp.width = width;
        JavaAWTApp.graphType = graphType;
    }

    @Override
    public void paint(Graphics g) {
        JavaAWTDrawingApi api = new JavaAWTDrawingApi(width, height, (Graphics2D) g);
        Graph graph = GraphGetter.getGraph(graphType, api);
        graph.drawGraph();
        api.show();
    }

    @Override
    public void run() {
        Frame frame = new JavaAWTApp();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize((int)width, (int)height);
        frame.setVisible(true);
    }
}
