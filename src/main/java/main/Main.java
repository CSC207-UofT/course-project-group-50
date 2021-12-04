package main;

import controllers.GameController;
import controllers.GameSetUp;
import details.CmdLineUI;

import java.io.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GameSetUp gameSetUp = new GameSetUp();
        CmdLineUI ui = new CmdLineUI();
        gameSetUp.setUpGame(ui);
    }

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
}
