import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel {
    static public PlayerChecker BoardPositions[][] = new PlayerChecker[8][8];
    static public PlayerChecker TempPositions[][] = new PlayerChecker[8][8];
    static private boolean playerMove = true;
    static private int last_x;
    static private int last_y;
    static private int r = 233;
    static private int g = 8;
    static private int b = 8;
    static private boolean gameOver = false;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(198, 16, 16));

        if (!gameOver) {
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

                            if (BoardPositions[i][j].isKing) { // draw the halo icon if is king
                                g2.setColor(new Color(231, 238, 0));
                                g2.fillOval(BoardPositions[i][j].xpos + 25, BoardPositions[i][j].ypos + 16, 20, 15);
                            }
                        } else {
                            g2.setColor(new Color(108, 108, 108));
                            g2.fillOval(BoardPositions[i][j].xpos, BoardPositions[i][j].ypos + 8, 70, 50);
                            g2.setColor(new Color(this.r, this.g, this.b));
                            g2.fillOval(BoardPositions[i][j].xpos, BoardPositions[i][j].ypos, 70, 50);

                            if (BoardPositions[i][j].isKing) { // draw the halo icon if is king
                                g2.setColor(new Color(231, 238, 0));
                                g2.fillOval(BoardPositions[i][j].xpos + 25, BoardPositions[i][j].ypos + 16, 20, 15);
                            }
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
        } else {
            g2.setColor(new Color(255, 255, 255));
            Font font = new Font("Serif", Font.PLAIN, 96);
            g2.setFont(font);
            g2.drawString("Game Over", 40, 120);

            boolean playerWon = true;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (BoardPositions[i][j] != null && BoardPositions[i][j].isComputer) {
                        playerWon = false;
                    }
                }
            }

            if (playerWon) {
                g2.drawString("You win!", 40, 220);
            } else {
                g2.drawString("You lose!", 40, 220);
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
                if (BoardPositions[i][j] != null && BoardPositions[i][j].isComputer) { // if is computer checker
                    if (i + 1 < 8 && j - 1 >= 0) { // check below to the left
                        if (BoardPositions[i + 1][j - 1] == null) {
                            possibleMovers.add(BoardPositions[i][j]);
                        }
                    }

                    if (i + 1 < 8 && j + 1 < 8) { // check below to the right
                        if (BoardPositions[i + 1][j + 1] == null) {
                            possibleMovers.add(BoardPositions[i][j]);
                        }
                    }

                    if (BoardPositions[i][j].isKing) {
                        if (i - 1 >= 0 && j - 1 >= 0) { // check above to the left
                            if (BoardPositions[i - 1][j - 1] == null) {
                                possibleMovers.add(BoardPositions[i][j]);
                            }
                        }

                        if (i - 1 >= 0 && j + 1 < 8) { // check above to the right
                            if (BoardPositions[i - 1][j + 1] == null) {
                                possibleMovers.add(BoardPositions[i][j]);
                            }
                        }
                    }
                }
            }
        }

        boolean made_move = false;

        // Check if taking a checker is possible
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (BoardPositions[i][j] != null && BoardPositions[i][j].isComputer && !made_move) {
                    if (i + 1 < 8 && j - 1 >= 0 && (BoardPositions[i + 1][j - 1] != null && !BoardPositions[i + 1][j - 1].isComputer) && (i + 2 < 8 && j - 2 >= 0) && (BoardPositions[i + 2][j - 2] == null)) { // check below to the left
                        BoardPositions[i+2][j-2] = new PlayerChecker();
                        BoardPositions[i+2][j-2].isComputer = true;
                        BoardPositions[i+2][j-2].setPosition(BoardPositions[i][j].xpos - 200, BoardPositions[i][j].ypos + 200);

                        if (BoardPositions[i][j].isKing) {
                            BoardPositions[i+2][j-2].isKing = true;
                        }

                        BoardPositions[i+1][j-1] = null;
                        BoardPositions[i][j] = null;

                        if (i + 2 == 7) { // check if was made king
                            BoardPositions[i + 2][j - 2].isKing = true;
                        }

                        made_move = true;
                    } else if (i - 2 >= 0 && j - 2 >= 0 && BoardPositions[i][j].isKing && BoardPositions[i - 1][j - 1] != null && !BoardPositions[i - 1][j - 1].isComputer) { // check up to the left
                        BoardPositions[i - 2][j - 2] = new PlayerChecker();
                        BoardPositions[i-2][j-2].isComputer = true;
                        BoardPositions[i-2][j-2].isKing = true;
                        BoardPositions[i-2][j-2].setPosition(BoardPositions[i][j].xpos - 200, BoardPositions[i][j].ypos - 200);
                        BoardPositions[i][j] = null;
                        BoardPositions[i - 1][j - 1] = null;

                        made_move = true;
                    } else if (i - 2 >= 0 && j + 2 < 8 && BoardPositions[i][j].isKing && BoardPositions[i - 1][j + 1] != null && !BoardPositions[i - 1][j + 1].isComputer) { // check up to the right
                        BoardPositions[i - 2][j + 2] = new PlayerChecker();
                        BoardPositions[i-2][j+2].isComputer = true;
                        BoardPositions[i-2][j+2].isKing = true;
                        BoardPositions[i-2][j+2].setPosition(BoardPositions[i][j].xpos + 200, BoardPositions[i][j].ypos - 200);
                        BoardPositions[i][j] = null;
                        BoardPositions[i - 1][j + 1] = null;

                        made_move = true;
                    } else if (j + 1 < 8 && i + 1 < 8) { // check below to the right
                        if (BoardPositions[i + 1][j + 1] != null && !BoardPositions[i + 1][j + 1].isComputer) {
                            if (j + 2 < 8 && i + 2 < 8) {
                                if (BoardPositions[i + 2][j + 2] == null) {
                                    BoardPositions[i+2][j+2] = new PlayerChecker();
                                    BoardPositions[i+2][j+2].isComputer = true;
                                    BoardPositions[i+2][j+2].setPosition(BoardPositions[i][j].xpos + 200, BoardPositions[i][j].ypos + 200);

                                    if (BoardPositions[i][j].isKing) {
                                        BoardPositions[i+2][j+2].isKing = true;
                                    }

                                    BoardPositions[i+1][j+1] = null;
                                    BoardPositions[i][j] = null;

                                    if (i + 2 == 7) { // check if was made king
                                        BoardPositions[i + 2][j + 2].isKing = true;
                                    }

                                    made_move = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        // Choose randomly to move forward
        if (!made_move) {
            if (possibleMovers.size() == 0) {
                gameOver = true;
            } else {
                PlayerChecker the_mover = possibleMovers.get(getRandom(possibleMovers.size()));

                int temp_x_pos = the_mover.xpos / 100 - 1;
                boolean can_go_left = true;

                if (temp_x_pos < 0) {
                    temp_x_pos = 0;
                    can_go_left = false;
                }

                if (the_mover.ypos / 100 + 1 < 8 && the_mover.xpos / 100 - 1 >= 0 && BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos] == null && can_go_left) { // move down to the left
                    BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos] = new PlayerChecker();
                    BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos].makeComputer();
                    BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos].setPosition(the_mover.xpos - 100, the_mover.ypos + 100);

                    if (BoardPositions[the_mover.ypos / 100][the_mover.xpos / 100].isKing) {
                        BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos].isKing = true;
                    }

                    if (the_mover.ypos / 100 + 1 == 7) {
                        BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos].isKing = true;
                    }
                } else if (the_mover.ypos / 100 - 1 >= 0 && the_mover.xpos / 100 - 1 >= 0 && BoardPositions[the_mover.ypos / 100 - 1][temp_x_pos] == null && can_go_left && BoardPositions[the_mover.ypos / 100][the_mover.xpos / 100].isKing) { // move up to the left
                    BoardPositions[the_mover.ypos / 100 - 1][temp_x_pos] = new PlayerChecker();
                    BoardPositions[the_mover.ypos / 100 - 1][temp_x_pos].makeComputer();
                    BoardPositions[the_mover.ypos / 100 - 1][temp_x_pos].setPosition(the_mover.xpos - 100, the_mover.ypos - 100);

                    if (BoardPositions[the_mover.ypos / 100][the_mover.xpos / 100].isKing) {
                        BoardPositions[the_mover.ypos / 100 - 1][temp_x_pos].isKing = true;
                    }

                    if (BoardPositions[the_mover.ypos / 100][the_mover.xpos / 100].isKing) {
                        BoardPositions[the_mover.ypos / 100 - 1][temp_x_pos].isKing = true;
                    }
                } else if (the_mover.ypos / 100 - 1 >= 0 && the_mover.xpos / 100 + 1 < 8 && BoardPositions[the_mover.ypos / 100 - 1][the_mover.xpos / 100 + 1] == null && BoardPositions[the_mover.ypos / 100][the_mover.xpos / 100].isKing) { // move up to the right
                    BoardPositions[the_mover.ypos / 100 - 1][the_mover.xpos / 100 + 1] = new PlayerChecker();
                    BoardPositions[the_mover.ypos / 100 - 1][the_mover.xpos / 100 + 1].makeComputer();
                    BoardPositions[the_mover.ypos / 100 - 1][the_mover.xpos / 100 + 1].setPosition(the_mover.xpos + 100, the_mover.ypos - 100);

                    if (BoardPositions[the_mover.ypos / 100][the_mover.xpos / 100].isKing) {
                        BoardPositions[the_mover.ypos / 100 - 1][the_mover.xpos / 100 + 1].isKing = true;
                    }
                } else { // move down to the right
                    BoardPositions[the_mover.ypos / 100 + 1][the_mover.xpos / 100 + 1] = new PlayerChecker();
                    BoardPositions[the_mover.ypos / 100 + 1][the_mover.xpos / 100 + 1].makeComputer();
                    BoardPositions[the_mover.ypos / 100 + 1][the_mover.xpos / 100 + 1].setPosition(the_mover.xpos + 100, the_mover.ypos + 100);

                    if (BoardPositions[the_mover.ypos / 100][the_mover.xpos / 100].isKing) {
                        BoardPositions[the_mover.ypos / 100 + 1][the_mover.xpos / 100 + 1].isKing = true;
                    }

                    if (the_mover.ypos / 100 + 1 == 7) {
                        BoardPositions[the_mover.ypos / 100 + 1][temp_x_pos].isKing = true;
                    }
                }

                BoardPositions[the_mover.ypos / 100][the_mover.xpos / 100] = null;
            }
        }
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

        // Keyboard Listener
        jf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'n') {
                    r = getRandom(255);
                    g = getRandom(255);
                    b = getRandom(255);

                    jf.revalidate();
                    jf.repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        // JF Action Listener
        jf.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if (TempPositions[e.getY() / 100][e.getX() / 100] != null) { // if the position selected is a vision square
                    BoardPositions[e.getY() / 100][e.getX() / 100] = BoardPositions[last_y][last_x];
                    BoardPositions[e.getY() / 100][e.getX() / 100].setPosition((e.getX() / 100) * 100 + 15, (e.getY() / 100) * 100 + 15);

                    if (e.getY() / 100 == 0) {
                        BoardPositions[e.getY() / 100][e.getX() / 100].isKing = true;
                    }

                    if (last_x - e.getX() / 100 == 2 && last_y - e.getY() / 100 > 0) { // left and up
                        BoardPositions[last_y - 1][last_x - 1] = null;
                    } else if (e.getX() / 100 - last_x == 2 && last_y - e.getY() / 100 > 0) { // right and up
                        BoardPositions[last_y - 1][last_x + 1] = null;
                    } else if (last_x - e.getX() / 100 == 2 && last_y - e.getY() / 100 < 0) { // left and down
                        BoardPositions[last_y + 1][last_x - 1] = null;
                    } else if (e.getX() / 100 - last_x == 2 && last_y - e.getY() / 100 < 0) { // right and down
                        BoardPositions[last_y + 1][last_x + 1] = null;
                    }

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
                        if (y - 1 >= 0 && x - 1 >= 0 && BoardPositions[y - 1][x - 1] == null) { // if the top left position is free
                            TempPositions[y - 1][x - 1] = new PlayerChecker();
                            TempPositions[y - 1][x - 1].setPosition(100 * (x - 1) + 15, 100 * (y - 1) + 15);
                        } else {    // check if can take checker to the left
                            if (y - 2 >= 0 && x - 2 >= 0 && BoardPositions[y - 2][x - 2] == null && BoardPositions[y - 1][x - 1].isComputer) {
                                TempPositions[y - 2][x - 2] = new PlayerChecker();
                                TempPositions[y - 2][x - 2].setPosition(100 * (x - 2) + 15, 100 * (y - 2) + 15);
                            }
                        }

                        if (y - 1 >= 0 && x + 1 < 8 && BoardPositions[y - 1][x + 1] == null) { // if the top right position is free
                            TempPositions[y - 1][x + 1] = new PlayerChecker();
                            TempPositions[y - 1][x + 1].setPosition(100 * (x + 1) + 15, 100 * (y - 1) + 15);
                        } else {
                            if (y - 2 >= 0 && x + 2 < 8 && BoardPositions[y - 2][x + 2] == null && BoardPositions[y - 1][x + 1].isComputer) {
                                TempPositions[y - 2][x + 2] = new PlayerChecker();
                                TempPositions[y - 2][x + 2].setPosition(100 * (x + 2) + 15, 100 * (y - 2) + 15);
                            }
                        }

                        if (BoardPositions[y][x].isKing && y + 1 < 8 && x - 1 >= 0 && BoardPositions[y + 1][x - 1] == null) { // if the bottom left position is free
                            TempPositions[y + 1][x - 1] = new PlayerChecker();
                            TempPositions[y + 1][x - 1].setPosition(100 * (x - 1) + 15, 100 * (y + 1) + 15);
                        } else {
                            if (y + 2 < 8 && x - 2 >= 0 && BoardPositions[y][x].isKing && BoardPositions[y + 2][x - 2] == null && BoardPositions[y + 1][x - 1] != null && BoardPositions[y + 1][x - 1].isComputer) {
                                TempPositions[y + 2][x - 2] = new PlayerChecker();
                                TempPositions[y + 2][x - 2].setPosition(100 * (x - 2) + 15, 100 * (y + 2) + 15);
                            }
                        }

                        if (BoardPositions[y][x].isKing && y + 1 < 8 && x + 1 < 8 && BoardPositions[y + 1][x + 1] == null) { // if the bottom right position is free
                            TempPositions[y + 1][x + 1] = new PlayerChecker();
                            TempPositions[y + 1][x + 1].setPosition(100 * (x + 1) + 15, 100 * (y + 1) + 15);
                        } else {
                            if (y + 2 < 8 && x + 2 < 8 && BoardPositions[y][x].isKing && BoardPositions[y + 2][x + 2] == null && BoardPositions[y + 1][x + 1] != null && BoardPositions[y + 1][x + 1].isComputer) {
                                TempPositions[y + 2][x + 2] = new PlayerChecker();
                                TempPositions[y + 2][x + 2].setPosition(100 * (x + 2) + 15, 100 * (y + 2) + 15);
                            }
                        }
                    }
                }

                last_x = e.getX() / 100;
                last_y = e.getY() / 100;
                jf.repaint();
                jf.validate();
                jf.revalidate();
            }
        });

        // Main Game loop
        while (!gameOver) {
            while (playerMove) {
                if (checkWin()) {
                    gameOver = true;
                    jf.validate();
                    jf.revalidate();
                    jf.repaint();
                }

                Thread.sleep(500);
            }

            if (checkWin()) {
                gameOver = true;
            }

            makeComputerMove();
            jf.repaint();

            playerMove = true;
        }
    }
}
