import java.awt.*;

public class ScreenConverter {
    private double rx, ry, rw, rh;
    private int sw, sh;
    private double zoom = 1;

    public Point r2s(MyPoint p){
        int x=(int)(sw*(p.getX()-rx)/rw);
        int y=(int)(sh*(ry-p.getY())/rh);
        return new Point(x,y);
    }

    public MyPoint s2r(Point p){
        double x=rx+p.x*rw/sw;
        double y=ry-p.y*rh/sh;
        return new MyPoint(x,y);
    }

    public ScreenConverter(double rx, double ry, double rw, double rh, int sw, int sh) {
        this.rx = rx;
        this.ry = ry;
        this.rw = rw;
        this.rh = rh;
        this.sw = sw;
        this.sh = sh;
    }

    public double getZoom() {
        return zoom;
    }

    public double getRx() {
        return rx;
    }

    public double getRy() {
        return ry;
    }

    public double getRw() {
        return rw;
    }

    public double getRh() {
        return rh;
    }

    public int getSw() {
        return sw;
    }

    public int getSh() {
        return sh;
    }

    public void setRx(double rx) {
        this.rx = rx;
    }

    public void setRy(double ry) {
        this.ry = ry;
    }

    public void setRw(double rw) {
        if(rw <= 0) return;
        this.rw = rw;
    }

    public void setRh(double rh) {
        if(rh <= 0) return;
        this.rh = rh;
    }

    public void zoomIn(){
        if(getRw() <= 2 || getRh() <= 2) return;
        zoom *= 0.9;
        double offsetX = getRw()*0.05;
        double offsetY = getRh()*0.05;
        setRw(getRw()*0.9);
        setRh(getRh()*0.9);
        setRx(getRx() + offsetX);
        setRy(getRy() - offsetY);
    }
    public void zoomOut(){
        if(getRw() >= 30000 || getRh() >= 30000)return;
        zoom *= 1.1;
        double offsetX = getRw()*0.05;
        double offsetY = getRh()*0.05;
        setRw(getRw()*1.1);
        setRh(getRh()*1.1);
        setRx(getRx() - offsetX);
        setRy(getRy() + offsetY);
    }


    public void setSw(int sw) {
        this.sw = sw;
    }

    public void setSh(int sh) {
        this.sh = sh;
    }
}
