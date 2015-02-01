/**
 * This class is a separate implementation of
 * the sorting algorithms used for statistics
 * purposes. Benchmarking the algorithms based
 * on the implementations in the SortPanel
 * sub-classes is unfair because of the delay.
 * @author cullen
 */
public class Sort {

    /**
     *
     * @param a
     */
    public static void bubbleSort(int[] a) {
        int x, y, temp;
        for(x = 0; x < a.length; x++)
        {
            for(y = 0; y < a.length - 1; y++)
            {
                if(a[y] > a[y+1])
                {
                    temp = a[y];
                    a[y] = a[y+1];
                    a[y+1] = temp;
                }
            }
        }
    }

    /**
     *
     * @param a
     */
    public static void selectionSort(int[] a) {
        for(int x = 0; x < a.length; x++)
        {
            int iMin = x;  // index of smallest value in a
            for(int y = x; y < a.length; y++)
            {
                if(a[y] < a[iMin])
                    iMin = y;
            }
            int temp = a[x];
            a[x] = a[iMin];
            a[iMin] = temp;
        }
    }

    /**
     *
     * @param a
     */
    public static void insertionSort(int[] a) {
        int x, y, value;
        for(x = 1; x < a.length; x++) {
            value = a[x];
            for(y = x; y > 0 && value < a[y - 1]; y--) {
                a[y] = a[y - 1];
            }
            a[y] = value;
        }
    }

    /**
     *
     * @param a
     */
    public static void mergeSort(int[] a) {
        if(a.length > 1) {
            int mid = a.length / 2;
            int[] lefthalf = new int[mid];
            int[] righthalf = new int[mid+a.length%2];

            for(int i = 0; i < mid; i++)
                lefthalf[i] = a[i];

            for(int i = 0, j = mid; i < mid+a.length%2; i++, j++)
                righthalf[i] = a[j];

            mergeSort(lefthalf);
            mergeSort(righthalf);

            int i = 0, j = 0, k = 0;

            while(i < lefthalf.length && j < righthalf.length) {
                if(lefthalf[i] < righthalf[j]) {
                    a[k] = lefthalf[i++];
                } else {
                    a[k] = righthalf[j++];
                }
                k++;
            }

            while(i < lefthalf.length) {
                a[k++] = lefthalf[i++];
            }

            while(j < righthalf.length) {
                a[k++] = righthalf[j++];
            }
        }
    }

    /**
     *
     * @param a
     */
    public static void quickSort(int a[]) {
        quickSort(a, 0, a.length-1);
    }

    /**
     *
     * @param a
     * @param first
     * @param last
     */
    private static void quickSort(int a[], int first, int last) {
        if(first < last) {
            int splitpoint = 0;
            try {
                splitpoint = partition(a, first, last);
            } catch (Exception e) {}
            quickSort(a, first, splitpoint-1);
            quickSort(a, splitpoint + 1, last);
        }
    }

    /**
     *
     * @param a
     * @param first
     * @param last
     * @return
     */
    private static int partition(int a[], int first, int last) {
        int pivotvalue = a[first];

        int leftmark = first+1;
        int rightmark = last;

        boolean done = false;
        while(!done) {
            while(leftmark <= rightmark && a[leftmark] <= pivotvalue)
                leftmark++;
            while(a[rightmark] >= pivotvalue && rightmark >= leftmark)
                rightmark--;

            if(rightmark < leftmark) {
                done = true;
            }
            else {
                int temp = a[leftmark];
                a[leftmark] = a[rightmark];
                a[rightmark] = temp;
            }
        }

        int temp = a[first];
        a[first] = a[rightmark];
        a[rightmark] = temp;

        return rightmark;
    }
}
