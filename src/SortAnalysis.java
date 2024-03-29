import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * This class produces statistics for various
 * sorting algorithms and plots them to a graph.
 * @author cullen
 */
public class SortAnalysis extends JPanel {
    private List<Integer> sizes;
    /* data structure for storing statistics */
    private HashMap<String, LinkedHashMap<Integer, Long>> statistics;
    // Line colors, key is same as statistics dict
    private HashMap<String, Color> colors;

    // Value ArrayLists for statistics HashMap, Java has no defaultdict :(
    private LinkedHashMap<Integer, Long> bubbleSortStats, selectionSortStats,
            insertionSortStats, mergeSortStats, quickSortStats;

    private JProgressBar progress;
    private JButton start, open, save;
    private JCheckBox bubbleSort, selectionSort, insertionSort, mergeSort, quickSort;
    private JFileChooser fc = new JFileChooser();

    private boolean generated = false;

    public SortAnalysis() {
        this.setLayout(new FlowLayout());

        this.sizes = new ArrayList<Integer>(Arrays.asList(1, 10, 100));
        for (int i = 1000; i <= 12000; i += 1000)
            this.sizes.add(i);

        progress = new JProgressBar();
        this.add(progress);

        start = new JButton("Graph");
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

        checkActionListener checkListener = new checkActionListener();

        bubbleSort = new JCheckBox("Bubble sort");
        selectionSort = new JCheckBox("Selection sort");
        insertionSort = new JCheckBox("Insertion sort");
        mergeSort = new JCheckBox("Merge sort");
        quickSort = new JCheckBox("Quicksort");

        bubbleSort.addActionListener(checkListener);
        selectionSort.addActionListener(checkListener);
        insertionSort.addActionListener(checkListener);
        mergeSort.addActionListener(checkListener);
        quickSort.addActionListener(checkListener);

        bubbleSort.setSelected(true);
        selectionSort.setSelected(true);
        insertionSort.setSelected(true);
        mergeSort.setSelected(true);
        quickSort.setSelected(true);

        this.add(bubbleSort);
        this.add(selectionSort);
        this.add(insertionSort);
        this.add(mergeSort);
        this.add(quickSort);

        this.add(start);
        this.add(open);
        this.add(save);
    }

