package entities;

import java.io.Serializable;

public class Player implements Serializable {
    private String username;
    private int cash;
    private int netWorth;
    private Token token;
    private boolean bankrupt;

    public Player(String username, ConstantsOutputBoundary constOutBound) {
        this.username = username;
        this.cash = 1000;
        this.netWorth = 1000;
        this.token = new Token(this, constOutBound);
        this.bankrupt = false;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCash() {
        return this.cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void addCash(int amount) {
        this.cash += amount;
    }

    public void subtractCash(int amount) {
        this.cash = this.cash - amount;
    }

    public int getNetWorth() {
        return this.netWorth;
    }

    public void setNetWorth(int netWorth) {
        this.netWorth = netWorth;
    }

    public void addNetWorth(int amount) {
        this.netWorth += amount;
    }

    public void subtractNetWorth(int amount) {
        this.netWorth = this.netWorth - amount;
    }

    public boolean isBankrupt() {
        return this.bankrupt;
    }

    public void setBankrupt() {
        this.bankrupt = true;
        this.netWorth = 0;
        this.cash = 0;
        this.token = null;
    }

    public int roll() {
        return (int)(Math.random()*6+1);
    }

    public Token getToken() {
        return this.token;
    }

    @Override
    public String toString() {
        return "entities.Player " + username + " – " + "Cash: $" + this.cash  + ", Net Worth: $" + this.netWorth;
    }
}
