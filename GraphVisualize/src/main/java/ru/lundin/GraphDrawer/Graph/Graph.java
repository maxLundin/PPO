package ru.lundin.GraphDrawer.Graph;

import ru.lundin.GraphDrawer.Drawing.DrawingApi;

public abstract class Graph {
    /**
     * Bridge to drawing api
     */
    private DrawingApi drawingApi;
    private DrawingApi.Point center;
    final double drawRadius = 50;


    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
        this.center = new DrawingApi.Point((double) (drawingApi.getDrawingAreaWidth()) / 2, (double) (drawingApi.getDrawingAreaHeight()) / 2);
    }

    public abstract void drawGraph();

    protected void drawVertices(int number) {
        double angle = 0;
        double dist = 2 * Math.PI / number;
        for (int i = 0; i < number; i++, angle += dist) {
            final double radius = 10;
            drawingApi.drawCircle(
                    new DrawingApi.Circle(
                            new DrawingApi.Point(
                                    center.x + Math.cos(angle) * drawRadius,
                                    center.y + Math.sin(angle) * drawRadius
                            ), radius
                    )
            );
        }
    }

    protected void drawLine(int index1, int index2, int number) {
        double dist1 = 2 * Math.PI * index1 / number;
        double dist2 = 2 * Math.PI * index2 / number;
        final double radius = 10;
        drawingApi.drawLine(new DrawingApi.Line(
                        new DrawingApi.Point(
                                center.x + Math.cos(dist1) * drawRadius,
                                center.y + Math.sin(dist1) * drawRadius
                        ),
                        new DrawingApi.Point(
                                center.x + Math.cos(dist2) * drawRadius,
                                center.y + Math.sin(dist2) * drawRadius
                        )
                )
        );
    }
}

