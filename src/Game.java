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
    static public PlayerChecker BoardPositions[][] = new PlayerChecker[8][8];

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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (BoardPositions[i][j] != null) {
                    if (BoardPositions[i][j].isComputer) {
                        g2.setColor(new Color(142, 142, 142));
                        g2.fillOval(BoardPositions[i][j].xpos, BoardPositions[i][j].ypos + 8, 70, 50);
                        g2.setColor(new Color(198, 198, 198));
                        g2.fillOval(BoardPositions[i][j].xpos, BoardPositions[i][j].ypos, 70, 50);
                    } else {
                        g2.setColor(new Color(233, 8, 8));
                        g2.fillOval(BoardPositions[i][j].xpos, BoardPositions[i][j].ypos + 8, 70, 50);
                        g2.setColor(new Color(242, 120, 120));
                        g2.fillOval(BoardPositions[i][j].xpos, BoardPositions[i][j].ypos, 70, 50);
                    }
                }
            }
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

        // Initialize the checkers
        int x_offset = 15;
        int y_offset = 15;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 || i == 1 || i == 2) {
                    if (i % 2 == 0) {
                        if (j % 2 == 1) {
                            BoardPositions[i][j] = new PlayerChecker();
                            BoardPositions[i][j].makeComputer();
                            BoardPositions[i][j].setPosition(100 * j + x_offset, i * 100 + y_offset);
                        }
                    } else {
                        if (j % 2 == 0) {
                            BoardPositions[i][j] = new PlayerChecker();
                            BoardPositions[i][j].makeComputer();
                            BoardPositions[i][j].setPosition(100 * j + x_offset, i * 100 + y_offset);
                        }
                    }
                } else if (i == 5 || i == 6 || i == 7) {
                    if (i % 2 == 0) {
                        if (j % 2 == 1) {
                            BoardPositions[i][j] = new PlayerChecker();
                            BoardPositions[i][j].setPosition(100 * j + x_offset, i * 100 + y_offset);
                        }
                    } else {
                        if (j % 2 == 0) {
                            BoardPositions[i][j] = new PlayerChecker();
                            BoardPositions[i][j].setPosition(100 * j + x_offset, i * 100 + y_offset);
                        }
                    }
                }
            }
        }

        // JF Action Listener
        jf.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                BoardPositions[0][0] =  BoardPositions[0][1];
                BoardPositions[0][1] = null;
            }
        });
    }
}
