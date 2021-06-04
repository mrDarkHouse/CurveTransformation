package project.Graphics;

import javax.swing.event.MouseInputListener;
import project.Main;
import java.awt.event.*;

public class ScreenController implements MouseMotionListener, MouseInputListener, MouseWheelListener, KeyListener{

    private static final int moveSpeed = 5;

    private ScreenConverter sc;

    private double offsetX;
    private double offsetY;
    private boolean dragging;

    public ScreenController(ScreenConverter sc) {
        this.sc = sc;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ObjectController currentLine = Main.main.currentLine;
        if(currentLine != null && currentLine.resizingNow()) return;
        if(dragging) {
            sc.setRx(sc.getRx() + offsetX - sc.s2r(e.getPoint()).getX());
            sc.setRy(sc.getRy() + offsetY - sc.s2r(e.getPoint()).getY());
            MyPoint t = sc.s2r(e.getPoint());
            offsetX = t.getX();
            offsetY = t.getY();
            Main.main.getGraphic().repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        ObjectController currentLine = Main.main.currentLine;
        if(currentLine != null && currentLine.resizingNow()) return;
        dragging = true;
        MyPoint p = sc.s2r(e.getPoint());
        offsetX = p.getX();
        offsetY = p.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getPreciseWheelRotation() < 0){
            sc.zoomIn();
        }else {
            sc.zoomOut();
        }
        Main.main.getGraphic().repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            sc.setRx(sc.getRx() - moveSpeed);
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            sc.setRx(sc.getRx() + moveSpeed);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            sc.setRy(sc.getRy() + moveSpeed);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            sc.setRy(sc.getRy() - moveSpeed);
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
//            sc.setRw(sc.getRw()*1.1);
//            sc.setRh(sc.getRh()*1.1);
            sc.zoomIn();
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
//            sc.setRw(sc.getRw()*0.9);
//            sc.setRh(sc.getRh()*0.9);
            sc.zoomOut();
        }
        Main.main.getGraphic().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
