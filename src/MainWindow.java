import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Cullen Rhodes
 * 13116683
 */

public class MainWindow extends JFrame {
    private JMenuBar menubar;
    private JMenu fileMenu, helpMenu;
    private JMenuItem exitItem, aboutItem;

    public MainWindow() {
        super("Sorting Algorithms Animated");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {};

        this.setLayout(new GridBagLayout());

        menubar = new JMenuBar();
        this.setJMenuBar(menubar);

        fileMenu = new JMenu("File");
        menubar.add(fileMenu);

        helpMenu = new JMenu("Help");
        menubar.add(helpMenu);

        aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        fileMenu.add(exitItem);

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
        SortAnalysis sortAnalysisPanel = new SortAnalysis();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Bubble Sort", bubbleSortPanel);
        tabbedPane.addTab("Selection Sort", selectionSortPanel);
        tabbedPane.addTab("Insertion Sort", insertionSortPanel);
        tabbedPane.addTab("Merge Sort", mergeSortPanel);
        tabbedPane.addTab("Quicksort", quickSortPanel);
        tabbedPane.addTab("Analysis", sortAnalysisPanel);

        this.add(tabbedPane, controlBarPanel_c);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }
}