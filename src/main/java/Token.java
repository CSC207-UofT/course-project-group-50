import java.io.Serializable;

public class Token implements Movable, Serializable {
    private int location;
    private String colour;
    private boolean inJail;
    private int jailDays;
    private Player player;

    public Token(Player player) {
        this.location = 0;
        this.inJail = false;
        this.jailDays = 0;
        this.player = player;
    }

    public Token(String colour, Player player) {
        this.location = 0;
        this.colour = colour;
        this.player = player;
    }

    public void move(int tilesToAdvance) {
        BoardManager bm = new BoardManager();
        int a = this.location + tilesToAdvance;
        int b = BoardManager.BOARD_SIZE;
        if (a > BoardManager.BOARD_SIZE - 1) {
            bm.getBoard().getTileAt(0).interact(this);
            this.location = a - b;
        }
        else{
            this.location = a;
        }
    }

    public int getLocation() {
        return this.location;
    }

    public String getColour() {
        return this.colour;
    }

    public boolean isInJail() {
        return this.inJail;
    }

    public void setInJail(boolean isInJail) {
        this.inJail = isInJail;
    }

    public int getJailDays() {
        return this.jailDays;
    }

    public void setJailDays(int days) {
        this.jailDays = days;
    }

    public Player getPlayer() {
        return this.player;
    }
}
