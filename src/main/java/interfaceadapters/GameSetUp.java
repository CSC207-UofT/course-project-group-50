package interfaceadapters;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// takes all the user input and initializes the game controller
public class GameSetUp implements Serializable{

    private GameController gc = null;
    private UI ui;
    /**
     * Sets up and runs a game of Simplified Monopoly
     * @param ui The user interface to be used
     */
    public void setUpGame(UI ui) {
        String input = ui.getStartInfo().toUpperCase();
        this.ui = ui;


        // This branch executes if the user wishes to start a game
        if (input.equals("S")) {
            List<String> usernames = ui.getPlayerNames();
            ArrayList<Integer> order = generateOrder(usernames.size());
            Presenter presenter = new Presenter(usernames.size(), this);
            gc = new GameController(ui, order, presenter, usernames);
            gc.runPlayerSetUp();
        }
        // This branch executes if a user wishes to load an existing game
        else if(input.equals("LOAD")) {
            gc = loadGame();
        }
        // The only other possible input is Q, which the user types if they wish to quit the game.
        // Nothing needs to happen here if the user wished to quit.
        else {
            quitGame(gc);
        }
        // Run the game
        // runGame method in GameController class throws InterruptedException, so handle that here
        if (gc != null){
            try {
                gc.runGame();
            } catch(InterruptedException e) {
                ui.printMessage("Game loop interrupted");
            }
        }
    }

    /**
     * Save the game object to a file in the game directory
     * @param game The GameController object that is being saved
     * @param filename Name of the file where the object is to be written
     */
    public static void save(GameController game, String filename){
        try {
            // opens the file and writes the object into it, then closes the file and prints.
            FileOutputStream fileOut =
                    new FileOutputStream(filename + ".txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(game);
            out.close();
            fileOut.close();
            System.out.println("The game has been saved to " + filename + ".txt in the game directory.");
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Load the game object from a file in the game directory
     * @param filepath Name of the file as it appears in the game directory without the extension
     * @return The GameController object that has been retrieved
     */
    public static GameController load(String filepath){
        // initializes gc as null which can be returned if the code goes into the catch blocks.
        GameController gc = null;
        try {
            // opens the file and reads the object and allocates it to gc, then closes the file and returns gc.
            FileInputStream fileIn = new FileInputStream(filepath + ".txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gc = (GameController) in.readObject();
            in.close();
            fileIn.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        catch (ClassNotFoundException exception){
            System.out.println("controllers.GameController class not found!");
            exception.printStackTrace();
        }
        return gc;
    }

    /**
     * Generate the order in which the players roll
     * @param num_players The number of players in the game
     * @return ArrayList containing integers which represent the player number from the setup
     */

    public ArrayList<Integer> generateOrder(int num_players) {
        ArrayList<Integer> order = new ArrayList<>();
        for(int i = 0; i < num_players; i++) {
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
     * @param gc The GameController object that can be saved if the user wants
     */
    public void quitGame(GameController gc) {
        String input;

        // If gc is null, then the user is quitting before the game starts, so there is nothing to save
        if (gc != null) {
            gc.setRunning(false);
            do {
                input = ui.getAnyInput("Would you like to save the game? Enter Y or N:");
            }
            while (!Objects.equals(input, "Y") && !Objects.equals(input, "N"));
            if (input.equals("Y")) {
                input = ui.getAnyInput("Please enter the filename you would like to save the game as:");
                GameSetUp.save(gc, input);
            }
        } ui.printMessage("You have successfully quit the game!");
        System.exit(0);
    }

    /**
     * Interact with user for the filename of the previously saved game, and pass it to the load method
     */
    public GameController loadGame() {
        String filename = null;
        int item = 0;
        do{
            try{
                filename = ui.getAnyInput("Please enter the name of the file as it appears in the game directory:");
                new FileInputStream(filename + ".txt");
                item = 1;
            }
            catch (IOException exception){
                exception.printStackTrace();
            }
        }
        while(item != 1);
        // added item as a dummy variable which only gets value assigned to 1 when the filename given by the user
        // is correct, unless the code goes to the catch block and item continues to have value 0
        return load(filename);
    }

}