package usecases;

import entities.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoardManager implements Serializable {

    public static final int BOARD_SIZE = 28;
    private final List<Player> players;
    private final Board board;
    private final BankManager bankManager;
    private final PropertyManager propertyManager;
    private final BoardOutputBoundary outBound;

    public BoardManager(BankManager bankManager, PropertyManager propertyManager, BoardOutputBoundary outBound) {
        this.players = new ArrayList<>();

        Director director = new Director();
        BoardBuilder builder = new BoardBuilder();
        director.makeRegularBoard(builder);
        this.board = builder.getProduct();

        this.bankManager = bankManager;
        this.propertyManager = propertyManager;
        this.outBound = outBound;
    }

    public void addPlayer(String username) {
        Player p = new Player(username);
        players.add(p);
    }

    /**
     * Run a turn for the player whose turn it is
     * @param i Index representing the player whose turn it is
     */
    public void runTurn(int i) {
        int currRoll = roll(i);
        moveAndInteract(i, currRoll);
    }

    /**
     * Roll for player whose turn it is
     * @param i Index representing the player whose turn it is
     * @return The value that this player rolled
     */
    private int roll(int i) {
        Player currPlayer = this.players.get(i);
        Token currToken = currPlayer.getToken();
        // TODO: Process player being in jail better
        // this is necessary so that the player does not roll if they are in jail
        int currRoll = 0;
        if(!currToken.isInJail()) {
            currRoll = currPlayer.roll();
        }
        this.outBound.notifyUser(currPlayer.getUsername() +  ", you just rolled a " + currRoll + "!");
        return currRoll;
    }

    /**
     * Move the player whose turn it is to the correct tile and handle the interaction between the player and the
     * tile
     * @param i Index representing the player whose turn it is
     * @param currRoll The value that the player rolled
     */
    private void moveAndInteract(int i, int currRoll) {
        Player currPlayer = this.players.get(i);
        Token currToken = currPlayer.getToken();
        int prevLoc = currToken.getLocation();

        currToken.move(currRoll);
        // If we pass start, give the player $200
        if(prevLoc + currRoll > BOARD_SIZE) {
            this.bankManager.passStart(currPlayer);
            this.outBound.notifyUser(currPlayer.getUsername() +  ", you were given $200 for passing Start!");
        }
        Tile tokenTile = this.board.getTileAt(currToken.getLocation());
        // if we land on start tile
        if(tokenTile instanceof StartTile) {
            this.bankManager.passStart(currPlayer);
            this.outBound.notifyUser(currPlayer.getUsername() +  ", you were given $200 for passing Start!");
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
        List<String> acceptedResponses = new ArrayList<>();
        acceptedResponses.add("y");
        acceptedResponses.add("n");
        if(city.isOwned() && city.getOwner().equals(player)) {
            String response = this.outBound.getResponse("Would you like to build on your property: " +
                    city.getName() + "? Please enter Y / N.", acceptedResponses);
            if(response.equalsIgnoreCase("Y")) {
                this.outBound.notifyUser("Unfortunately, this feature has not yet been implemented.");
            } // no else case because response must be "N" or "n" so we can just go to the next move
        } else if(city.isOwned()) {
            payRent(player, city);
        } else {
            String response = this.outBound.getResponse("Would you like to buy " + city.getName() +
                    " for " + city.getPrice() + "?", acceptedResponses);
            if(response.equalsIgnoreCase("Y")) {
                boolean propertyBought = buyProperty(player, city);
                if(propertyBought) {
                    this.outBound.notifyUser("You just bought " + city.getName() + "!");
                } else {
                    this.outBound.notifyUser(player.getUsername() + ", you do not have enough to buy this.");
                }
            }
            // no else case because input must be "N" or "n" so we can just go to the next move
        }
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

    public List<Tile> getBoardList(){
        return this.board.tiles;
    }

    public void payRent(Player player1, PropertyTile property){
        Player player2 = property.getOwner();
        if(player1.getCash() >= property.getRent()){
            // renter is player 2 (could be null), payee is player 1
            bankManager.payRent(player2, player1, property);
            if(property instanceof City) {
                this.outBound.notifyUser(player1.getUsername() + ", you just paid " + property.getRent() + " to "
                        + player2.getUsername());
            } else {
                this.outBound.notifyUser(player1.getUsername() + ", you just paid " + property.getRent() +
                        " to the city!");
            }
        }else{
            // they will either have to sell or declare bankruptcy
            List<String> acceptedResponses = new ArrayList<>();
            acceptedResponses.add("sell");
            acceptedResponses.add("bankrupt");

            this.outBound.notifyUser(player1.getUsername() + ", you do not have enough to pay.");
            this.outBound.notifyUser("You can either sell a property or declare bankruptcy.");
            String response = this.outBound.getResponse("Please enter either \"sell\" or \"bankrupt\"",
                    acceptedResponses);
            // make sure they own at least one property to sell
            if(response.equals("sell") && propertyManager.propertiesOwnedByPlayer(player1).size() != 0){
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
        String propertyString = this.outBound.getAnyResponse("Which property would you like to sell?");
        // TODO: Check if property is valid?
        PropertyTile propToSell = propertyManager.stringToPropertyTile(propertyString);
        sellProperty(player1, propToSell);
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
        // TODO: Does this code work?
        Player player2;
        String player2String = this.outBound.getAnyResponse("Please enter the name of the player you " +
                "wish to trade with.");

        for(Player player: this.players){
            if(player.getUsername().equals(player2String)){
                player2 = player;
                propertyManager.tradeProperties(player1, player2, this.bankManager);
                return;
            }
        }
        this.outBound.notifyUser("Invalid name entered.");
        tileIsAuctionTile(player1);
    }
}
