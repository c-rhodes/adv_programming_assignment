/**
 * Created by cullen on 31/01/15.
 */
public class InsertionSortPanel extends SortPanel {

    protected void sort(int[] a) throws InterruptedException {
        int x, y, value;
        for (x = 1; x < a.length; x++) {
            value = a[x];
            for (y = x; y > 0 && value < a[y - 1]; y--) {
                a[y] = a[y - 1];
            }
            a[y] = value;
            delay(100);
        }
    }
}
