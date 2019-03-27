package jp.desktopgame.mycomponent;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        MyComponent comp = new MyComponent();
        frame.setSize(800, 600);
        frame.add(comp, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
