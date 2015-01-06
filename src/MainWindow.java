import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Arrays;

/**
 * Created by cullen on 05/01/15.
 */

public class MainWindow extends JFrame {

    public MainWindow() {
        super("Visual Representation of Sorting Algorithms");

        this.setLayout(new GridBagLayout());

        GridBagConstraints controlBarPanel_c = new GridBagConstraints();

        controlBarPanel_c.gridx = 0;
        controlBarPanel_c.gridy = 0;
        controlBarPanel_c.gridwidth = GridBagConstraints.REMAINDER;
        controlBarPanel_c.gridheight = 1;
        controlBarPanel_c.weightx = 1;
        controlBarPanel_c.weighty = 1;
        controlBarPanel_c.anchor = GridBagConstraints.FIRST_LINE_START;
        controlBarPanel_c.fill = GridBagConstraints.BOTH;
        controlBarPanel_c.insets = new Insets(4, 4, 4, 4);

        ControlBarPanel controlBarPanel = new ControlBarPanel();
        this.add(controlBarPanel, controlBarPanel_c);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }

    private final class ControlBarPanel extends JPanel {

        private Random r = new Random();
        private final int ARRAY_LENGTH = 400;
        private final int MAX_VALUE = 100;
        private final int[] array = new int[ARRAY_LENGTH];
        private boolean stopThread = false;

        JButton startButton, stopButton;
        JComboBox algorithmComboBox;

        public ControlBarPanel() {
            setBorder(BorderFactory.createLineBorder(Color.black));

            this.setLayout(new FlowLayout());

            this.startButton = new JButton("Start sorting");
            this.stopButton = new JButton("Stop sorting");
            this.stopButton.setEnabled(false);
            this.algorithmComboBox = new JComboBox(new String[]{"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quicksort"});

            this.add(startButton);
            this.add(stopButton);
            this.add(algorithmComboBox);

            startActionHandler startHandler = new startActionHandler();
            stopActionListener stopHandler = new stopActionListener();

            this.startButton.addActionListener(startHandler);
            this.stopButton.addActionListener(stopHandler);
        }

        private class startActionHandler implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start button pressed");
                new Thread(new Runnable() {
                    public void run() {
                        startButton.setEnabled(false);
                        stopButton.setEnabled(true);
                        fillArray(array);

                        int algorithm = algorithmComboBox.getSelectedIndex();
                        switch (algorithm) {
                            case 0:
                                // Bubble sort
                                System.out.println("Bubble sort");
                                try {
                                    bubbleSort(array);
                                } catch (Exception e) {}
                                break;
                            case 1:
                                // Selection sort
                                System.out.println("Selection sort");
                                try {
                                    selectionSort(array);
                                } catch(Exception e) {}
                                break;
                            case 2:
                                // Insertion sort
                                System.out.println("Insertion sort");
                                try {
                                    insertionSort(array);
                                } catch (Exception e) {}
                                break;
                            case 3:
                                // Merge sort
                                System.out.println("Merge sort");
                                try {
                                    mergeSort(array);
                                } catch (Exception e) {}
                                break;
                            case 4:
                                // Quicksort
                                System.out.println("Quicksort");
                                try {
                                    quickSort(array);
                                } catch (Exception e) {}
                                break;
                        }
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

        private void fillArray(int[] a) {
            for(int i = 0; i < a.length; i++)
                a[i] = r.nextInt(MAX_VALUE);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.black);

            int offset = 50;
            int defaultw = 25;
            int defaultx = (this.getWidth()/2) - ((array.length-1)*defaultw)/2;
            int defaulty = this.getHeight()/2;

            for(int i = 0; i < array.length; i++) {
                //g.fillRect(defaultx+i*defaultw, defaulty-array[i], defaultw, array[i]);
                g.drawLine(i+offset, MAX_VALUE+offset, i+offset, MAX_VALUE+offset - array[i]);
            }
        }

        private void delay() throws InterruptedException {
            repaint();
            Thread.sleep(1);
            if(stopThread) {
                stopThread = false;
                throw new RuntimeException();
            }
        }

        private void bubbleSort(int[] a) throws InterruptedException {
            int x, y, temp;
            for(x = 0; x < a.length; x++)
            {
                for(y = 0; y < a.length - 1; y++)
                {
                    if(a[y] > a[y+1])
                    {
                        temp = a[y];
                        a[y] = a[y+1];
                        a[y+1] = temp;
                        delay();
                    }
                }
            }
        }

        private void selectionSort(int[] a) throws InterruptedException {
            for(int x = 0; x < a.length; x++)
            {
                int iMin = x;  // index of smallest value in a
                for(int y = x; y < a.length; y++)
                {
                    if(a[y] < a[iMin])
                        iMin = y;
                }
                int temp = a[x];
                a[x] = a[iMin];
                a[iMin] = temp;
                delay();
            }
        }

        private void insertionSort(int[] a) throws InterruptedException {
            int x, y, value;
            for(x = 1; x < a.length; x++) {
                value = a[x];
                for(y = x; y > 0 && value < a[y - 1]; y--) {
                    a[y] = a[y - 1];
                }
                a[y] = value;
                delay();
            }
        }

        private void mergeSort(int a[]) {
            System.out.println("Splitting " + Arrays.toString(a));

            if(a.length > 1) {
                // split (divide)
                int mid = a.length / 2;
                int[] lefthalf = new int[mid];
                int[] righthalf = new int[mid+a.length%2];

                for(int i = 0; i < mid; i++)
                    lefthalf[i] = a[i];

                for(int i = 0, j = mid; i < mid+a.length%2; i++, j++)
                    righthalf[i] = a[j];

                mergeSort(lefthalf);
                mergeSort(righthalf);

                int i = 0, j = 0, k = 0;

                // merge (conquer)
                while(i < lefthalf.length && j < righthalf.length) {
                    if(lefthalf[i] < righthalf[j]) {
                        a[k] = lefthalf[i++];
                    } else {
                        a[k] = righthalf[j++];
                    }
                    k++;
                }

                while(i < lefthalf.length) {
                    a[k++] = lefthalf[i++];
                }

                while(j < righthalf.length) {
                    a[k++] = righthalf[j++];
                }
            }
            System.out.println("Merging " + Arrays.toString(a));
        }

        private void quickSort(int a[]) {
            quickSort(a, 0, a.length-1);
        }

        private void quickSort(int a[], int first, int last) {
            if(first < last) {
                int splitpoint = partition(a, first, last);
                quickSort(a, first, splitpoint-1);
                quickSort(a, splitpoint+1, last);
            }
        }

        private int partition(int a[], int first, int last) {
            int pivotvalue = a[first];

            int leftmark = first+1;
            int rightmark = last;

            boolean done = false;
            while(!done) {
                while(leftmark <= rightmark && a[leftmark] <= pivotvalue)
                    leftmark++;
                while(a[rightmark] >= pivotvalue && rightmark >= leftmark)
                    rightmark--;

                if(rightmark < leftmark) {
                    done = true;
                }
                else {
                    int temp = a[leftmark];
                    a[leftmark] = a[rightmark];
                    a[rightmark] = temp;
                }
            }

            int temp = a[first];
            a[first] = a[rightmark];
            a[rightmark] = temp;

            return rightmark;
        }
    }
}