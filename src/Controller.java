import javax.swing.*;
import java.util.Random;

/**
 * Created by cullen on 04/12/14.
 */
public class Controller {
    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow f = new MainWindow();
            }
        });
    }
}
