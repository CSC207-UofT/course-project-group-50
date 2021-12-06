package entities;

import java.io.Serializable;

public class Token implements Movable, Serializable {
    private int location;
    private String colour;
    private boolean inJail;
    private int jailDays;
    private Player player;
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
        this.colour = colour;
        this.player = player;
    }

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
