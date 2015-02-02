import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * The purpose of this class is to provide a generic base
 * sorting panel. It provides all the boilerplate
 * for a animated sorting panel and can be used
 * as a base such that adding new sorting algorithms
 * is as simple as subclassing this and overriding the
 * sort method.
 * @author cullen
 */
public class SortPanel extends JComponent {

    static private Random r = new Random();
    static protected final int MAX_VALUE = 100;
    protected final int ARRAY_LENGTH = 400;
    protected final int[] array = new int[ARRAY_LENGTH];
    private boolean stopThread = false;
    protected int swap_pos; // index of element being swapped, used for graphical swap tracer

    static final int DELAY_MIN = 0;
    static final int DELAY_MAX = 30;
    static final int DELAY_INIT = 5;

    JButton startButton, stopButton;
    JSlider delaySlider;

    public SortPanel() {
        this.setLayout(new FlowLayout());

        this.startButton = new JButton("Start sorting");
        this.stopButton = new JButton("Stop sorting");
        this.stopButton.setEnabled(false);

        this.delaySlider = new JSlider(JSlider.HORIZONTAL, DELAY_MIN, DELAY_MAX, DELAY_INIT);
        this.delaySlider.setMajorTickSpacing(10);
        this.delaySlider.setMinorTickSpacing(1);
        this.delaySlider.setPaintTicks(true);
        this.delaySlider.setPaintLabels(true);

        this.add(startButton);
        this.add(stopButton);
        this.add(delaySlider);

        startActionHandler startHandler = new startActionHandler();
        stopActionListener stopHandler = new stopActionListener();

        this.startButton.addActionListener(startHandler);
        this.stopButton.addActionListener(stopHandler);
    }

    private class startActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fillArray(array);
            new Thread(new Runnable() {
                public void run() {
                    startButton.setEnabled(false);
                    stopButton.setEnabled(true);
                    try {
                        final long startTime = System.currentTimeMillis();
                        sort(array);
                        final long endTime = System.currentTimeMillis();
                        JOptionPane.showMessageDialog(null,
                            "Delay: " + delaySlider.getValue() + "ms\nDataset size (n): " + array.length + "\nTime: " + (endTime - startTime) + "ms",
                            "Statistics", JOptionPane.INFORMATION_MESSAGE
                        );
                    } catch (Exception e) {};
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                }
            }).start();
        }
    }

    private class stopActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stopThread = true;
        }
    }

    /**
     *
     * @param a
     */
    static public void fillArray(int[] a) {
        for (int i = 0; i < a.length; i++)
            a[i] = r.nextInt(MAX_VALUE);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x1 = 75;
        int y1 = 80;
        int y2 = getHeight() - y1;
        int y_height = getHeight() - 2 * y1;
        int x_width = getWidth() - (2 * x1);

        int x_padding = (int) Math.round((double) x_width / (double) array.length);
        int y_pixel_ratio = y_height / MAX_VALUE;

        int[] xPoints = new int[]{swap_pos*x_padding+x1-5, swap_pos*x_padding+x1, swap_pos*x_padding+x1+5};
        int[] yPoints = new int[]{y2 + 15, y2 + 5, y2 + 15};
        g.setColor(Color.red);
        g.fillPolygon(xPoints, yPoints, 3);
        int x_i = x1;
        for (int i = 0; i < array.length; i++) {
            g.setColor(Color.black);
            g.drawLine(x_i, y2, x_i, y2 - (array[i] * y_pixel_ratio));
            x_i += x_padding;
        }
    }

    /**
     *
     * @throws InterruptedException
     */
    protected void delay() throws InterruptedException {
        repaint();
        Thread.sleep(this.delaySlider.getValue());
        if (stopThread) {
            stopThread = false;
            throw new RuntimeException();
        }
    }

    /**
     * Stub class, should be overrode in a base class
     * @param a
     * @throws InterruptedException
     */
    protected void sort(int[] a) throws InterruptedException {

    }
}