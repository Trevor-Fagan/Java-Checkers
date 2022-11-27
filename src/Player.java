public class Player {
    public PlayerChecker checkers[];

    Player () {
        this.checkers = new PlayerChecker[12];

        for (int i = 0; i < 12; i++) {
            this.checkers[i] = new PlayerChecker();
        }

        int x_offset = 0;
        int y_pos = 500;
        int curr_checker = 0;

        for (int i = 0; i < 3; i ++) {
            y_pos = 515 + i * 100;

            if (i % 2 == 0) {
                x_offset = 15;
            } else {
                x_offset = 115;
            }

            for (int j = 0; j < 4; j++) {
                checkers[curr_checker].setPosition(j * 200 + x_offset, y_pos);
                curr_checker += 1;
            }
        }
    }
}
