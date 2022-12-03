public class PlayerChecker {
    public int xpos;
    public int ypos;
    public boolean isComputer = false;
    public boolean isKing = false;

    PlayerChecker () {
        this.xpos = 0;
        this.ypos = 0;
    }

    public void setPosition(int x, int y) {
        this.xpos = x;
        this.ypos = y;
    }

    public void makeComputer () {
        this.isComputer = true;
    }
}
