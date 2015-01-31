import javax.swing.*;
import java.awt.*;

/**
 * Created by cullen on 18/01/15.
 */
public class SortAnalysisController {
    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            JFrame f = new JFrame();
            f.add(new SortAnalysis());
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(800, 600);
            f.setVisible(true);
            }
        });
    }
}
