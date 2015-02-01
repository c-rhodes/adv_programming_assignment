import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.*;

public class SortAnalysis extends JPanel {
    private final int sizes[] = new int[] {1, 10, 100, 1000, 2000, 4000, 6000, 8000, 10000, 12000};

    /* data structure for storing statistics */
    private HashMap<String, ArrayList<Long>> statistics;
    // Line colors, key is same as statistics dict
    private HashMap<String, Color> colors;

    // Java has no defaultdict :(
    private ArrayList<Long> bubbleSortStats, selectionSortStats, insertionSortStats, mergeSortStats, quickSortStats;

    private JButton start, open, save;
    private JCheckBox bubbleSort, selectionSort, insertionSort, mergeSort, quickSort;

    private JFileChooser fc = new JFileChooser();

    public SortAnalysis() {
        this.setLayout(new FlowLayout());

        start = new JButton("Graph sorting algorithms");
        startActionListener startListener = new startActionListener();
        this.start.addActionListener(startListener);

        open = new JButton("Open saved plot");
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    openFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        save = new JButton("Save plot data");
        save.setEnabled(false);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    saveFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        bubbleSort = new JCheckBox("Bubble sort");
        bubbleSort.setSelected(true);
        this.add(bubbleSort);

        selectionSort = new JCheckBox("Selection sort");
        selectionSort.setSelected(true);
        this.add(selectionSort);

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
        this.add(open);
        this.add(save);
    }

    private class startActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            initDataStructures();

