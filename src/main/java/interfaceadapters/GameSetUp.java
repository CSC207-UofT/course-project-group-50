package interfaceadapters;

import java.io.*;
import java.util.*;

// takes all the user input and initializes the game controller
public class GameSetUp implements Serializable {

    private GameController gc = null;
    private final UI ui;
    private final SerializerBoundary serializerBoundary;

    /**
     * Construct a new GameSetUp object
     *
     * @param ui                 The user interface to be used
     * @param serializerBoundary The serializer to be used
     */
    public GameSetUp(UI ui, SerializerBoundary serializerBoundary) {
        this.ui = ui;
        this.serializerBoundary = serializerBoundary;
    }

    /**
     * Sets up and runs a game of Simplified Monopoly
     */
    public void setUpGame() throws IOException {
        String input = ui.getStartInfo().toUpperCase();

        // This branch executes if the user wishes to start a game
        if (input.equals("S")) {
            List<String> usernames = ui.getPlayerNames();
            ArrayList<Integer> order = generateOrder(usernames.size());
            gc = new GameController(ui, order, usernames);
            gc.runPlayerSetUp();
        }
        // This branch executes if a user wishes to load an existing game
        else if (input.equals("LOAD")) {
            gc = loadGame();
        }
        // The only other possible input is Q, which the user types if they wish to quit the game.
        // Nothing needs to happen here if the user wished to quit.
        else {
            quitGame(gc);
        }
        // Run the game
        // runGame method in GameController class throws InterruptedException, so handle that here
        if (gc != null) {
            try {
                gc.runGame();
            } catch (InterruptedException e) {
                ui.printMessage("Game loop interrupted");
            }
        }
    }

    /**
     * Generate the order in which the players roll
     *
     * @param num_players The number of players in the game
     * @return ArrayList containing integers which represent the player number from the setup
     */
    public ArrayList<Integer> generateOrder(int num_players) {
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < num_players; i++) {
            order.add(i);
        }

        Collections.shuffle(order);
        return order;
    }

    public GameController getGc() {
        return gc;
    }

    /**
     * Quit the game and save if the user wants to
     *
     * @param gc The GameController object that can be saved if the user wants
     */
    public void quitGame(GameController gc) {
        String input;

        // If gc is null, then the user is quitting before the game starts, so there is nothing to save
        if (gc != null) {
            gc.setRunning(false);
            input = ui.getInput("Would you like to save the game? Enter Y or N:", Arrays.asList("y", "n"));
            if (input.equalsIgnoreCase("Y")) {
                input = ui.getAnyInput("Please enter the filename you would like to save the game as:");
                serializerBoundary.save(gc, input);
            }
        }
        ui.printMessage("You have successfully quit the game!");
        System.exit(0);
    }

    /**
     * Interact with user for the filename of the previously saved game, and pass it to the load method
     */
    public GameController loadGame() {
        String filename = null;
        int item = 0;
        do {
            try {
                filename = ui.getAnyInput("Please enter the name of the file as it appears in the game directory:");
                new FileInputStream(filename + ".txt");
                item = 1;
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        while (item != 1);
        // added item as a dummy variable which only gets value assigned to 1 when the filename given by the user
        // is correct, unless the code goes to the catch block and item continues to have value 0
        return serializerBoundary.load(filename);
    }

}