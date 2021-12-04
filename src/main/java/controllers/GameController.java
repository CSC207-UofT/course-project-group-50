package controllers;

import entities.Player;
import usecases.BankManager;
import usecases.BoardManager;
import usecases.BoardOutputBoundary;
import usecases.PropertyManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameController implements Serializable, BoardOutputBoundary {
    private final BoardManager boardManager;
    private final BankManager bankManager;
    private final PropertyManager propertyManager;
    private final long id;
    private final String filepath;
    private final int netWorthGoal;
    private final ArrayList<String> usernames;
    private ArrayList<Integer> order;
    private final UI ui;

    public GameController(UI ui) {
        this.ui = ui;
        this.id = new Random().nextLong();
        this.filepath = "";
        this.usernames = new ArrayList<>();
        this.netWorthGoal = 5000;
        this.bankManager = new BankManager();
        this.propertyManager = new PropertyManager();
        this.boardManager = new BoardManager(this.bankManager, this.propertyManager, this);
        this.order = new ArrayList<>();
    }

    public void runPlayerSetUp(List<String> usernames){
        for(String s : usernames) {
            boardManager.addPlayer(s);
        }
    }

    public void runGame() throws InterruptedException {
        // this checks if the game instance is a new one or is loaded from a file. If it is loaded from a file then
        // the controllers.GameController object will have a non-empty this.order array list from the previous game,
        // and so the code will go straight into the game loop. If this.order is empty, then it's a new game, so the
        // if block generates the order for the game.
        if (this.order.size() == 0) {
            this.order = generateOrder();
            this.ui.printMessage("The playing order is:");
            for(int i : this.order) {
                this.ui.printMessage(this.boardManager.getPlayers().get(i).getUsername());
            }
            this.ui.printMessage("Let the game begin! \n");
            TimeUnit.SECONDS.sleep(2);
        }
        // TODO: handle what happens if all players go bankrupt but there are no winners
        // TODO: Remove all instances of boardManager.getPlayers() because cannot access entities in this class
         while (!isWinner()) {
            for (int i : this.order) {
                // TODO: Delegate out to helper because this method is large already
                if(!this.boardManager.getPlayers().get(i).isBankrupt()) {
                    // For formatting
                    this.ui.printMessage("\n");
                    this.boardManager.runTurn(i);

                    TimeUnit.SECONDS.sleep(2);
                }
            }
            updateBankruptcy();
            printCurrentStatistics();
         }
         List<Player> winners = getWinners();
         this.ui.printMessage("Game over! The winner(s) are: ");
         // TODO: Can't access player here
         for(Player player : winners) {
             this.ui.printMessage(player.getUsername());
         }
        this.ui.printMessage("Thanks for playing!");
    }

    // TODO: Move to GameSetUp?
    public ArrayList<Integer> generateOrder() {
        ArrayList<Integer> order = new ArrayList<>();
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

    public List<Player> getWinners() {
        List<Player> winners = new ArrayList<>();
        for(Player p : this.boardManager.getPlayers()) {
            if(p.getNetWorth() >= this.netWorthGoal) {
                winners.add(p);
            }
        }
        return winners;
    }

    public void updateBankruptcy() {
        for(Player p: this.boardManager.getPlayers()) {
            if(p.getCash() < -500 || p.getNetWorth() == 0) {
                p.setBankrupt();
                propertyManager.resetProperties(p);
            }
        }
    }

    public void printCurrentStatistics() throws InterruptedException {
        ui.printMessage("The statistics for this round are: ");
        for(int i: this.order) {
            Player player = this.boardManager.getPlayers().get(i);
            int cash = player.getCash();
            int netWorth = player.getNetWorth();
            ui.printMessage(player.getUsername() + ": Cash = " + cash + ", Net Worth = " + netWorth);
        }
        ui.printMessage("\n");
        TimeUnit.SECONDS.sleep(4);
    }

    public void notifyUser(String message) {
        this.ui.printMessage(message);
    }

    public String getAnyResponse(String message) {
        return this.ui.getAnyInput(message);
    }

    public String getResponse(String message, List<String> acceptedResponses) {
        return this.ui.getInput(message, acceptedResponses);
    }

    public String getResponse(List<String> messages, List<String> acceptedResponses) {
        return this.ui.getInput(messages, acceptedResponses);
    }

    public String getFilepath() {
        return this.filepath;
    }
}
