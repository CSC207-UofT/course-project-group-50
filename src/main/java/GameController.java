import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.stream.IntStream;

public class GameController implements Serializable {
    private final BoardManager boardManager;
    private final BankManager bankManager;
    private final PropertyManager propertyManager;
    private final long id;
    private final String filepath;
    private final int netWorthGoal;
    private final ArrayList<String> usernames;

    public GameController(long id, String filepath, ArrayList<String> usernames) {
        this.id = id;
        this.filepath = filepath;
        this.usernames = usernames;
        this.netWorthGoal = 5000;
        this.boardManager = new BoardManager();
        this.bankManager = new BankManager();
        this.propertyManager = new PropertyManager();
    }

    public GameController(long id, String filepath, ArrayList<String> usernames, int netWorthGoal) {
        this.id = id;
        this.filepath = filepath;
        this.usernames = usernames;
        this.netWorthGoal = netWorthGoal;
        this.boardManager = new BoardManager();
        this.bankManager = new BankManager();
        this.propertyManager = new PropertyManager();
    }

    public GameController() {
        this.id = new Random().nextLong();
        this.filepath = "";
        this.players = new ArrayList<>();
        this.netWorthGoal = 5000;
        this.boardManager = new BoardManager();
        this.bankManager = new BankManager();
        this.propertyManager = new PropertyManager();
    }

    public void runPlayerSetUp(List<String> usernames){
        for(String s : usernames) {
            boardManager.addPlayer(s);
        }
        System.out.println("\n PLAYER LIST");
        // commented this out because i'm not sure what it is meant to do
        // System.out.println(boardManager);
    }

    public void runGame() {
        runPlayerSetUp(this.usernames);
        List<Integer> order = generateOrder();

        while(!isWinner()) {
            for(int i : order) {
                Player currPlayer = this.boardManager.getPlayers().get(i);
                int currRoll = currPlayer.roll();
                currPlayer.getToken().move(currRoll);
            }
            updateBankruptcy();
        }
    }

    public List<Integer> generateOrder() {
        List<Integer> order = new ArrayList<>();
        for(int i = 0; i < this.boardManager.numOfPlayers(); i++) {
            order.add(i);
        }

        Collections.shuffle(order);
        return order;
    }

    public boolean isWinner() {
        for(Player p : this.boardManager.getPlayers()) {
            if(p.getNetWorth() >= this.netWorthGoal) {
                return true;
            }
        }
        return false;
    }

    // there may be a more efficient way to do this but im unsure how our game loop
    // will go so i'm just leaving it here
    public void updateBankruptcy() {
        for(Player p: this.boardManager.getPlayers()) {
            if(p.getCash() < -500 || p.getNetWorth() == 0) {
                p.setBankrupt();
                propertyManager.resetProperties(p);
            }
        }
    }

    public String getFilepath() {
        return this.filepath;
    }

    public void buyProperty(Player player){
        PropertyTile property = getPropertyTile(player);
        if(player.getCash() >= property.getPrice()){
            propertyManager.buyProperty(player, property);
            bankManager.deductCostOfProperty(player, property);
        }else{
            System.out.println(player.getUsername() + ", you do not have enough to buy this.");
        }

    }

    public void sellProperty(Player player){
        PropertyTile property = getPropertyTile(player);
        propertyManager.sellProperty(player, property);
        bankManager.addSellbackOfProperty(player, property);
    }
    public void sellProperty(Player player, PropertyTile property){
        propertyManager.sellProperty(player, property);
        bankManager.addSellbackOfProperty(player, property);
    }

    public void payRent(Player player1){
        PropertyTile property = getPropertyTile(player1);
        Player player2 = property.getOwner();
        if(player1.getCash() >= property.getRent()){
            bankManager.payRent(player1, player2, property);
        }else{
            // they will either have to sell or declare bankruptcy
            System.out.println(player1.getUsername() + ", you do not have enough to pay.");
            System.out.println("You can either sell a property or declare bankruptcy");
            String input = CmdLineUI.scanner.nextLine();
            if(input.equals("sell")){
                System.out.println("Which property would you like to sell?");
                String property_string = CmdLineUI.scanner.nextLine();
                if (propertyManager.stringToPropertyTile(property_string) != null){
                    PropertyTile sell_property = propertyManager.stringToPropertyTile(property_string);
                    sellProperty(player1, sell_property);
                }else{
                    System.out.println("Invalid Input");
                }
                payRent(player1);

            }
            if(input.equals("bankrupt")){
                bankManager.payRent(player1, player2, property);
                bankruptPlayer(player1);
            }else{
                System.out.println("Invalid Input");
                payRent(player1);
            }
        }
    }

    private PropertyTile getPropertyTile(Player player) {
        int location = player.getToken().getLocation();
        return (PropertyTile) boardManager.getBoardList().get(location);
    }

    public void bankruptPlayer(Player player){
        player.setBankrupt();
        propertyManager.resetProperties(player);
    }

    public void startTrade(Player player1){
        Player player2;
        System.out.println("Please enter the name of the player you wish to trade with.");
        String player2_string = CmdLineUI.scanner.nextLine();
        for(Player player: this.players){
            if(player.getUsername().equals(player2_string)){
                player2 = player;
                propertyManager.tradeProperties(player1, player2);
                break;
            }
        }
        System.out.println("Invalid name entered.");
    }




}
