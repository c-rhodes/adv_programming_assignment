import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cullen on 18/01/15.
 */
public class SortAnalysis extends JPanel {
    private Random r = new Random();
    private final int sizes[] = new int[] {1, 10, 100, 1000, 10000, 20000, 30000, 40000, 50000, 75000, 100000};

    /* data structure for storing statistics */
    HashMap<String, ArrayList<Long>> statistics = new HashMap<String, ArrayList<Long>>();

    // Java has no defaultdict :(
    ArrayList<Long> bubbleSortStats = new ArrayList<Long>();
    ArrayList<Long> selectionSortStats = new ArrayList<Long>();
    ArrayList<Long> insertionSortStats = new ArrayList<Long>();
    ArrayList<Long> mergeSortStats = new ArrayList<Long>();
    ArrayList<Long> quickSortStats = new ArrayList<Long>();

    JButton start;
    JCheckBox bubbleSort, selectionSort, insertionSort, mergeSort, quickSort;

    boolean generated = false;

    public SortAnalysis() {
        this.setLayout(new FlowLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        start = new JButton("Graph sorting algorithms");
        startActionListener startListener = new startActionListener();
        this.start.addActionListener(startListener);

        bubbleSort = new JCheckBox("Bubble sort");
        bubbleSort.setSelected(true);
        this.add(bubbleSort);

        selectionSort = new JCheckBox("Selection sort");
        selectionSort.setSelected(true);
        this.add(bubbleSort);

        insertionSort = new JCheckBox("Insertion sort");
        insertionSort.setSelected(true);
        this.add(insertionSort);

        mergeSort = new JCheckBox("Merge sort");
        mergeSort.setSelected(true);
        this.add(mergeSort);

        quickSort = new JCheckBox("Quicksort");
        quickSort.setSelected(true);
        this.add(quickSort);

        this.add(start);

        statistics.put("bubble", bubbleSortStats);
        statistics.put("selection", selectionSortStats);
        statistics.put("insertion", insertionSortStats);
        statistics.put("merge", mergeSortStats);
        statistics.put("quick", quickSortStats);
    }

    private class startActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Generating statistics");

            long startTime, endTime;

            for (int size : sizes) {
                int a[] = new int[size];
                fillArray(a);

                if (bubbleSort.isSelected()) {
                    startTime = System.currentTimeMillis();
                    Sort.bubbleSort(a.clone());
                    endTime = System.currentTimeMillis();
                    statistics.get("bubble").add(endTime-startTime);
                }
                if (selectionSort.isSelected()) {
                    startTime = System.currentTimeMillis();
                    Sort.selectionSort(a.clone());
                    endTime = System.currentTimeMillis();
                    statistics.get("selection").add(endTime-startTime);
                }
                if (insertionSort.isSelected()) {
                    startTime = System.currentTimeMillis();
                    Sort.insertionSort(a.clone());
                    endTime = System.currentTimeMillis();
                    statistics.get("insertion").add(endTime-startTime);
                }
                if (mergeSort.isSelected()) {
                    startTime = System.currentTimeMillis();
                    Sort.mergeSort(a.clone());
                    endTime = System.currentTimeMillis();
                    statistics.get("merge").add(endTime-startTime);
                }
                if (quickSort.isSelected()) {
                    startTime = System.currentTimeMillis();
                    Sort.quickSort(a.clone());
                    endTime = System.currentTimeMillis();
                    statistics.get("quick").add(endTime-startTime);
                }
                System.out.println();
            }
            /*
            Map<Integer, Long> sortedBubbleMap = new TreeMap<Integer, Long>(statistics.get("bubble"));
            for (Map.Entry<Integer, Long> entry: sortedBubbleMap.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }

            System.out.println("Algorthm\tn\ttime (ms)");
            for (Map.Entry<String, HashMap<Integer, Long>> entry : statistics.entrySet()) {
                System.out.print(entry.getKey() + "\t");
                for (Map.Entry<Integer, Long> entry2 : entry.getValue().entrySet()) {
                    System.out.println(entry2.getKey() + "\t" + entry2.getValue());
                }
            }
            */

            generated = true;
            repaint();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x1 = 75;
        int y1 = 80;
        int x2 = getWidth() - x1;
        int y2 = getHeight() - y1;

        if (generated){
            this.drawAxes(g, x1, y1, x2, y2);
            double largestSortTime = (double) getLargestSortingTime(statistics);
            // Y1 HAS TO ME MINUSED TWICE FOR TOP AND BOTTOM!!!
            double y_pixel_ratio = (y2-y1) / largestSortTime;

            System.out.println(y2);
            System.out.println(y_pixel_ratio);
            System.out.println(largestSortTime);

            int x_label_i = x1;
            int x_label_padding = x2 / sizes.length;
            int[] xPoints = new int[sizes.length];

            for (int i = 0; i < sizes.length; i++) {
                xPoints[i] = x_label_i;
                x_label_i += x_label_padding;
            }

            for (Map.Entry<String, ArrayList<Long>> entry : statistics.entrySet()) {
                int[] yPoints = new int[sizes.length];
                int i = 0;
                for (long time : entry.getValue()) {
                    yPoints[i] = y2 - (int) ((double) time * y_pixel_ratio);
                    i++;
                }
                System.out.println(Arrays.toString(xPoints));
                System.out.println(Arrays.toString(yPoints));
                g.drawPolyline(xPoints, yPoints, i);
            }
        }
    }

