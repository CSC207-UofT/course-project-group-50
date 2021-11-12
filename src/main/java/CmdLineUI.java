import java.util.ArrayList;
import java.util.Scanner;

public class CmdLineUI {
    public static Scanner scanner = new Scanner(System.in);
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
