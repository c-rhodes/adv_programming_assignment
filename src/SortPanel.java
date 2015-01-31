import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by cullen on 31/01/15.
 */

public class SortPanel extends JComponent {
    private Random r = new Random();
    protected final int ARRAY_LENGTH = 400;
    protected final int MAX_VALUE = 100;
    protected final int[] array = new int[ARRAY_LENGTH];
    protected boolean stopThread = false;
    protected int swap_value, swap_pos;   // value of item being swapped, index of value being swapped
    protected int delay;

    static final int DELAY_MIN = 0;
    static final int DELAY_MAX = 30;
    static final int DELAY_INIT = 5;

    JButton startButton, stopButton;
    JSlider delaySlider;

    public SortPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.setLayout(new FlowLayout());


        this.startButton = new JButton("Start sorting");
        this.stopButton = new JButton("Stop sorting");
        this.stopButton.setEnabled(false);

        this.delay = 5;
        this.delaySlider = new JSlider(JSlider.HORIZONTAL, DELAY_MIN, DELAY_MAX, DELAY_INIT);
        this.delaySlider.setMajorTickSpacing(10);
        this.delaySlider.setMinorTickSpacing(1);
        this.delaySlider.setPaintTicks(true);
        this.delaySlider.setPaintLabels(true);
        this.delaySlider.addChangeListener(new SliderListener());

        this.add(startButton);
        this.add(stopButton);
        this.add(delaySlider);

        startActionHandler startHandler = new startActionHandler();
        stopActionListener stopHandler = new stopActionListener();

        this.startButton.addActionListener(startHandler);
        this.stopButton.addActionListener(stopHandler);
    }

    class SliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if(!source.getValueIsAdjusting()) {
                delay = (int) source.getValue();
            }
        }
    }

    private class startActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Start button pressed");
            startButton.setEnabled(false);
            stopButton.setEnabled(true);

            fillArray(array);

            new Thread(new Runnable() {
                public void run() {
                    try {
                        final long startTime = System.currentTimeMillis();
                        sort(array);
                        final long endTime = System.currentTimeMillis();
                        JOptionPane.showMessageDialog(null,
                            "Delay: " + delay + "ms\nDataset size (n): " + array.length + "\nTime: " + (endTime - startTime) + "ms",
                            "Statistics", JOptionPane.INFORMATION_MESSAGE
                        );
                    } catch (Exception e) {};
                }
            }).start();

            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }

    private class stopActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stopThread = true;
        }
    }

    private void fillArray(int[] a) {
        for (int i = 0; i < a.length; i++)
            a[i] = r.nextInt(MAX_VALUE);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int defaultx = (this.getWidth() / 2) - array.length / 2;
        int defaulty = (this.getHeight() / 2) - MAX_VALUE / 2;

        int[] xPoints = new int[]{swap_pos + defaultx - 5, swap_pos + defaultx, swap_pos + defaultx + 5};
        int[] yPoints = new int[]{MAX_VALUE + defaulty + 15, MAX_VALUE + defaulty + 5, MAX_VALUE + defaulty + 15};
        g.setColor(Color.red);
        g.fillPolygon(xPoints, yPoints, 3);

        for (int i = 0; i < array.length; i++) {
            g.setColor(Color.black);
            g.drawLine(i + defaultx, MAX_VALUE + defaulty, i + defaultx, MAX_VALUE + defaulty - array[i]);
        }
    }

    protected void delay(int delay) throws InterruptedException {
        repaint();
        Thread.sleep(this.delay);
        if (stopThread) {
            stopThread = false;
            throw new RuntimeException();
        }
    }

    protected void sort(int[] a) throws InterruptedException {

    }
}