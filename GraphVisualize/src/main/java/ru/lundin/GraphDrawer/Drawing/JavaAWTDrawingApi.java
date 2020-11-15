package ru.lundin.GraphDrawer.Drawing;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class JavaAWTDrawingApi implements DrawingApi {

    final private Graphics2D graphics2D;
    final private long drawingAreaWidth;
    final private long drawingAreaHeight;

    public JavaAWTDrawingApi(long drawingAreaWidth, long drawingAreaHeight, Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
        this.drawingAreaWidth = drawingAreaWidth;
        this.drawingAreaHeight = drawingAreaHeight;
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
        graphics2D.setColor(Color.CYAN);
        graphics2D.fill(new Ellipse2D.Double(circle.center.x - circle.radius, circle.center.y - circle.radius, 2 * circle.radius, 2 * circle.radius));
    }

    @Override
    public void drawLine(Line line) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.draw(new Line2D.Double(line.start.x, line.start.y, line.finish.x, line.finish.y));
    }

    @Override
    public void show() {
    }
}
