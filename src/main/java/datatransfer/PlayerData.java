package datatransfer;

import java.awt.geom.Point2D;

public class PlayerData {
    public String username;
    public int number;
    public int cash;
    public int netWorth;
    public int location;

    public PlayerData(String username, int number, int cash, int netWorth, int location) {
        this.username = username;
        this.number = number;
        this.cash = cash;
        this.netWorth = netWorth;
        this.location = location;
    }
}
