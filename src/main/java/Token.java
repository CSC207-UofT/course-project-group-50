public class Token implements Movable{
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
        int a = this.location + tilesToAdvance;
        int b = BoardManager.BOARD_SIZE;
        // Java behaves weird with negative numbers and mod so I needed to use this formula instead
        // so that I always get a positive remainder
        this.location = (a % b + b) % b;
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
