import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class ObjectController extends DrawableObject implements MouseListener, MouseMotionListener {

    public BezierCurve object;
    private ArrayList<Boolean> resizeRects;
    private final int resizeBoxSize = 5;
    private boolean editing = true;
    private boolean pointsAdded = true;
    private int maxControlPoints;

    private double offsetX;
    private double offsetY;
    private boolean dragging;

    public boolean resizingNow(){
        for (Boolean resizeRect : resizeRects) {
            if (resizeRect) return true;
        }
        return false;
    }

    public ObjectController(int maxControlPoints) {
        this.object = new BezierCurve();
        resizeRects = new ArrayList<>();
        this.maxControlPoints = maxControlPoints;
    }

    public void pauseAdd(){
        pointsAdded = false;
    }

    public void continueAdd(){
        pointsAdded = true;
    }

    public void endEdit(){
        editing = false;
        repaint();
    }

    private void drawResizeBoxes(Graphics2D g2){
        if(!editing) return;
        for (int i = 0; i < object.controlPoints.size(); i++) {
            Point point = Main.main.converter.r2s(new MyPoint(object.controlPoints.get(i).x, object.controlPoints.get(i).y));
//            double k = Main.main.converter.getZoom();
            g2.drawRect(
                    point.x - resizeBoxSize,
                    point.y - resizeBoxSize,
                    resizeBoxSize*2,
                    resizeBoxSize*2);
        }
    }

    private void activateResizeBox(Point p){
        for (int i = 0; i < resizeRects.size(); i++) {
            double k = Main.main.converter.getZoom();
            double size = /*Math.floor*/(resizeBoxSize*k);
            if(p.x >= object.controlPoints.get(i).x - size &&
                    p.x <= object.controlPoints.get(i).x + size &&
                    p.y >= object.controlPoints.get(i).y - size &&
                    p.y <= object.controlPoints.get(i).y + size){
                resizeRects.set(i, true);
            }else {
                resizeRects.set(i, false);
            }
        }
    }

    public void addPoint(Point p){
        object.controlPoints.add(p);
        resizeRects.add(false);
        if (maxControlPoints == object.controlPoints.size()){
            Main.main.allPointsSetted();
        }
        repaint();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(pointsAdded){
            if (maxControlPoints != object.controlPoints.size()/* || maxControlPoints == -1*/) {
                MyPoint p = Main.main.converter.s2r(new Point(e.getX(), e.getY()));
                addPoint(new Point((int)p.getX(), (int)p.getY()));
            }
        }
    }

    private double errX;
    private double errY;

    @Override
    public void mouseDragged(MouseEvent e) {
        if(!editing) return;
        if (dragging) {
            for (int i = 0; i < resizeRects.size(); i++) {
                if(resizeRects.get(i)){
                    ScreenConverter sc = Main.main.converter;

                    double dx = (offsetX - sc.s2r(e.getPoint()).getX());
                    double dy = (offsetY - sc.s2r(e.getPoint()).getY());
                    errX += dx;
                    errY += dy;
                    if(Math.abs(errX) > 1){
                        object.controlPoints.get(i).x = object.controlPoints.get(i).x - (int)errX;
                        errX = errX - (int)errX;
                    }
                    if(Math.abs(errY) > 1){
                        object.controlPoints.get(i).y = object.controlPoints.get(i).y - (int)errY;
                        errY = errY - (int)errY;
                    }
                    MyPoint t = sc.s2r(e.getPoint());
                    offsetX = t.getX();
                    offsetY = t.getY();
                    repaint();
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!editing) return;
        MyPoint p = Main.main.converter.s2r(e.getPoint());
        activateResizeBox(new Point((int)p.getX(), (int)p.getY()));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!editing) return;

        for (int i = 0; i < resizeRects.size(); i++) {
            if(resizeRects.get(i)){
                MyPoint p = Main.main.converter.s2r(e.getPoint());
                offsetX = p.getX();
                offsetY = p.getY();
                dragging = true;
                errX = 0;
                errY = 0;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!editing) return;
        dragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    protected void draw(Graphics2D g2) {
        object.draw(g2);
        drawResizeBoxes(g2);
    }
}