    private int max(int[] a) {
        int largest = a[0];
        for(int i : a) {
            if (i > largest) {
                largest = i;
            }
        }
        return largest;
    }

    private void drawAxes(Graphics g, int x1, int y1, int x2, int y2) {

        /* array size (x), sorting time (y) */
        String xLabel = "Dataset size (n)";
        String yLabel = "Sorting time (ms)";

        /* axes (x, y) */
        g.drawLine(x1, y2, x2, y2);
        g.drawLine(x1, y1, x1, y2);

        Font originalFont = g.getFont();
        FontMetrics metrics = g.getFontMetrics(originalFont); // font metrics for precise axis labelling
        int xLabelWidth = metrics.stringWidth(xLabel);
        g.drawString(xLabel, (x1/2+x2/2)-xLabelWidth/2, y2+45);  // draw x-axis label

        // draw y-axis label
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform transformedFont = new AffineTransform();
        transformedFont.rotate(-Math.PI/2);  // rotate 90 degrees (pi/2 in radians)
        Font derivedFont = originalFont.deriveFont(transformedFont);
        g2.setFont(derivedFont);

        int yLabelWidth = metrics.stringWidth(yLabel); // width of y-label
        g2.drawString(yLabel, x1-60, (y1/2+y2/2)+yLabelWidth/2); // label width used for precise axis labelling

        g2.setFont(originalFont); // restore original font

        /* number axes */

        // x
        int x_label_padding = x2 / sizes.length;
        int x_label_i = x1;
        for (int size : sizes) {
            g.drawString(String.valueOf(size), x_label_i, y2+15);
            x_label_i += x_label_padding;
        }

        // y
        long largestSortTime = getLargestSortingTime(statistics);

        int step = (int) Math.pow(10, Math.floor(Math.log10((double) largestSortTime))-1);
        int y_max = ((int) largestSortTime - ((int) largestSortTime % step)) + step;

        int y2_rounded_down = y2 - (y2 % (int) Math.pow(10, Math.floor(Math.log10((double) y2))));

        int y_label_padding = y2_rounded_down / (y_max/step);
        int y_label_i = y2;
        for (int i = 0; i <= y_max; i += step) {
            g.drawString(String.valueOf(i), x1-40, y_label_i);
            y_label_i -= y_label_padding;
        }
    }

    private long getLargestSortingTime(Map<String, ArrayList<Long>> stats) {
        long largest = 0;
        for (Map.Entry<String, ArrayList<Long>> entry : stats.entrySet()) {
            for (long time : entry.getValue()) {
                if (time > largest) {
                    largest = time;
                }
            }
        }
        return largest;
    }

    private void fillArray(int a[]) {
        for (int i = 0; i < a.length; i++) {
            a[i] = r.nextInt(100);
        }
    }
}