import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        GameController gc = new GameController();
        CmdLineUI ui = new CmdLineUI();
        ui.startGame();
    }

    public static void save(GameController game, String filename){
        try {
            String name = System.getProperty("user.home") + "\\" + "Documents" + "\\" + filename + ".txt";
            FileOutputStream fileOut =
                    new FileOutputStream(name);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(game);
            out.close();
            fileOut.close();
            System.out.println("The game has been saved to " + name);
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public static GameController load(String filepath){
        GameController gc = null;
        try {
            String name = System.getProperty("user.home") + "\\" + "Documents" + "\\" + filepath + ".txt";
            FileInputStream fileIn = new FileInputStream(name);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gc = (GameController) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException exception) {
            exception.printStackTrace();
    }
        catch (ClassNotFoundException exception){
            System.out.println("GameController class not found!");
            exception.printStackTrace();
        }
        return gc;
    }
}
