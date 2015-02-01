
/**
 * JPanel with an animation for the insertion sort algorithm.
 */
public class InsertionSortPanel extends SortPanel {

    /**
     *
     * @param a
     * @throws InterruptedException
     */
    protected void sort(int[] a) throws InterruptedException {
        int x, y, value;
        for (x = 1; x < a.length; x++) {
            value = a[x];
            for (y = x; y > 0 && value < a[y - 1]; y--) {
                a[y] = a[y - 1];
                swap_pos = y;
                delay();
            }
            a[y] = value;
            swap_pos = y;
            delay();
        }
    }
}
