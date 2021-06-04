import java.awt.*;

public class BrezLine extends Line {
    private int completed = -1;
    private boolean visible;

    public BrezLine(int x1, int x2, int y1, int y2, Color color) {
        super(x1, x2, y1, y2, color);
        completed = 1;
        visible = true;
    }
    public BrezLine(int x1, int x2, int y1, int y2) {
        super(x1, x2, y1, y2, Color.BLACK);
        completed = 1;
    }


    public BrezLine(Color color) {
        super(color);
    }

    @Override
    public void draw(Graphics2D g2) {
        if(completed == -1) return;
        g2.setColor(color);
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;
        dx = (int) (x2 - x1);
        dy = (int) (y2 - y1);
        incx = comp(dx);
        incy = comp(dy);
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        if (dx > dy){
            pdx = incx;
            pdy = 0;
            es = dy;
            el = dx;
        }else {
            pdx = 0;
            pdy = incy;
            es = dx;
            el = dy;
        }
        x = (int) x1;
        y = (int) y1;

        drawPixel(g2, x, y);
        err = 2 * es - el;
        for (int i = 0; i < el; i++) {
            if(err >= 0){
                x += incx;
                y += incy;
                err += 2*(es - el);
            }else {
                x += pdx;
                y += pdy;
                err += 2*es;
            }
            drawPixel(g2, x, y);
        }
    }
    private int comp (int x) {
        return Integer.compare(x, 0);
    }

    @Override
    protected void drawPixel(Graphics2D g2, int x, int y) {
        if(visible) super.drawPixel(g2, x, y);
        path.add(new Point(x, y));
    }
}
