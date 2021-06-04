package project;

import project.Graphics.ObjectController;
import project.Graphics.ScreenController;
import project.Lines.BrezLine;
import project.Lines.CoordLine;
import project.Graphics.ScreenConverter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


public class Main{
    public final static int FRAME_WIDTH = 1300;
    public final static int FRAME_HEIGHT = 700;

    private final static int BORDER_SIZE = 2;

    public ObjectController currentLine;
    private ArrayList<ObjectController> lines;

    public JPanel getGraphic() {
        return graphic;
    }

    private JPanel graphic;
    private JPanel control;
    private JButton move;
    private JButton addLine;
    private JButton endLine;
    private JLabel iterationsL;
    private JLabel delayL;
    private JSpinner iterations;
    private JSpinner delay;

    public static Main main;
    public ScreenConverter converter;

    public static void main(String[] args) {
        main = new Main();
    }

    public Main() {
        lines = new ArrayList<>();

        JFrame w = new JFrame();

        w.setLayout(null);
        w.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        w.setSize(FRAME_WIDTH + 10, FRAME_HEIGHT + 40);


        graphic = new JPanel();
        control = new JPanel();
        graphic.setBorder(new LineBorder(Color.BLACK, BORDER_SIZE));
        control.setBorder(new LineBorder(Color.BLACK, BORDER_SIZE));
        graphic.setLayout(null);
        control.setLayout(null);
        graphic.setBounds(0, 0, FRAME_WIDTH/5*4, FRAME_HEIGHT);
        control.setBounds(graphic.getX() + graphic.getWidth(), 0, FRAME_WIDTH/5, FRAME_HEIGHT);

        converter = new ScreenConverter(
                0, 0, graphic.getWidth(), graphic.getHeight(), graphic.getWidth(), graphic.getHeight());
        ScreenController controller = new ScreenController(converter);
//        w.addKeyListener(controller);
        graphic.setFocusable(true);
        graphic.addMouseListener(controller);
        graphic.addMouseMotionListener(controller);
        graphic.addKeyListener(controller);
        graphic.addMouseWheelListener(controller);

        CoordLine scaleModel = new CoordLine(converter, graphic);
        graphic.add(scaleModel);

        addLine = new JButton("Create curve");
        endLine = new JButton("End editing");
        JButton continueEdit = new JButton("Continue editing");
        move = new JButton("Start move");
        JButton nextTest = new JButton("Next test");
//        JLabel controlPointsL = new JLabel("Points");
        iterationsL = new JLabel("Iterations", JLabel.CENTER);
        delayL = new JLabel("Delay", JLabel.CENTER);
//        JSpinner controlPoints = new JSpinner(new SpinnerNumberModel(4, 2, 20, 1));
        iterations = new JSpinner(new SpinnerNumberModel(100, 0, 1000, 10));
        delay = new JSpinner(new SpinnerNumberModel(25, 25, 1000, 25));

        int width = FRAME_WIDTH/5;
        int heigth = 40;

        addLine.setBounds(BORDER_SIZE, BORDER_SIZE, width - BORDER_SIZE*2, heigth);
        endLine.setBounds(BORDER_SIZE, BORDER_SIZE, width - BORDER_SIZE*2, heigth);
        continueEdit.setBounds(BORDER_SIZE, BORDER_SIZE + heigth, width - BORDER_SIZE*2, heigth);
        move.setBounds(BORDER_SIZE, BORDER_SIZE + heigth, width - BORDER_SIZE*2, heigth);
        nextTest.setBounds(BORDER_SIZE, BORDER_SIZE, width - BORDER_SIZE*2, heigth);
        iterationsL.setBounds(BORDER_SIZE, BORDER_SIZE, width/4 - BORDER_SIZE*2, heigth);
        iterations.setBounds(BORDER_SIZE + width/4, BORDER_SIZE, width/4 - BORDER_SIZE*2, heigth);
        delayL.setBounds(BORDER_SIZE + width*2/4, BORDER_SIZE, width/4 - BORDER_SIZE*2, heigth);
        delay.setBounds(BORDER_SIZE + width*3/4, BORDER_SIZE, width/4 - BORDER_SIZE*2, heigth);
        endLine.setVisible(false);
        continueEdit.setVisible(false);
        move.setVisible(false);
        nextTest.setVisible(false);
        iterationsL.setVisible(false);
        delayL.setVisible(false);
        iterations.setVisible(false);
        delay.setVisible(false);

        addLine.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(currentLine != null){
                    stopChangeLine();
                }
                addLine();
                addLine.setVisible(false);
                endLine.setVisible(true);
                continueEdit.setVisible(false);

            }
        });
        endLine.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                endLine.setVisible(false);
                continueEdit.setVisible(true);
                if(lines.size() == 1 && lines.get(0).object.controlPoints.size() == currentLine.object.controlPoints.size()){
                    continueEdit.setVisible(false);
                    stopChangeLine();
                }

                if(lines.size() < 2) {
                    currentLine.pauseAdd();

                    addLine.setVisible(true);
                    if(lines.size() == 1){
                        if(lines.get(0).object.controlPoints.size() != currentLine.object.controlPoints.size()){
                            addLine.setVisible(false);
                        }
                    }
                } else {
                    showMoveOptions();
                }
            }
        });
        continueEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addLine.setVisible(false);
                endLine.setVisible(true);
                continueEdit.setVisible(false);
                currentLine.continueAdd();
            }
        });
        move.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(currentLine != null)stopChangeLine();
                move.setVisible(false);
                delay.setVisible(false);
                iterationsL.setVisible(false);
                delayL.setVisible(false);
                iterations.setVisible(false);
                nextTest.setVisible(true);
                move((Integer) iterations.getValue(), (Integer) delay.getValue());
            }
        });
        nextTest.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                graphic.remove(lines.get(0));
                graphic.remove(lines.get(1));
                graphic.repaint();
                lines.clear();
                nextTest.setVisible(false);
                addLine.setVisible(true);
            }
        });

        control.add(addLine);
        control.add(endLine);
        control.add(continueEdit);
        control.add(move);
        control.add(nextTest);
        control.add(iterationsL);
        control.add(iterations);
        control.add(delayL);
        control.add(delay);


        w.add(graphic);
        w.add(control);

        w.setVisible(true);
    }

    private void addLine(){
        if(lines.size() == 0) currentLine = new ObjectController(-1);
        else currentLine = new ObjectController(lines.get(0).object.controlPoints.size());
        graphic.add(currentLine);
        graphic.addMouseMotionListener(currentLine);
        graphic.addMouseListener(currentLine);
    }

    private void showMoveOptions(){
        move.setVisible(true);
        iterations.setVisible(true);
        delay.setVisible(true);
        iterationsL.setVisible(true);
        delayL.setVisible(true);
    }

    public void allPointsSetted(){
        endLine.setVisible(false);
        showMoveOptions();
    }

    public void stopChangeLine(){
        currentLine.endEdit();
        graphic.removeMouseMotionListener(currentLine);
        graphic.removeMouseListener(currentLine);
        lines.add(currentLine);
        currentLine = null;
    }

    private void move(int iterations, int delay){
        int n = lines.get(0).object.controlPoints.size();
        BrezLine[] connect = new BrezLine[n];
        for (int i = 0; i < n; i++) {
            connect[i] = new BrezLine(
                    lines.get(0).object.controlPoints.get(i).x,
                    lines.get(1).object.controlPoints.get(i).x,
                    lines.get(0).object.controlPoints.get(i).y,
                    lines.get(1).object.controlPoints.get(i).y);
            connect[i].draw((Graphics2D) graphic.getGraphics());
        }

        int[] sizes = new int[n];
        for (int i = 0; i < n; i++) {
            sizes[i] = connect[i].path.size();
        }
        int[] steps = new int[n];
        for (int i = 0; i < n; i++) {
            steps[i] = sizes[i]/iterations;
        }
        float[] delta = new float[n];
        float[] error = new float[n];
        for (int i = 0; i < n; i++) {
            delta[i] = (float)sizes[i]/(iterations /*- 1*/) - steps[i];
        }
        Arrays.fill(error, 0f);

        Point[][] path = new Point[n][iterations + 1];

        for (int j = 0; j < n; j++) {
            int k1 = 0;
            for (int i = 0; i < iterations /*- 1*/; i++) {
                if(error[j] > 1){
                    error[j]--;
                    k1++;
                }
                path[j][i] = new Point(connect[j].path.get((i)*steps[j] + k1));
                error[j] += delta[j];
            }
            path[j][iterations] = new Point(
                    lines.get(1).object.controlPoints.get(j).x,
                    lines.get(1).object.controlPoints.get(j).y);
        }

        Timer t = new Timer();

        t.schedule(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                for (int j = 0; j < path.length; j++) {
                    lines.get(0).object.controlPoints.get(j).x = path[j][i].x;
                    lines.get(0).object.controlPoints.get(j).y = path[j][i].y;
                }
                i++;

                graphic.repaint();
                if(i == iterations + 1)cancel();
            }
        }, 500, delay);
    }

}
