package details;

import interfaceadapters.GameController;
import interfaceadapters.SerializerBoundary;

import java.io.*;

public class GameStateSerializer implements SerializerBoundary {

    private CmdLineUI ui = new CmdLineUI();

    /**
     * Save the game object to a file in the game directory
     * @param game The GameController object that is being saved
     * @param filename Name of the file where the object is to be written
     */
    public void save(GameController game, String filename){
        try {
            // opens the file and writes the object into it, then closes the file and prints.
            FileOutputStream fileOut =
                    new FileOutputStream(filename + ".txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(game);
            out.close();
            fileOut.close();
            ui.printMessage("The game has been saved to " + filename + ".txt in the game directory.");
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
    public GameController load(String filepath){
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
            ui.printMessage("GameController class not found!");
            exception.printStackTrace();
        }
        return gc;
    }
}
