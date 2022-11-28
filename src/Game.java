import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

public class Game extends JPanel {
    static public PlayerChecker BoardPositions[][] = new PlayerChecker[8][8];
    static public PlayerChecker TempPositions[][] = new PlayerChecker[8][8];
    static private boolean playerMove = true;
    static private int last_x;
    static private int last_y;

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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (TempPositions[i][j] != null) {
                    g2.setColor(Color.BLUE);
                    g2.fillOval(TempPositions[i][j].xpos, TempPositions[i][j].ypos + 8, 70, 50);
                }
            }
        }
    }

    public static boolean checkWin() {
        boolean player_has_checker = false;
        boolean computer_has_checker = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (BoardPositions[i][j] != null) {
                   if (BoardPositions[i][j].isComputer) {
                       computer_has_checker = true;
                   } else {
                       player_has_checker = true;
                   }
                }
            }
        }

        return !(player_has_checker && computer_has_checker);
    }

    public static int getRandom(int cap) {
        Random rand = new Random();
        return rand.nextInt(cap);
    }

    public static void makeComputerMove() {
        List<PlayerChecker> possibleMovers = new ArrayList<PlayerChecker>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (BoardPositions[i][j] != null && BoardPositions[i][j].isComputer) {
                    if (i + 1 < 8 && j - 1 > 0) { // check below to the left
                        if (BoardPositions[i + 1][j - 1] == null) {
                            possibleMovers.add(BoardPositions[i][j]);
                        }
                    } else if (i + 1 < 8 && j + 1 < 8) { // check below to the right
                        if (BoardPositions[i + 1][j + 1] == null) {
                            possibleMovers.add(BoardPositions[i][j]);
                        }
                    }
                }
            }
        }

        // Check if taking a checker is possible
        // Check if making a king is possible
        // Choose randomly to move forward
        PlayerChecker the_mover = possibleMovers.get(getRandom(possibleMovers.size()));

        int temp_x_pos = the_mover.xpos / 100 - 1;

        if (temp_x_pos < 0) {
            temp_x_pos = 0;
        }

        System.out.println(the_mover.ypos / 100);
        System.out.println(the_mover.xpos / 100);
        System.out.println("---------");

        if (BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos] == null) {
            BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos] = new PlayerChecker();
            BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos].makeComputer();
            BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos].setPosition(the_mover.xpos - 100, the_mover.ypos + 100);
        } else {
            BoardPositions[the_mover.ypos / 100 + 1][the_mover.xpos / 100 + 1] = new PlayerChecker();
            BoardPositions[the_mover.ypos / 100 + 1][the_mover.xpos / 100 + 1].makeComputer();
            BoardPositions[the_mover.ypos / 100 + 1][the_mover.xpos / 100 + 1].setPosition(the_mover.xpos + 100, the_mover.ypos + 100);
       }

        BoardPositions[the_mover.ypos / 100][the_mover.xpos / 100] = null;
    }

    public static void main(String args[]) throws InterruptedException {
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
                if (TempPositions[e.getY() / 100][e.getX() / 100] != null) {
                    BoardPositions[e.getY() / 100][e.getX() / 100] = BoardPositions[last_y][last_x];
                    BoardPositions[e.getY() / 100][e.getX() / 100].setPosition((e.getX() / 100) * 100 + 15, (e.getY() / 100) * 100 + 15);
                    BoardPositions[last_y][last_x] = null;
                    playerMove = false;
                }

                // Cleanup the temp array
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        TempPositions[i][j] = null;
                    }
                }

                int y = e.getY() / 100;
                int x = e.getX() / 100;

                if (playerMove) {
                    if (BoardPositions[y][x] != null && !BoardPositions[y][x].isComputer) { // if is a player's checker
                        if (y - 1 > 0 && x - 1 > 0 && BoardPositions[y - 1][x - 1] == null) { // if the top left position is free
                            TempPositions[y - 1][x - 1] = new PlayerChecker();
                            TempPositions[y - 1][x - 1].setPosition(100 * (x - 1) + 15, 100 * (y - 1) + 15);
                        }

                        if (y - 1 > 0 && x + 1 < 8 && BoardPositions[y - 1][x + 1] == null) { // if the top right position is free
                            TempPositions[y - 1][x + 1] = new PlayerChecker();
                            TempPositions[y - 1][x + 1].setPosition(100 * (x + 1) + 15, 100 * (y - 1) + 15);
                        }
                    }
                }

                last_x = e.getX() / 100;
                last_y = e.getY() / 100;
                jf.repaint();
            }
        });

        // Main Game loop
        while (true) {
            while (playerMove) {
                Thread.sleep(500);
            }

            makeComputerMove();
            jf.repaint();

            playerMove = true;
        }
    }
}
