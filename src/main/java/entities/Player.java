package entities;

import java.io.Serializable;

public class Player implements Serializable {
    private String username;
    private int cash;
    private int netWorth;
    private Token token;
    private boolean bankrupt;

    /**
     * Creates a new player.
     *
     * @param username      the username of this player.
     * @param constOutBound an input boundary for entities.
     */
    public Player(String username, ConstantsOutputBoundary constOutBound) {
        this.username = username;
        this.cash = 1000;
        this.netWorth = 1000;
        this.token = new Token(this, constOutBound);
        this.bankrupt = false;
    }

    /**
     * Returns the username of this player.
     *
     * @return the username of this player.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username of this player.
     *
     * @param username the new username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the amount of cash this player has.
     *
     * @return the amount of cash this player has.
     */
    public int getCash() {
        return this.cash;
    }

    /**
     * Sets the amount of cash this player has.
     *
     * @param cash the new amount of cash of this player.
     */
    public void setCash(int cash) {
        this.cash = cash;
    }

    /**
     * Adds cash to this player's net worth.
     *
     * @param amount the amount of cash to add to this player's net worth.
     */
    public void addCash(int amount) {
        this.cash += amount;
    }

    /**
     * Subtracts cash from player's net worth.
     *
     * @param amount the amount of cash to subtract from this player's net worth.
     */
    public void subtractCash(int amount) {
        this.cash = this.cash - amount;
    }

    /**
     * Returns the net worth of this player.
     *
     * @return the net worth of this player.
     */
    public int getNetWorth() {
        return this.netWorth;
    }

    /**
     * Sets the net worth of this player.
     *
     * @param netWorth the new net worth of this player.
     */
    public void setNetWorth(int netWorth) {
        this.netWorth = netWorth;
    }

    /**
     * Adds money to this player's net worth.
     *
     * @param amount the amount added to this player's net worth.
     */
    public void addNetWorth(int amount) {
        this.netWorth += amount;
    }

    /**
     * Subtracts money from this player's net worth.
     *
     * @param amount the amount subtracted from this player's net worth.
     */
    public void subtractNetWorth(int amount) {
        this.netWorth = this.netWorth - amount;
    }

    /**
     * Returns true if and only if this player's bankrupt.
     *
     * @return true iff this player is bankrupt.
     */
    public boolean isBankrupt() {
        return this.bankrupt;
    }

    /**
     * Sets this player's status to bankruptcy.
     */
    public void setBankrupt() {
        this.bankrupt = true;
        this.netWorth = 0;
        this.cash = 0;
        this.token = null;
    }

    /**
     * Rolls the dice for this player's turn.
     *
     * @return the number that is rolled by the dice.
     */
    public int roll() {
        return (int) (Math.random() * 6 + 1);
    }

    /**
     * Returns the token for this player.
     *
     * @return the token for this player.
     */
    public Token getToken() {
        return this.token;
    }

    @Override
    public String toString() {
        return "entities.Player " + username + " – " + "Cash: $" + this.cash + ", Net Worth: $" + this.netWorth;
    }
}
