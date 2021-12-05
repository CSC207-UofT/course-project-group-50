package controllers;

import entities.Player;
import usecases.BankManager;
import usecases.BoardManager;
import usecases.UseCaseOutputBoundary;
import usecases.PropertyManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameController implements Serializable, UseCaseOutputBoundary {
    private final BoardManager boardManager;
    private final BankManager bankManager;
    private final PropertyManager propertyManager;
    private final long id;
    private final String filepath;
    private final int netWorthGoal;
    private final ArrayList<String> usernames;
    private ArrayList<Integer> order;
    private final UI ui;

    public GameController(UI ui, ArrayList<Integer> order) {
        this.ui = ui;
        this.id = new Random().nextLong();
        this.filepath = "";
        this.usernames = new ArrayList<>();
        this.netWorthGoal = 5000;
        this.bankManager = new BankManager();
        this.propertyManager = new PropertyManager();
        this.boardManager = new BoardManager(this.bankManager, this.propertyManager, this);
        this.order = order;
    }

    public void runPlayerSetUp(List<String> usernames) {
        for (String s : usernames) {
            boardManager.addPlayer(s);
        }
    }

    public void runGame() throws InterruptedException {
        this.ui.printMessage("The playing order is:");
        for (int i : this.order) {
            this.ui.printMessage(this.boardManager.getPlayers().get(i).getUsername());
        }
        this.ui.printMessage("Let the game begin! \n");
        TimeUnit.SECONDS.sleep(2);
        // TODO: handle what happens if all players go bankrupt but there are no winners
        // TODO: Remove all instances of boardManager.getPlayers() because cannot access entities in this class
        while (isWinner() == null | !allBankrupt()) {
            for (int i : this.order) {
                // TODO: Delegate out to helper because this method is large already
                if (!this.boardManager.getPlayers().get(i).isBankrupt()) {
                    // For formatting
                    this.ui.printMessage("\n");
                    this.boardManager.runTurn(i);

                    TimeUnit.SECONDS.sleep(2);
                }
            }
            printCurrentStatistics();
        }
        Player winner = isWinner();
        this.ui.printMessage("Game over! The winner is: ");
        // TODO: Can't access player here
        this.ui.printMessage(winner.getUsername());
        this.ui.printMessage("Thanks for playing!");
    }

    public Player isWinner() {
        for (Player p : this.boardManager.getPlayers()) {
            if (p.getNetWorth() >= this.netWorthGoal) {
                return p;
            }
        }
        return null;
    }

    public boolean allBankrupt() {
        int num_non_bankrupt = this.order.size();
        for (Player player : this.boardManager.getPlayers()) {
            if (player.isBankrupt()) {
                num_non_bankrupt += 1;
            }
        }
        return num_non_bankrupt >= this.order.size() - 1;
    }

    public void printCurrentStatistics() throws InterruptedException {
        ui.printMessage("The statistics for this round are: ");
        for (int i : this.order) {
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

}
