import javax.swing.*;

/**
 * Created by cullen on 31/01/15.
 */

public class BubbleSortPanel extends SortPanel {

    protected void sort(int[] a) throws InterruptedException {
        int x, y, temp;
        for (x = 0; x < a.length; x++) {
            for (y = 0; y < a.length - 1; y++) {
                if (a[y] > a[y + 1]) {
                    temp = a[y];
                    a[y] = a[y + 1];
                    a[y + 1] = temp;
                    swap_value = a[y];
                    swap_pos = y;
                    delay(1);
                }
            }
        }
    }
}