import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import sun.misc.Sort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Arrays;

/**
 * Created by cullen on 05/01/15.
 */

public class MainWindow extends JFrame {

    public MainWindow() {
        super("Visual Representation of Sorting Algorithms");

        this.setLayout(new GridBagLayout());

        GridBagConstraints controlBarPanel_c = new GridBagConstraints();

        controlBarPanel_c.gridx = 0;
        controlBarPanel_c.gridy = 0;
        controlBarPanel_c.gridwidth = GridBagConstraints.REMAINDER;
        controlBarPanel_c.gridheight = 1;
        controlBarPanel_c.weightx = 1;
        controlBarPanel_c.weighty = 1;
        controlBarPanel_c.anchor = GridBagConstraints.FIRST_LINE_START;
        controlBarPanel_c.fill = GridBagConstraints.BOTH;
        controlBarPanel_c.insets = new Insets(4, 4, 4, 4);

        BubbleSortPanel bubbleSortPanel = new BubbleSortPanel();
        SelectionSortPanel selectionSortPanel = new SelectionSortPanel();
        InsertionSortPanel insertionSortPanel = new InsertionSortPanel();
        QuickSortPanel quickSortPanel = new QuickSortPanel();
        MergeSortPanel mergeSortPanel = new MergeSortPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Bubble Sort", bubbleSortPanel);
        tabbedPane.addTab("Selection Sort", selectionSortPanel);
        tabbedPane.addTab("Insertion Sort", insertionSortPanel);
        tabbedPane.addTab("Merge Sort", mergeSortPanel);
        tabbedPane.addTab("Quicksort", quickSortPanel);

        this.add(tabbedPane, controlBarPanel_c);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }
}