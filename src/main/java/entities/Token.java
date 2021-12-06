package entities;

import java.io.Serializable;

public class Token implements Movable, Serializable {
    private int location;
    private String colour;
    private boolean inJail;
    private int jailDays;
    private Player player;
    private ConstantsOutputBoundary constOutBound;

    /**
     * Initialize this token.
     * @param player the player who represents this token.
     * @param constOutBound an input boundary.
     */
    public Token(Player player, ConstantsOutputBoundary constOutBound) {
        this.location = 0;
        this.inJail = false;
        this.jailDays = 0;
        this.player = player;
        this.constOutBound = constOutBound;
    }

    /**
     * Initialize this token.
     * @param colour the colour of this token.
     * @param player the player who represents this token.
     */
    public Token(String colour, Player player) {
        this.location = 0;
        this.colour = colour;
        this.player = player;
    }

    /**
     * Moves this token.
     * @param tilesToAdvance the number of tiles the token advances.
     */
    public void move(int tilesToAdvance) {
        int a = location + tilesToAdvance;
        int b = constOutBound.getBoardSize();
        // Java behaves weird with negative numbers and mod so we need to use this formula instead
        // so that we always get a positive remainder
        location = (a % b + b) % b;
    }

    /**
     * Returns the location of this token.
     * @return the location of this token.
     */
    public int getLocation() {
        return this.location;
    }

    /**
     * Returns the colour of this token.
     * @return the colour of this token.
     */
    public String getColour() {
        return this.colour;
    }

    /**
     * Returns whether if this token is in jail.
     * @return true iff this token is in jail.
     */
    public boolean isInJail() {
        return this.inJail;
    }

    /**
     * Sets the token to be in jail or not.
     * @param isInJail true iff the token is to be in jail.
     */
    public void setInJail(boolean isInJail) {
        this.inJail = isInJail;
    }

    /**
     * Returns the remaining days of jail time.
     * @return the remaining days of jail time.
     */
    public int getJailDays() {
        return this.jailDays;
    }

    /**
     * Sets the jail time.
     * @param days the number of days of jail time.
     */
    public void setJailDays(int days) {
        this.jailDays = days;
    }

    /**
     * Returns the player this token represents.
     * @return the player this token represents.
     */
    public Player getPlayer() {
        return this.player;
    }
}
