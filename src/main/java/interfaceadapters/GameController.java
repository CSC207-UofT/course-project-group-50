package interfaceadapters;

import usecases.BankManager;
import usecases.BoardManager;
import usecases.UseCaseOutputBoundary;
import usecases.PropertyManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameController implements Serializable, UseCaseOutputBoundary {
    private final BoardManager boardManager;
    private final BankManager bankManager;
    private final PropertyManager propertyManager;
    private final String filepath;
    private final int netWorthGoal;
    private final List<String> usernames;
    private ArrayList<Integer> order;
    private final UI ui;
    private Presenter presenter;
    private boolean isRunning;

    public GameController(UI ui, ArrayList<Integer> order, Presenter presenter, List<String> usernames) {
        this.ui = ui;
        this.filepath = "";
        this.usernames = usernames;
        this.netWorthGoal = 5000;
        this.bankManager = new BankManager();
        this.propertyManager = new PropertyManager();
        this.boardManager = new BoardManager(this.bankManager, this.propertyManager, this);
        this.order = order;
        this.presenter = presenter;
        this.isRunning = true;
    }

    public void runPlayerSetUp() {
        for(String s : usernames) {
            boardManager.addPlayer(s);
        }
    }

    public void runGame() throws InterruptedException {
        ui.printMessage("The playing order is:");
        for(int i : order) {
            ui.printMessage(boardManager.getPlayerUsernameFromNumber(i));
        }
        ui.printMessage("Let the game begin! \n");
        TimeUnit.SECONDS.sleep(2);
        // This loop will run as long as there is no explicit winner or no winner by default (i.e., everyone except
        // one player has gone bankrupt
        while (!boardManager.isWinner(netWorthGoal) || !boardManager.onlyOneNonBankrupt() || isRunning) {
            // guiTokenIndex  is a variable that keeps track of which token should move for the GameBoardPanel to use
            // cannot simply use i in the for loop because i comes from order, which is randomized
            int guiTokenIndex = 1;
            for (int i : order) {
                // Check if the player whose turn it is can legally continue to play the game
                if(boardManager.canPlay(usernames.get(i))) {
                    runTurn(i, guiTokenIndex);
                    guiTokenIndex++;
                }
            }
            boardManager.printCurrentStatistics(order);

            // Checks if the game is running or not, if not then the game loop ends
            if (!isRunning){return;}

        }
        // If we reach this point in the code, someone has won the game
        boardManager.processWinner(netWorthGoal);
    }

    /**
     * Run the current turn
     * @param i The index of the player whose turn it is
     * @param guiTokenIndex The index of the token on the gui that is meant to move
     * @throws InterruptedException If the current thread is interrupted
     */
    public void runTurn(int i, int guiTokenIndex) throws InterruptedException {
        ui.printMessage("\n");
        // Roll for the player
        int newLoc = boardManager.rollAndMove(i);
        // Update Game Board and move player's token to new tile
        presenter.boardPanel.updateBoard(guiTokenIndex, newLoc);
        // Make player's token interact with tile
        boardManager.interactWithTile(i);
        //TODO: If player lands on special tile and moves forward/backward, the gui does not update
        TimeUnit.SECONDS.sleep(2);
    }

    public void notifyUser(String message) {
        ui.printMessage(message);
    }

    public String getAnyResponse(String message) {
        return ui.getAnyInput(message);
    }

    public String getResponse(String message, List<String> acceptedResponses) {
        return ui.getInput(message, acceptedResponses);
    }

    public String getResponse(List<String> messages, List<String> acceptedResponses) {
        return ui.getInput(messages, acceptedResponses);
    }

    public List<String> getUsernames(){
        return usernames;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }
}
