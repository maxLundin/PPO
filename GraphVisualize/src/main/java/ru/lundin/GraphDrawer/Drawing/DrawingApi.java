package ru.lundin.GraphDrawer.Drawing;

public interface DrawingApi {

    class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    class Circle {
        public Point center;
        public double radius;

        public Circle(Point center, double radius) {
            this.center = center;
            this.radius = radius;
        }
    }

    class Line {
        public Point start;
        public Point finish;

        public Line(Point a, Point b) {
            start = a;
            finish = b;
        }
    }

    long getDrawingAreaWidth();

    long getDrawingAreaHeight();

    void drawCircle(Circle circle);

    void drawLine(Line line);

    void show();
}