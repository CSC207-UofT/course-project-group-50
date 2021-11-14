import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CmdLineUI {
    public static Scanner scanner = new Scanner(System.in);

    public void startGame(){
        System.out.println("Welcome to Monopoly!");
        System.out.println("This is a fun and interactive 2-4 player game." +
                "The last one standing or the first player to reach the net worth goal wins the game" +
                "and takes home the trophy!");
        System.out.println("Let the fun begin!");
        String input;
        do{
            System.out.println("Enter S to start the game, load to load an existing game, and Q to quit.");
            input = scanner.nextLine();
        }
        while (!Objects.equals(input, "S") && !Objects.equals(input, "load") && !Objects.equals(input, "Q"));
        if (input.equals("S")){
            GameController gc = new GameController();
            runPlayerSetup(gc);
            gc.runGame();
        }
        else if (input.equals("load")){
            loadHelper();
        }
        else{
            System.out.println("You have successfully quit the game!");
        }

    }

    private void loadHelper() {
        String filename = null;
        int item = 0;
        do{
            try{
            System.out.println("Please enter the name of the file as it appears in " +
                    System.getProperty("user.name") + "\\ Documents \\");
            filename = scanner.nextLine();
            new FileInputStream(filename);
            item = 1;
            }
            catch (IOException exception){
                exception.printStackTrace();
            }
        }
        while(item != 1);
        // added item as a dummy variable which only gets value assigned to 1 when the filename given by the user
        // is correct, unless the code goes to the catch block and item continues to have value 0
        GameController gc = Main.load(filename);
        gc.runGame();
    }

    public void runPlayerSetup(GameController controller) {
        int numPlayers;
        ArrayList<String> usernames = new ArrayList<>();

        do {
            System.out.println("How many players do you want? Please pick a number from 2-4 (inclusive). ");
            // We'll eventually need to implement try/catch for data sanitation here.
            // For now, we assume the user enters an int as they should.
            numPlayers = Integer.parseInt(scanner.nextLine());
        } while(numPlayers < 2 || numPlayers > 4);

        for (int i = 1; i <= numPlayers; i++) {
            System.out.println("Input the username for Player " + i + ". ");
            usernames.add(scanner.nextLine());
        }
        controller.runPlayerSetUp(usernames);

    }
}
