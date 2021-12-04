package usecases;

import entities.*;
import details.CmdLineUI; // TODO: remove ui from use case

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardManager implements Serializable {

    public static final int BOARD_SIZE = 28;
    private List<Player> players;
    private Map<Player, Integer> locations;
    private final Board board;
    private BankManager bankManager;
    private PropertyManager propertyManager;

    public BoardManager(BankManager bankManager, PropertyManager propertyManager) {
        this.players = new ArrayList<>();

        Director director = new Director();
        BoardBuilder builder = new BoardBuilder();
        director.makeRegularBoard(builder);
        this.board = builder.getProduct();

        this.bankManager = bankManager;
        this.propertyManager = propertyManager;
    }

    public void addPlayer(String username) {
        Player p = new Player(username);
        players.add(p);
    }

    public void runTurn(int i) {
        Player currPlayer = this.players.get(i);
        Token currToken = currPlayer.getToken();
        int prevLoc = currToken.getLocation();
        // TODO: Somehow make it so these use cases aren't printing things directly to users
        System.out.print("\n");
        // this is necessary so that the player does not roll if they are in jail
        int currRoll = 0;
        if(!currToken.isInJail()) {
            currRoll = currPlayer.roll();
            System.out.println(currPlayer.getUsername() +  ", you just rolled a " + currRoll + "!");
        }
        currToken.move(currRoll);
        // If we pass start, give the player $200
        if(prevLoc + currRoll > 28) {
            this.bankManager.passStart(currPlayer);
            System.out.println(currPlayer.getUsername() +  ", you were given $200 for passing Start!");
        }
        Tile tokenTile = this.board.getTileAt(currToken.getLocation());
        // if we land on start tile
        if(tokenTile instanceof StartTile) {
            this.bankManager.passStart(currPlayer);
            System.out.println(currPlayer.getUsername() +  ", you were given $200 for landing on Start!");
        } else if(tokenTile instanceof City) {
            tileIsCity((City) tokenTile, currPlayer);
        } else if (tokenTile instanceof PublicProperty) {
            payRent(currPlayer, (PublicProperty) tokenTile);
        } else if (tokenTile instanceof SurpriseTile){
            ((SurpriseTile) tokenTile).interact(currPlayer);
        } else if (tokenTile instanceof JailTile){
            tokenTile.interact(currToken);
        } else {
            tileIsAuctionTile(currPlayer);
        }
    }

    public void tileIsCity(City city, Player player) {
        if(city.isOwned() && city.getOwner().equals(player)) {
            System.out.println("Would you like to build on your property: " + city.getName() + "? Please enter" +
                    " Y / N.");
            String input = "";
            while(!(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N"))) {
                System.out.println("Please enter Y / N");
                input = CmdLineUI.scanner.nextLine();
            }
            if(input.equalsIgnoreCase("Y")) {
                System.out.println("Unfortunately, this feature has not yet been implemented.");
            } // no else case because input must be "N" or "n" so we can just go to the next move
        } else if(city.isOwned()) {
            payRent(player, city);
        } else {
            System.out.println("Would you like to buy " + city.getName() + " for " + city.getPrice() + "?");
            String input = "";
            while(!(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N"))) {
                System.out.println("Please enter Y / N");
                input = CmdLineUI.scanner.nextLine();
            }
            if(input.equalsIgnoreCase("Y")) {
                boolean propertyBought = buyProperty(player, city);
                if(propertyBought) {
                    System.out.println("You just bought " + city.getName() + "!");
                } else {
                    System.out.println(player.getUsername() + ", you do not have enough to buy this.");
                }
            }
            // no else case because input must be "N" or "n" so we can just go to the next move
        }
    }

    public Tile[] initializeBoard() {
        // create array of tiles
        return new Tile[BOARD_SIZE];
    }
    
    public List<Player> getPlayers() {
        return this.players;
    }

    public int numOfPlayers() {
        return this.players.size();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Player p : players) {
            s.append(p.toString());
            s.append("\n");
        }
        return s.toString();
    }

    public Board getBoard(){
        return this.board;
    }

    public List<Tile> getBoardList(){
        return this.board.tiles;
    }

    public void payRent(Player player1, PropertyTile property){
        Player player2 = property.getOwner();
        if(player1.getCash() >= property.getRent()){
            // renter is player 2 (could be null), payee is player 1
            bankManager.payRent(player2, player1, property);
            if(property instanceof City) {
                System.out.println(player1.getUsername() + ", you just paid " + property.getRent() + " to "
                        + player2.getUsername());
            } else {
                System.out.println(player1.getUsername() + ", you just paid " + property.getRent() + " to the city!");
            }
        }else{
            // they will either have to sell or declare bankruptcy
            System.out.println(player1.getUsername() + ", you do not have enough to pay.");
            System.out.println("You can either sell a property or declare bankruptcy");
            String input = "";
            while (!(input.equals("sell") || input.equals("bankrupt"))){
                System.out.println("Please enter sell or bankrupt");
                input = CmdLineUI.scanner.nextLine();
            }
            // make sure they own at least one property to sell
            if(input.equals("sell") && propertyManager.propertiesOwnedByPlayer(player1).size() != 0){
                sellRentHelper(player1, property);
            }else { // input must be "bankrupt"
                bankManager.payRent(player1, player2, property);
                bankruptPlayer(player1);
            }
        }
    }

    public boolean buyProperty(Player player, City property){
        if(player.getCash() >= property.getPrice()){
            propertyManager.buyProperty(player, property);
            bankManager.deductCostOfProperty(player, property);
            return true;
        }
        return false;
    }

    private void sellRentHelper(Player player1, PropertyTile property) {
        String property_string = "";
        while (propertyManager.stringToPropertyTile(property_string) == null){
            System.out.println(propertyManager.propertiesOwnedByPlayer(player1));
            System.out.println("Which property would you like to sell?");
            property_string = CmdLineUI.scanner.nextLine();
        }
        PropertyTile sell_property = propertyManager.stringToPropertyTile(property_string);
        sellProperty(player1, sell_property);
        payRent(player1, property);
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

    private PropertyTile getPropertyTile(Player player) {
        int location = player.getToken().getLocation();
        return (PropertyTile) this.getBoardList().get(location);
    }

    public void bankruptPlayer(Player player){
        player.setBankrupt();
        propertyManager.resetProperties(player);
    }

    public void tileIsAuctionTile(Player player1){
        Player player2;
        System.out.println("Please enter the name of the player you wish to trade with.");
        String player2_string = CmdLineUI.scanner.nextLine();
        for(Player player: this.players){
            if(player.getUsername().equals(player2_string)){
                player2 = player;
                propertyManager.tradeProperties(player1, player2, this.bankManager);
                return;
            }
        }
        System.out.println("Invalid name entered.");
        tileIsAuctionTile(player1);
    }
}
