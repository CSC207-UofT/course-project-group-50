package entities;

import java.io.Serializable;

public class Token implements Movable, Serializable {
    private int location;
    private boolean inJail;
    private int jailDays;
    private final Player player;
    private ConstantsOutputBoundary constOutBound;

    public Token(Player player, ConstantsOutputBoundary constOutBound) {
        this.location = 0;
        this.inJail = false;
        this.jailDays = 0;
        this.player = player;
        this.constOutBound = constOutBound;
    }

    public Token(String colour, Player player) {
        this.location = 0;
        this.player = player;
    }

    /**
     * Moves the token forward on the board by tilesToAdvance tiles
     *
     * @param tilesToAdvance the number of tiles to move forward
     */
    public void move(int tilesToAdvance) {
        int a = location + tilesToAdvance;
        int b = constOutBound.getBoardSize();
        // Java behaves weird with negative numbers and mod so we need to use this formula instead
        // so that we always get a positive remainder
        location = (a % b + b) % b;
    }

    public int getLocation() {
        return this.location;
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
