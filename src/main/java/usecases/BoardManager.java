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
        TileManager tileManager = new TileManager(outBound, this, bankManager, propertyManager);
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

    public Player stringToPlayer(String player_string){
        for(Player player: this.getPlayers()){
            if(player.getUsername().equals(player_string)){
                return player;
            }
        }
        return null;
    }

}
