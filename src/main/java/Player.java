public class Player {
    private String username;
    private int cash;
    private int netWorth;
    private Token token;
    private boolean bankrupt;

    public Player(String username) {
        this.username = username;
        this.cash = 1000;
        this.netWorth = 1000;
        this.token = new Token();
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

    public int getNetWorth() {
        return this.netWorth;
    }

    public void setNetWorth(int netWorth) {
        this.netWorth = netWorth;
    }

    public boolean isBankrupt() {
        return this.bankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public int roll() {
        return (int)(Math.random()*6+1);
    }

    @Override
    public String toString() {
        return "Player " + username + " – " + "Cash: $" + this.cash  + ", Net Worth: $" + this.netWorth;
    }
}
