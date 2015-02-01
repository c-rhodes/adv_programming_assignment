
/**
 * JPanel with an animation for the selection sort algorithm.
 */
public class SelectionSortPanel extends SortPanel {

    /**
     *
     * @param a
     * @throws InterruptedException
     */
    protected void sort(int[] a) throws InterruptedException {
        for (int x = 0; x < a.length; x++) {
            int min = x;  // index of smallest value in a
            for (int y = x; y < a.length; y++) {
                if (a[y] < a[min])
                    min = y;
            }
            int temp = a[x];
            a[x] = a[min];
            a[min] = temp;
            swap_pos = min;
            delay();
        }
    }
}