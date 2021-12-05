package usecases;

import entities.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoardManager implements Serializable {

    public static final int BOARD_SIZE = 24;
    private final List<Player> players;
    private final Board board;
    private final BankManager bankManager;
    private final PropertyManager propertyManager;
    private final UseCaseOutputBoundary outBound;

    public BoardManager(BankManager bankManager, PropertyManager propertyManager, UseCaseOutputBoundary outBound) {
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
     * Roll for player whose turn it is
     * @param i Index representing the player whose turn it is
     * @return An array containing the value that this player rolled and the current location of the player's token
     * (before they rolled)
     */
    public Integer[] roll(int i) {
        Player currPlayer = this.players.get(i);
        Token currToken = currPlayer.getToken();
        // TODO: Process player being in jail better
        // this is necessary so that the player does not roll if they are in jail
        int currRoll = 0;
        if(!currToken.isInJail()) {
            currRoll = currPlayer.roll();
        }
        this.outBound.notifyUser(currPlayer.getUsername() +  ", you just rolled a " + currRoll + "!");
        return new Integer[]{currRoll, currToken.getLocation()};
    }

    /**
     * Move the player whose turn it is to the correct tile and handle the interaction between the player and the
     * tile
     * @param i Index representing the player whose turn it is
     * @param currRoll The value that the player rolled
     */
    public void moveAndInteract(int i, int currRoll) {
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
        TileManagerFacade tileManager = new TileManagerFacade(outBound, this, bankManager, propertyManager);
        tokenTile.interact(currToken, tileManager);
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

    public Player stringToPlayer(String playerString){
        for(Player player: this.getPlayers()){
            if(player.getUsername().equals(playerString)){
                return player;
            }
        }
        return null;
    }

}
