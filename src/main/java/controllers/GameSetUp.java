package controllers;

import java.util.List;

public class GameSetUp {
    // takes all the user input and initializes the game controller

    // Interface for UI that allows us to inject our command line UI into this class
    public interface UI {
        String getStartInfo();
        List<String> getPlayerNames();
        GameController loadGame();
        // void runPlayerSetup(GameController gc);
        // void quitGame(GameController gc);
    }

    public void setUpGame(UI ui) throws InterruptedException {
        String input = ui.getStartInfo().toUpperCase();

        // This branch executes if the user wishes to start a game
        if (input.equals("S")) {
            GameController gc = new GameController();
            List<String> usernames = ui.getPlayerNames();
            gc.runPlayerSetUp(usernames);
            gc.runGame();
        }
        // This branch executes if a user wishes to load an existing game
        else if(input.equals("LOAD")) {
            ui.loadGame().runGame();
        }
        // The only other possible input is Q, which the user types if they wish to quit the game.
        // Nothing needs to happen here if the user wished to quit.
    }
}
