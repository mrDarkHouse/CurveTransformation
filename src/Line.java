import java.awt.*;

public abstract class Line extends DrawableObject {
    public double x1, x2, y1, y2;

    public Line(double x1, double x2, double y1, double y2, Color color) {
        super(color);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public Line(Color color) {
        super(color);
    }
}
