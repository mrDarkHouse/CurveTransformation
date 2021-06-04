import java.awt.*;
import java.util.ArrayList;

public class BezierCurve extends DrawableObject{

    public ArrayList<Point> controlPoints;

    public BezierCurve() {
        controlPoints = new ArrayList<>();
    }

    @Override
    protected void draw(Graphics2D g2) {
        float step = 0.02f;

        ArrayList<Point> res = new ArrayList<>();

        for (float t = 0; t < 1; t += step) {
            if (t > 1) t = 1;

            double ytmp = 0;
            double xtmp = 0;

            for (int i = 0; i < controlPoints.size(); i++) {
                double b = getBezierBasis(i, controlPoints.size() - 1, t);
                xtmp += controlPoints.get(i).x * b;
                ytmp += controlPoints.get(i).y * b;
            }
            Point point = Main.main.converter.r2s(new MyPoint(xtmp, ytmp));
            res.add(new Point(point.x, point.y));
        }
        for (int i = 0; i < res.size() - 1; i++) {
            BrezLine line = new BrezLine(res.get(i).x, res.get(i + 1).x, res.get(i).y, res.get(i + 1).y, Color.BLACK);
            line.draw(g2);
        }
    }

    private int fact(int n) {
        if(n <= 1){
            return 1;
        }else{
            return n * fact(n - 1);
        }
    }

    private double getBezierBasis(int i, int n, float t){
        return (fact(n) / (double)(fact(i) * fact(n - i))) * Math.pow(t, i) * Math.pow(1 - t, n - i);
    }

}
