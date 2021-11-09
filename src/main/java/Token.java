public class Token implements Movable{
    private int location;
    private String colour;
    private boolean inJail;
    private int jailDays;

    public Token() {
        this.location = 0;
        this.inJail = false;
        this.jailDays = 0;
    }

    public Token(String colour) {
        this.location = 0;
        this.colour = colour;
    }

    public void move(int tilesToAdvance) {
        this.location = (this.location + tilesToAdvance) % BoardManager.BOARD_SIZE;
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

}
