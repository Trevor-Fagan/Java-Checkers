import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;

public class Game extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(78, 54, 185));

        int checker_offset = 0;
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) {
                checker_offset = 0;
            } else {
                checker_offset = 100;
            }

            for (int j = 0; j < 8; j += 2) {
                g2.fillRect(j * 100 + checker_offset, i * 100, 100, 100);
            }
        }
    }
    public static void main(String args[]) {
        Game t = new Game();
        JFrame jf = new JFrame();

        jf.setTitle("Checkers - Trevor Fagan");
        jf.setSize(800, 800);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.add(t);
        jf.validate();
    }
}
