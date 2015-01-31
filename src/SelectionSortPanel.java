/**
 * Created by cullen on 31/01/15.
 */

public class SelectionSortPanel extends SortPanel {

    protected void sort(int[] a) throws InterruptedException {
        for (int x = 0; x < a.length; x++) {
            int iMin = x;  // index of smallest value in a
            for (int y = x; y < a.length; y++) {
                if (a[y] < a[iMin])
                    iMin = y;
            }
            int temp = a[x];
            a[x] = a[iMin];
            a[iMin] = temp;
            swap_value = a[x];
            swap_pos = iMin;
            delay(100);
        }
    }
}