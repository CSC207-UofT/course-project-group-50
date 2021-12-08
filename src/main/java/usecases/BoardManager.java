package usecases;

import datatransferobj.PlayerDTO;
import datatransferobj.TileDTO;
import entities.*;
import exceptions.NoWinnerException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BoardManager implements Serializable {

    public static final int BOARD_SIZE = 24;
    private final List<Player> players;
    private final Board board;
    private final BankManager bankManager;
    private final PropertyManager propertyManager;
    private final UseCaseOutputBoundary outBound;
    private final GameOutputBoundary gameOutput;

    public BoardManager(BankManager bankManager, PropertyManager propertyManager, UseCaseOutputBoundary outBound, GameOutputBoundary gameOutput) throws IOException {
        this.players = new ArrayList<>();
        Director director = new Director();
        BoardBuilder builder = new BoardBuilder();
        director.makeRegularBoard(builder);
        this.board = builder.getProduct();
        this.bankManager = bankManager;
        this.propertyManager = propertyManager;
        this.outBound = outBound;
        this.gameOutput = gameOutput;
    }

    public void addPlayer(String username) {
        Player p = new Player(username, new ConstantsInputBoundary());
        players.add(p);
    }

    /**
     * Roll for player whose turn it is
     * @param i Index representing the player whose turn it is
     * @return The tile that the player's token lands on (after they rolled)
     */
    public int rollAndMove(int i) {
        Player currPlayer = players.get(i);
        Token currToken = currPlayer.getToken();
        // this is necessary so that the player does not roll if they are in jail
        int currRoll = 0;
        if(!currToken.isInJail()) {
            currRoll = currPlayer.roll();
        }
        this.outBound.notifyUser(currPlayer.getUsername() +  ", you just rolled a " + currRoll + "!");

        int prevLoc = currToken.getLocation();
        currToken.move(currRoll);
        // If we pass start, give the player $200
        if(prevLoc + currRoll > BOARD_SIZE) {
            this.bankManager.passStart(currPlayer);
            this.outBound.notifyUser(currPlayer.getUsername() +  ", you were given $200 for passing Start!");
        }
        printBoard();
        return currToken.getLocation();
    }

    /**
     * Move the player whose turn it is to the correct tile and handle the interaction between the player and the
     * tile
     * @param i Index representing the player whose turn it is
     */
    public void interactWithTile(int i) {
        Player currPlayer = this.players.get(i);
        Token currToken = currPlayer.getToken();

        Tile tokenTile = this.board.getTileAt(currToken.getLocation());
        TileManagerFacade tileManager = new TileManagerFacade(outBound, this, bankManager, propertyManager);
        tokenTile.interact(currToken, tileManager);
    }

    /**
     * Get the username of player number i
     * @param i The number of the player whose username we wish to get
     * @return Return the username of player number i
     */
    public String getPlayerUsernameFromNumber(int i) {
        return this.players.get(i).getUsername();
    }

    /**
     * @return Returns whether there is a player playing on this board who has won the game at the time
     * that this method was called.
     */
    public boolean isWinner(int netWorth) {
        return this.bankManager.anyNetworthGreater(this.players, netWorth);
    }

    /**
     * @return Returns whether there is only one non-bankrupt player remaining
     */
    public boolean onlyOneNonBankrupt() {
        int numNonBankrupt = 0;
        for(Player player: players){
            if(bankManager.checkBankruptcy(player)){
                numNonBankrupt += 1;
            }
        }
        return numNonBankrupt >= players.size() - 1;
    }

    /**
     * Determine whether the player can legally play in this turn
     * @param username The username of the player we wish to check
     * @return Whether player can legally play in this turn
     */
    public boolean canPlay(String username) {
        // A player can legally play iff they are not bankrupt
        Player player = getPlayerFromUsername(username);
        // We know player is not null because the list of usernames from GameController (which is calling this
        // method) is initialized at the same time as this.players using the same strings of usernames
        assert player != null;
        return !bankManager.checkBankruptcy(player);
    }

    /**
     * Get the Player object corresponding to a given username
     * @return The Player object corresponding to a given username, or null if none exist.
     */
    public Player getPlayerFromUsername(String username) {
        for(Player player : players) {
            if(player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Print the current statistics of the game
     * @param order The list of integers corresponding to the order that the statistics will be printed in.
     * @throws InterruptedException If the current thread is interrupted
     */
    public void printCurrentStatistics(List<Integer> order) throws InterruptedException {
        outBound.notifyUser("The statistics for this round are: ");
        for(int i: order) {
            Player player = players.get(i);
            int cash = player.getCash();
            int netWorth = player.getNetWorth();
            outBound.notifyUser(player.getUsername() + ": Cash = " + cash + ", Net Worth = " + netWorth);
        }
        outBound.notifyUser("\n");
        TimeUnit.SECONDS.sleep(4);
    }

    /**
     * Process the winner of the game
     * @param netWorthGoal The target net worth for this game
     * @throws NoWinnerException If there is no winner in this game
     */
    public void processWinner(int netWorthGoal) {
        // First check if there is an explicit winner
        for(Player player: players) {
            if(bankManager.netWorthGreater(player, netWorthGoal)) {
                processWinnerHelper(player);
            }
        }
        // If we reach this point in the code, there is no explicit winner, so check winners by default.
        // Note that there can only be one winner by default, so if there is one person that isn't bankrupt,
        // they must be the winner (since, by the preconditions, this method can only be called if there is a winner)
        for(Player player: players) {
            if(!bankManager.checkBankruptcy(player)) {
                processWinnerHelper(player);
                return;
            }
        }
        // If we reach this point in the code, there were no winners, so throw the appropriate RunTime exception.
        throw new NoWinnerException();
    }

    /**
     * Helper method for processWinner that reports the winner to the user
     * @param player The winner of the game
     */
    private void processWinnerHelper(Player player) {
        outBound.notifyUser("Game over! The winner is: ");
        outBound.notifyUser(player.getUsername());
        outBound.notifyUser("Thanks for playing!");
    }

    private void printBoard(){
        Map<String, TileDTO> boardData;
        boardData = this.board.getBoardDataTransferObj();
        Map<Integer, PlayerDTO> playerData;
        playerData = this.getPlayerDataTransferObj();
        this.gameOutput.update(boardData, playerData);
    }

    private Map<Integer, PlayerDTO> getPlayerDataTransferObj(){
        Map<Integer, PlayerDTO> playerData = new HashMap<>();
        PlayerDTO dto;

        for(int i = 0; i < this.players.size(); i++) {
            String name = this.players.get(i).getUsername();
            int number = i + 1;
            int cash = this.players.get(i).getCash();
            int netWorth = this.players.get(i).getNetWorth();
            int location1D = this.players.get(i).getToken().getLocation();
            dto = new PlayerDTO(name, number, cash, netWorth, location1D);
            playerData.put(number, dto);
        }
        return playerData;
    }

}
