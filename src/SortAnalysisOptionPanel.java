import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by cullen on 18/01/15.
 */
public class SortAnalysisOptionPanel extends JPanel {
    JButton start;
    JCheckBox bubbleSort, selectionSort, insertionSort, mergeSort, quickSort;

    public SortAnalysisOptionPanel() {
        this.setLayout(new FlowLayout());

        start = new JButton("Graph sorting algorithms");
        startActionListener startListener = new startActionListener();
        this.start.addActionListener(startListener);

        bubbleSort = new JCheckBox("Bubble sort");
        bubbleSort.setSelected(true);
        this.add(bubbleSort);

        selectionSort = new JCheckBox("Selection sort");
        selectionSort.setSelected(true);
        this.add(bubbleSort);

        insertionSort = new JCheckBox("Insertion sort");
        insertionSort.setSelected(true);
        this.add(insertionSort);

        mergeSort = new JCheckBox("Merge sort");
        mergeSort.setSelected(true);
        this.add(mergeSort);

        quickSort = new JCheckBox("Quicksort");
        quickSort.setSelected(true);
        this.add(quickSort);

        this.add(start);
    }

    private class startActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        }
    }
}