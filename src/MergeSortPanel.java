import java.awt.*;
import java.util.Arrays;

/**
 * Created by cullen on 31/01/15.
 */
public class MergeSortPanel extends SortPanel {
    private int[] numbers;
    private int[] helper;

    private int number;

    public MergeSortPanel() {
        this.numbers = new int[]{};
    }

    protected void sort(int[] a) {
        this.numbers = a;
        number = a.length;
        this.helper = new int[number];
        mergeSort(0, number - 1);
    }

    private void mergeSort(int low, int high) {
        if(low < high) {
            int middle = low + (high - low) / 2;
            mergeSort(low, middle);
            mergeSort(middle+1, high);
            try {
                merge(low, middle, high);
            } catch (Exception e) {};
        }
    }

    private void merge(int low, int middle, int high) throws InterruptedException {

        for(int i = low; i <= high; i++) {
            helper[i] = numbers[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        while(i <= middle && j <= high) {
            if(helper[i] <= helper[j]) {
                numbers[k] = helper[i];
                i++;
            } else {
                numbers[k] = helper[j];
                j++;
            }
            k++;
            delay(10);
        }

        while(i <= middle) {
            numbers[k] = helper[i];
            k++;
            i++;
        }
        delay(10);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int defaultx = (this.getWidth() / 2) - numbers.length / 2;
        int defaulty = (this.getHeight() / 2) - MAX_VALUE / 2;

        for (int i = 0; i < numbers.length; i++) {
            g.setColor(Color.black);
            g.drawLine(i + defaultx, MAX_VALUE + defaulty, i + defaultx, MAX_VALUE + defaulty - numbers[i]);
        }
    }
}