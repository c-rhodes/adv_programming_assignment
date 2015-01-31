/**
 * Created by cullen on 31/01/15.
 */

public class QuickSortPanel extends SortPanel {

    protected void sort(int[] a) {
        quickSort(a);
    }

    private void quickSort(int a[]) {
        quickSort(a, 0, a.length - 1);
    }

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
                delay(10);
            }
        }

        int temp = a[first];
        a[first] = a[rightmark];
        a[rightmark] = temp;
        delay(10);

        return rightmark;
    }
}