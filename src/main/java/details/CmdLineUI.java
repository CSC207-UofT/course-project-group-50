package details;

import interfaceadapters.GameController;
import interfaceadapters.GameSetUp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class CmdLineUI implements interfaceadapters.UI, Serializable {
    public static Scanner scanner = new Scanner(System.in);

    /**
     * Start the game and give the user various options at the menu like start, load, and quit
     * @return Input from the user
     */
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

    /**
     * Interact with the user to get the number of players and the usernames
     * @return List containing the usernames of the players
     */
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




}