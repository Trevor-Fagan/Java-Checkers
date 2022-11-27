import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;

public class Game extends JPanel {
    static Player main_player = new Player();
    static ComputerPlayer computer_player = new ComputerPlayer();
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(78, 54, 185));

        // Draw the checker background board
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

        // Draw each of the individual checkerså
        g2.setColor(Color.BLACK);
        for (int i = 0; i < main_player.checkers.length; i++) {
            g2.fillOval(main_player.checkers[i].xpos, main_player.checkers[i].ypos, 70, 70);
        }

        // Draw each of the individual checkers
        g2.setColor(new Color(11, 126, 141));
        for (int i = 0; i < computer_player.checkers.length; i++) {
            g2.fillOval(computer_player.checkers[i].xpos, computer_player.checkers[i].ypos, 70, 70);
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
