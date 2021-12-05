package details;

import interfaceadapters.GameController;
import main.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CmdLineUI implements interfaceadapters.UI {
    public static Scanner scanner = new Scanner(System.in);

    public String getStartInfo() {
        // starts the game and gives the user three options to choose from.
        System.out.println("Welcome to Monopoly!");
        System.out.println("This is a fun and interactive 2-4 player game. " +
                "The last one standing or the first player to reach the net worth goal wins the game " +
                "and takes home the trophy!");
        System.out.println("Let the fun begin!");
        String input;
        do{
            System.out.println("Enter S to start the game, load to load an existing game, and Q to quit.");
            input = scanner.nextLine();
        } while (!input.equalsIgnoreCase("S") &&
                !input.equalsIgnoreCase("load") &&
                !input.equalsIgnoreCase("Q"));

        return input;
    }

    public List<String> getPlayerNames() {
        // Set a default number here so IntelliJ doesn't give warning saying this variable may not
        // be initialized
        int numPlayers;
        List<String> usernames = new ArrayList<>();

        do {
            System.out.println("How many players do you want? Please pick a number from 2-4 (inclusive). ");
            // TODO: implement try/catch for data sanitation here.
            // For now, we assume the user enters an int as they should.
            numPlayers = Integer.parseInt(scanner.nextLine());
        } while(numPlayers < 2 || numPlayers > 4);

        for (int i = 1; i <= numPlayers; i++) {
            System.out.println("Input the username for Player " + i + ". ");
            usernames.add(scanner.nextLine());
        }
        return usernames;
    }

    public GameController loadGame() {
        String filename = null;
        int item = 0;
        do{
            try{
                System.out.println("Please enter the name of the file as it appears in the game directory:");
                filename = scanner.nextLine();
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
        return Main.load(filename);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String getAnyInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public String getInput(String message, List<String> acceptedResponses) {
        List<String> messages = new ArrayList<>();
        messages.add(message);
        return getInput(messages, acceptedResponses);
    }

    public String getInput(List<String> messages, List<String> acceptedResponses) {
        String input;
        do{
            // Display all messages to the user
            for(String m : messages) {
                System.out.println(m);
            }
            // Get the users input
            input = scanner.nextLine();
        // by the preconditions of this method, each element of acceptedResponses is lowercase
        } while(!acceptedResponses.contains(input.toLowerCase()));
        return input;
    }

    // TODO: Method is currently never used, but it will be soon, but it will need to be modified.
//    public void quitGame(GameController gc) {
//        String input;
//        if (gc != null) {
//            do {
//                System.out.println("Would you like to save the game? Enter Y or N:");
//                input = scanner.nextLine();
//            }
//            while (!Objects.equals(input, "Y") && !Objects.equals(input, "N"));
//            if (input.equals("Y")) {
//                System.out.println("Please enter the filename you would like to save the game as:");
//                input = scanner.nextLine();
//                Main.save(gc, input);
//            }
//        } System.out.println("You have successfully quit the game!");
//    }
//
}
