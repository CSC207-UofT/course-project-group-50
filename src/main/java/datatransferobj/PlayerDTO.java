package datatransferobj;

/**
 * Simple data transfer object for passing player information through layers according to Clean Architecture.
 */
public class PlayerDTO {
    public String username;
    public int number;
    public int cash;
    public int netWorth;
    public int location;

    public PlayerDTO(String username, int number, int cash, int netWorth, int location) {
        this.username = username;
        this.number = number;
        this.cash = cash;
        this.netWorth = netWorth;
        this.location = location;
    }
}
