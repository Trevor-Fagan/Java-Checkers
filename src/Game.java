import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Game extends JPanel {
    static Player main_player = new Player();
    static ComputerPlayer computer_player = new ComputerPlayer();
    static public int BoardPositions[][] = new int[8][8];

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(198, 16, 16));

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

        // Draw each of the individual checkers
        for (int i = 0; i < main_player.checkers.length; i++) {
            g2.setColor(new Color(233, 8, 8));
            g2.fillOval(main_player.checkers[i].xpos, main_player.checkers[i].ypos + 8, 70, 50);
            g2.setColor(new Color(242, 120, 120));
            g2.fillOval(main_player.checkers[i].xpos, main_player.checkers[i].ypos, 70, 50);
        }

        // Draw each of the individual checkers
        for (int i = 0; i < computer_player.checkers.length; i++) {
            g2.setColor(new Color(142, 142, 142));
            g2.fillOval(computer_player.checkers[i].xpos, computer_player.checkers[i].ypos + 8, 70, 50);
            g2.setColor(new Color(198, 198, 198));
            g2.fillOval(computer_player.checkers[i].xpos, computer_player.checkers[i].ypos, 70, 50);
        }
    }
    public static void main(String args[]) {
        Game t = new Game();
        JFrame jf = new JFrame();

        // JF Setup
        jf.setTitle("Checkers - Trevor Fagan");
        jf.setSize(800, 800);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.add(t);
        jf.validate();
        t.setBackground(Color.BLACK);

        // JF Action Listener
        jf.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){

            }
        });
    }
}
