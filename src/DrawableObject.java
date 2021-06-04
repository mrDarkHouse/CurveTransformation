import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class DrawableObject extends JComponent {

    protected Color color;
    protected ArrayList<Point> path;

    public DrawableObject(Color color) {
        this();
        this.color = color;
        path = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public DrawableObject() {
        setBounds(0, 0, Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
    }

    protected abstract void draw(Graphics2D g2);

    protected void drawPixel(Graphics2D g2, int x, int y){
        g2.drawLine(x, y, x, y);
    }

    @Override
    public void repaint() {
//        path.clear();
        super.repaint();
    }

}
