package interfaceadapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// takes all the user input and initializes the game controller
public class GameSetUp {
    /**
     * Sets up and runs a game of Simplified Monopoly
     * @param ui The user interface to be used
     */
    public void setUpGame(UI ui) {
        String input = ui.getStartInfo().toUpperCase();
        GameController gc;

        // This branch executes if the user wishes to start a game
        if (input.equals("S")) {

            List<String> usernames = ui.getPlayerNames();
            ArrayList<Integer> order = generateOrder(usernames.size());
            gc = new GameController(ui, order);
            gc.runPlayerSetUp(usernames);
        }
        // This branch executes if a user wishes to load an existing game
        else if(input.equals("LOAD")) {
            gc = ui.loadGame();
        }
        // The only other possible input is Q, which the user types if they wish to quit the game.
        // Nothing needs to happen here if the user wished to quit.
        else {
            // This line is included to prevent IntelliJ from giving a warning that the object gc has
            // not been initialized in the gc.runGame() line below.
            throw new RuntimeException("This code shouldn't be reached");
        }
        // Run the game
        // runGame method in GameController class throws InterruptedException, so handle that here
        try {
            gc.runGame();
        } catch(InterruptedException e) {
            ui.printMessage("Game loop interrupted");
        }
    }

    public ArrayList<Integer> generateOrder(int num_players) {
        ArrayList<Integer> order = new ArrayList<>();
        for(int i = 0; i < num_players; i++) {
            order.add(i);
        }

        Collections.shuffle(order);
        return order;
    }
}
