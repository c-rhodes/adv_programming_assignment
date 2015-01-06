import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by cullen on 04/12/14.
 */
public class Assignment extends JFrame {
    int dataset[];

    JPanel controlBar, drawWidget;

    public Assignment(int dataset[]) {
        super("Visual Representation of Sorting Algorithms");

        this.setLayout(new GridLayout(0, 1));
        this.dataset = dataset;
        //GridBagConstraints c = new GridBagConstraints();

        ControlBar controlBar = new ControlBar(dataset);
        this.add(controlBar);

        DrawWidget drawWidget = new DrawWidget(dataset);
        this.add(drawWidget);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }
}

class DrawWidget extends JPanel {
    int dataset[];

    public DrawWidget(int dataset[]) {
        this.dataset = dataset;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);

        for(int i = 0; i < dataset.length; i++) {
            g.fillRect(i*10, 100-dataset[i], 20, dataset[i]);
            //g.drawRect(i*10, 100, 20, dataset[i]);
        }
    }
}

class ControlBar extends JPanel {
    int dataset[];

    JButton start, stop;
    JComboBox algorithm;

    public ControlBar(int dataset[]) {
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.setLayout(new FlowLayout());

        this.dataset = dataset;
        this.start = new JButton("Start sorting");
        this.stop = new JButton("Stop sorting");
        this.algorithm = new JComboBox(new String[]{"Bubble Sort", "Selection Sort", "Merge Sort", "Quicksort"});

        this.add(start);
        this.add(stop);
        this.add(algorithm);

        startEventHandler startHandler = new startEventHandler();

        this.start.addActionListener(startHandler);
        this.stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        this.algorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }
    public int[] bubbleSort(int array[]) {
        int x, y, temp;
        for(x = 0; x < array.length; x++)
        {
            for(y = 0; y < array.length - 1; y++)
            {
                if(array[y] > array[y+1])
                {
                    temp = array[y+1];
                    array[y+1] = array[y];
                    array[y] = temp;
                    this.repaint();
                }
            }
        }
        return array;
    }

    private class startEventHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.println("Start button pressed");
            if(algorithm.getSelectedItem().toString().equals("Bubble Sort")) {
                System.out.println("Bubble sort");
                bubbleSort(dataset);
            }
        }
    }
}
