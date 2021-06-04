import javax.swing.*;
import java.awt.*;

public class CoordLine extends DrawableObject{

    private ScreenConverter converter;
    private JPanel panel;

    public CoordLine(ScreenConverter converter, JPanel panel) {
        super(Color.BLACK);
        this.converter = converter;
        this.panel = panel;
    }

    @Override
    protected void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        int numX = 12;
        int numY = 12;
        int dx = panel.getWidth()/numX;
        int dy = panel.getHeight()/numY;
        int nx = panel.getWidth()/dx;
        int ny = panel.getHeight()/dy;
        for (int i = 0; i <= nx; i++) {
            g2.drawLine(panel.getX() + dx*i, panel.getY(), panel.getX() + dx*i, panel.getY() + 20);
            double tx = converter.getRw()/numX;
            int offset = i == 0 ? -100 : i == nx ? -35 : -10;
            g2.drawString(String.format("%.1f", (converter.getRx() + tx*i)), panel.getX() + dx*i + offset, panel.getY() + 30);
        }
        for (int i = 0; i <= ny; i++) {
            g2.drawLine(panel.getX(), panel.getY() + dy*i, panel.getX() + 20, panel.getY()+ dy*i);
            double ty = converter.getRh()/numY;
            int offset = i == 0 ? -100 : i == ny ? -15 : 5;
            g2.drawString(String.format("%.1f", (converter.getRy() + ty*i)), panel.getX() + 25, panel.getY() + dy*i + offset);

        }
        g2.drawString(String.format("%.3f", converter.getZoom()), panel.getWidth() - 40, panel.getHeight() - 10);

    }
}
