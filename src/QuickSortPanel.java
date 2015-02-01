import java.awt.*;

/**
 * JPanel with an animation for the quick sort algorithm.
 */
public class QuickSortPanel extends SortPanel {
    protected int second_swap_pos;

    protected void sort(int[] a) {
        quickSort(a);
    }

    /**
     *
     * @param a
     */
    private void quickSort(int a[]) {
        quickSort(a, 0, a.length - 1);
    }

    /**
     *
     * @param a
     * @param first
     * @param last
     */
    private void quickSort(int a[], int first, int last) {
        if (first < last) {
            int splitpoint = 0;
            try {
                splitpoint = partition(a, first, last);
            } catch (Exception e) {
            }
            quickSort(a, first, splitpoint - 1);
            quickSort(a, splitpoint + 1, last);
        }
    }

    /**
     *
     * @param a
     * @param first
     * @param last
     * @return
     * @throws InterruptedException
     */
    private int partition(int a[], int first, int last) throws InterruptedException {
        int pivotvalue = a[first];

        int leftmark = first + 1;
        int rightmark = last;

        boolean done = false;
        while (!done) {
            while (leftmark <= rightmark && a[leftmark] <= pivotvalue)
                leftmark++;
            while (a[rightmark] >= pivotvalue && rightmark >= leftmark)
                rightmark--;

            if (rightmark < leftmark) {
                done = true;
            } else {
                int temp = a[leftmark];
                a[leftmark] = a[rightmark];
                a[rightmark] = temp;
                swap_pos = leftmark;
                delay();
            }
        }

        int temp = a[first];
        a[first] = a[rightmark];
        a[rightmark] = temp;
        second_swap_pos = rightmark;
        delay();

        return rightmark;
    }

    /**
     * Overrode paintComponent that draws an additional
     * swap tracker for the left and right marks in the
     * algorithm.
     * @param g
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x1 = 75;
        int y1 = 80;
        int y2 = getHeight() - y1;
        int y_height = getHeight() - 2 * y1;
        int x_width = getWidth() - 2 * x1;

        int x_padding = (int) Math.round((double) x_width / (double) array.length);
        int y_pixel_ratio = y_height / MAX_VALUE;

        int[] yPoints = new int[]{y2 + 15, y2 + 5, y2 + 15};
        int[] first_xPoints = new int[]{swap_pos*x_padding+x1-5, swap_pos*x_padding+x1, swap_pos*x_padding+x1+5};
        int[] second_xPoints = new int[]{second_swap_pos*x_padding+x1-5, second_swap_pos*x_padding+x1, second_swap_pos*x_padding+x1+5};
        g.setColor(Color.red);
        g.fillPolygon(first_xPoints, yPoints, 3);
        g.fillPolygon(second_xPoints, yPoints, 3);

        int x_i = x1;
        for (int i = 0; i < array.length; i++) {
            g.setColor(Color.black);
            g.drawLine(x_i, y2, x_i, y2 - (array[i] * y_pixel_ratio));
            x_i += x_padding;
        }
    }
}