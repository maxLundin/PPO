package ru.lundin.GraphDrawer.Drawing;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JavafxDrawingApi implements DrawingApi {

    private final long drawingAreaWidth;
    private final long drawingAreaHeight;

    private final Stage stage;
    private final Group group;
    private final Canvas canvas;
    private final GraphicsContext graphicsContext;

    public JavafxDrawingApi(long drawingAreaHeight, long drawingAreaWidth, Stage stage) {
        this.drawingAreaHeight = drawingAreaHeight;
        this.drawingAreaWidth = drawingAreaWidth;
        this.stage = stage;
        this.group = new Group();
        this.canvas = new Canvas(drawingAreaWidth, drawingAreaHeight);
        this.graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.CYAN);
    }

    @Override
    public long getDrawingAreaWidth() {
        return drawingAreaWidth;
    }

    @Override
    public long getDrawingAreaHeight() {
        return drawingAreaHeight;
    }

    @Override
    public void drawCircle(Circle circle) {
        graphicsContext.fillOval(circle.center.x - circle.radius, circle.center.y - circle.radius, 2 * circle.radius, 2 * circle.radius);
    }

    @Override
    public void drawLine(Line line) {
        graphicsContext.strokeLine(line.start.x, line.start.y, line.finish.x, line.finish.y);
    }

    @Override
    public void show() {
        group.getChildren().add(canvas);
        stage.setScene(new Scene(group));
        stage.show();
    }
}