    /**
     * ActionListener for the algorithm checkboxes,
     * if deselected generated is set to false so
     * to prevent repainting until triggered by the user.
     */
    private class checkActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AbstractButton b = (AbstractButton) e.getSource();
            if (!b.getModel().isSelected()) generated = false;
        }
    }

    /**
     * ActionListener that starts the analysis.
     */
    private class startActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            generated = false;
            analyseSortingAlgorithms();
        }
    }

    /**
     * Method that initialises the appropriate data structures
     * and plots the selected algorithms. The averaging is handled
     * by Sort.averageTime.
     */
    private void analyseSortingAlgorithms() {
        initDataStructures();

        progress.setIndeterminate(true);
        new Thread(new Runnable() {
            public void run() {
            final int n = 10;
            for (int size : sizes) {
                if (bubbleSort.isSelected()) {
                    statistics.put("bubble", bubbleSortStats);
                    colors.put("bubble", Color.black);
                    statistics.get("bubble").put(size, Sort.averageTime("bubble", size, n));
                }
                if (selectionSort.isSelected()) {
                    statistics.put("selection", selectionSortStats);
                    colors.put("selection", Color.blue);
                    statistics.get("selection").put(size, Sort.averageTime("selection", size, n));
                }
                if (insertionSort.isSelected()) {
                    statistics.put("insertion", insertionSortStats);
                    colors.put("insertion", Color.red);
                    statistics.get("insertion").put(size, Sort.averageTime("insertion", size, n));
                }
                if (mergeSort.isSelected()) {
                    statistics.put("merge", mergeSortStats);
                    colors.put("merge", Color.green);
                    statistics.get("merge").put(size, Sort.averageTime("merge", size, n));
                }
                if (quickSort.isSelected()) {
                    statistics.put("quick", quickSortStats);
                    colors.put("quick", Color.orange);
                    statistics.get("quick").put(size, Sort.averageTime("quick", size, n));
                }
            }
            generated = true;
            save.setEnabled(true);
            progress.setIndeterminate(false);
            repaint();
            }
        }).start();
    }

    /**
     * To redraw the data structures must be
     * reinitialised to prevent error.
     */
    private void initDataStructures() {
        statistics = new LinkedHashMap<String, LinkedHashMap<Integer, Long>>();
        colors = new LinkedHashMap<String, Color>();
        bubbleSortStats = new LinkedHashMap<Integer, Long>();
        selectionSortStats = new LinkedHashMap<Integer, Long>();
        insertionSortStats = new LinkedHashMap<Integer, Long>();
        mergeSortStats = new LinkedHashMap<Integer, Long>();
        quickSortStats = new LinkedHashMap<Integer, Long>();
    }

    /**
     * Open a csv file of statistical data
     * and plot it.
     * @throws IOException
     */
    private void openFile() throws IOException {
        int result = fc.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File csv = fc.getSelectedFile();
            initDataStructures();

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(csv));
                String line;
                String algorithm = "";

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");

                    algorithm = data[0];
                    int n = Integer.parseInt(data[1]);
                    long time = Long.parseLong(data[2]);

                    LinkedHashMap<Integer, Long> algorithmStats;
                    Color algorithmColor;
                    if (algorithm.equals("bubble")) {
                        algorithmStats = bubbleSortStats;
                        algorithmColor = Color.black;
                    }
                    else if (algorithm.equals("selection")) {
                        algorithmStats = selectionSortStats;
                        algorithmColor = Color.blue;
                    }
                    else if (algorithm.equals("insertion")) {
                        algorithmStats = insertionSortStats;
                        algorithmColor = Color.red;
                    }
                    else if (algorithm.equals("merge")) {
                        algorithmStats = mergeSortStats;
                        algorithmColor = Color.green;
                    }
                    else if (algorithm.equals("quick")) {
                        algorithmStats = quickSortStats;
                        algorithmColor = Color.orange;
                    }
                    else {
                        return;
                    }

                    if (!statistics.containsKey(algorithm))
                        statistics.put(algorithm, algorithmStats);

                    if (!colors.containsKey(algorithmColor))
                        colors.put(algorithm, algorithmColor);

                    statistics.get(algorithm).put(n, time);
                }
                sizes = new ArrayList<Integer>(statistics.get(algorithm).keySet());
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                if (br != null) br.close();
            }
            generated = true;
            save.setEnabled(true);
            repaint();
        }
    }

    /**
     * Save the data the plot represents.
     * @throws IOException
     */
    private void saveFile() throws IOException {
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File csv = fc.getSelectedFile();
            BufferedWriter br = new BufferedWriter(new FileWriter(csv));
            StringBuilder s = new StringBuilder();
            for (Map.Entry<String, LinkedHashMap<Integer, Long>> entry : statistics.entrySet()) {
                for (Map.Entry<Integer, Long> entry2 : entry.getValue().entrySet())
                {
                    s.append(entry.getKey());
                    s.append(",");
                    s.append(entry2.getKey());
                    s.append(",");
                    s.append(String.valueOf(entry2.getValue()));
                    s.append(",");
                    s.append("\n");
                }
            }
            br.write(s.toString());
            br.close();
        }
    }

    /**
     * Utility method to setup the graphics object.
     * @param g
     * @return
     */
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

        if (generated) {
            this.drawAxes(g, x1, y1, x2, y2);
            this.drawKey(g, x1, y1);
            double largestSortTime = (double) getLargestSortingTime(statistics);
            double y_pixel_ratio = y_height / largestSortTime;

            int x_label_i = x1;
            int x_label_padding = x_width / sizes.size();
            int[] xPoints = new int[sizes.size()];

            for (int i = 0; i < sizes.size(); i++) {
                xPoints[i] = x_label_i;
                x_label_i += x_label_padding;
            }

            for (Map.Entry<String, LinkedHashMap<Integer, Long>> entry : statistics.entrySet()) {
                int[] yPoints = new int[entry.getValue().keySet().size()];
                int i = 0;
                for (Map.Entry<Integer, Long> entry2 : entry.getValue().entrySet()) {
                    yPoints[i] = y2 - (int) ((double) entry2.getValue() * y_pixel_ratio);
                    i++;
                }
                g.setColor(colors.get(entry.getKey()));
                g.drawPolyline(xPoints, yPoints, yPoints.length);
            }
            g.setColor(Color.black);
        }
    }

    /**
     * Helper method to draw the graph key.
     * @param g
     * @param x1
     * @param y1
     */

    private void drawKey(Graphics g, int x1, int y1) {
        g.drawString("Key", x1+10, y1+10);
        int x = x1+10;
        int y = y1+20;
        int width = 10;
        int height = 10;
        for (Map.Entry<String, LinkedHashMap<Integer, Long>> entry : statistics.entrySet()) {
            String algorithm = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1) + " sort";
            g.setColor(colors.get(entry.getKey()));
            g.fillRect(x, y, width, height);
            g.drawString(algorithm, x + width * 2, y + height);
            y += 20;
        }
    }

    /**
     * Helper method to draw the graph axes.
     * @param g
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
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
        int x_label_padding = x_width / sizes.size();
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

    /**
     * Helper method that determines the greatest
     * sorting time in the statistics data structure
     * @param stats
     * @return
     */
    private long getLargestSortingTime(Map<String, LinkedHashMap<Integer, Long>> stats) {
        long largest = 0;
        for (Map.Entry<String, LinkedHashMap<Integer, Long>> entry : stats.entrySet()) {
            for (Map.Entry<Integer, Long> entry2 : entry.getValue().entrySet()) {
                if (entry2.getValue() > largest)
                    largest = entry2.getValue();
            }
        }
        return largest;
    }
}