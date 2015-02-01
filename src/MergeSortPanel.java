
/**
 * JPanel with an animation for the merge sort algorithm.
 */
public class MergeSortPanel extends SortPanel {
    private int[] array;
    private int[] scratch;

    private int number;

    public MergeSortPanel() {
        this.array = new int[]{};
    }

    /**
     *
     * @param a
     */
    protected void sort(int[] a) {
        this.array = a;
        number = a.length;
        this.scratch = new int[number];
        mergeSort(0, number - 1);
    }

    /**
     *
     * @param low
     * @param high
     */
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

    /**
     *
     * @param low
     * @param middle
     * @param high
     * @throws InterruptedException
     */
    private void merge(int low, int middle, int high) throws InterruptedException {

        for(int i = low; i <= high; i++) {
            scratch[i] = array[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        while(i <= middle && j <= high) {
            if(scratch[i] <= scratch[j]) {
                array[k] = scratch[i++];
            } else {
                array[k] = scratch[j++];
            }
            swap_pos = k;
            k++;
            delay();
        }

        while(i <= middle) {
            array[k] = scratch[i];
            swap_pos = k;
            k++; i++;
        }
        delay();
    }
}