            long startTime, endTime;
            for (int size : sizes) {
                int a[] = new int[size];
                SortPanel.fillArray(a);

                if (bubbleSort.isSelected()) {
                    statistics.put("bubble", bubbleSortStats);
                    colors.put("bubble", Color.black);
                    startTime = System.currentTimeMillis();
                    Sort.bubbleSort(a.clone());
                    endTime = System.currentTimeMillis();
                    statistics.get("bubble").add(endTime - startTime);
                }
                if (selectionSort.isSelected()) {
                    statistics.put("selection", selectionSortStats);
                    colors.put("selection", Color.blue);
                    startTime = System.currentTimeMillis();
                    Sort.selectionSort(a.clone());
                    endTime = System.currentTimeMillis();
                    statistics.get("selection").add(endTime - startTime);
                }
                if (insertionSort.isSelected()) {
                    statistics.put("insertion", insertionSortStats);
                    colors.put("insertion", Color.red);
                    startTime = System.currentTimeMillis();
                    Sort.insertionSort(a.clone());
                    endTime = System.currentTimeMillis();
                    statistics.get("insertion").add(endTime - startTime);
                }
                if (mergeSort.isSelected()) {
                    statistics.put("merge", mergeSortStats);
                    colors.put("merge", Color.green);
                    startTime = System.currentTimeMillis();
                    Sort.mergeSort(a.clone());
                    endTime = System.currentTimeMillis();
                    statistics.get("merge").add(endTime - startTime);
                }
                if (quickSort.isSelected()) {
                    statistics.put("quick", quickSortStats);
                    colors.put("quick", Color.orange);
                    startTime = System.currentTimeMillis();
                    Sort.quickSort(a.clone());
                    endTime = System.currentTimeMillis();
                    statistics.get("quick").add(endTime - startTime);
                }
            }
            save.setEnabled(true);
            repaint();
        }
    }

    private void initDataStructures() {
        statistics = new HashMap<String, ArrayList<Long>>();
        colors = new HashMap<String, Color>();
        bubbleSortStats = new ArrayList<Long>();
        selectionSortStats = new ArrayList<Long>();
        insertionSortStats = new ArrayList<Long>();
        mergeSortStats = new ArrayList<Long>();
        quickSortStats = new ArrayList<Long>();
    }

    private void openFile() throws IOException {
        int result = fc.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File csv = fc.getSelectedFile();
            initDataStructures();

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(csv));
                String line;

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");

                    String algorithm = data[0];
                    if (algorithm.equals("bubble")) {
                        statistics.put(algorithm, bubbleSortStats);
                        colors.put(algorithm, Color.black);
                    }
                    else if (algorithm.equals("selection")) {
                        statistics.put(algorithm, selectionSortStats);
                        colors.put(algorithm, Color.blue);
                    }
                    else if (algorithm.equals("insertion")) {
                        statistics.put(algorithm, insertionSortStats);
                        colors.put(algorithm, Color.red);
                    }
                    else if (algorithm.equals("merge")) {
                        statistics.put(algorithm, mergeSortStats);
                        colors.put(algorithm, Color.green);
                    }
                    else if (algorithm.equals("quick")) {
                        statistics.put(algorithm, quickSortStats);
                        colors.put("quick", Color.orange);
                    }
                    else {

                    }

                    for (int i = 1; i < data.length; i++) {
                        statistics.get(algorithm).add(Long.parseLong(data[i]));
                    }
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                if (br != null) br.close();
            }
            save.setEnabled(true);
            repaint();
        }
    }

    private void saveFile() throws IOException {
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File csv = fc.getSelectedFile();
            BufferedWriter br = new BufferedWriter(new FileWriter(csv));
            StringBuilder s = new StringBuilder();
            for (Map.Entry<String, ArrayList<Long>> entry : statistics.entrySet()) {
                s.append(entry.getKey());
                s.append(",");
                for (long time : entry.getValue()) {
                    s.append(String.valueOf(time));
                    s.append(",");
                }
                s.append("\n");
            }
            br.write(s.toString());
            br.close();
        }
    }

    private Graphics2D setupGraphics(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g2;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g = setupGraphics(g);

        int x1 = 75;
        int y1 = 80;
        int x2 = getWidth() - x1;
        int y2 = getHeight() - y1;
        int y_height = getHeight() - 2 * y1;
        int x_width = getWidth() - 2 * x1;

        if (statistics != null){
            this.drawAxes(g, x1, y1, x2, y2);
            this.drawKey(g, x1, y1);
            double largestSortTime = (double) getLargestSortingTime(statistics);
            double y_pixel_ratio = y_height / largestSortTime;

            int x_label_i = x1;
            int x_label_padding = x_width / sizes.length;
            int[] xPoints = new int[sizes.length];

            for (int i = 0; i < sizes.length; i++) {
                xPoints[i] = x_label_i;
                x_label_i += x_label_padding;
            }

            for (Map.Entry<String, ArrayList<Long>> entry : statistics.entrySet()) {
                ArrayList<Long> sortTimes = entry.getValue();
                int[] yPoints = new int[sortTimes.size()];
                for (int i = 0; i < sortTimes.size(); i++) {
                    yPoints[i] = y2 - (int) ((double) sortTimes.get(i) * y_pixel_ratio);
                }
                g.setColor(colors.get(entry.getKey()));
                g.drawPolyline(xPoints, yPoints, sortTimes.size());
            }
            g.setColor(Color.black);
        }
    }

    private void drawKey(Graphics g, int x1, int y1) {
        g.drawString("Key", x1+10, y1+10);
        int x = x1+10;
        int y = y1+20;
        int width = 10;
        int height = 10;
        for (Map.Entry<String, ArrayList<Long>> entry : statistics.entrySet()) {
            String algorithm = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1) + " sort";
            g.setColor(colors.get(entry.getKey()));
            g.fillRect(x, y, width, height);
            g.drawString(algorithm, x + width * 2, y + height);
            y += 20;
        }
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
        int x_width = getWidth() - 2 * x1;
        int x_label_padding = x_width / sizes.length;
        int x_label_i = x1;
        for (int size : sizes) {
            int labelWidth = metrics.stringWidth(String.valueOf(size));
            g.drawString(String.valueOf(size), x_label_i-(labelWidth/2), y2+15);
            x_label_i += x_label_padding;
        }

        // y
        int y_height = getHeight() - 2 * y1;
        long largestSortTime = getLargestSortingTime(statistics);

        int step = (int) Math.pow(10, Math.floor(Math.log10((double) largestSortTime)));
        int y_max = ((int) largestSortTime - ((int) largestSortTime % step)) + step;

        int y_label_padding = y_height / (y_max/step);
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
}