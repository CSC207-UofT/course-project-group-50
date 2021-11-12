import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        long gameId = new Random().nextLong();
        String gameFilepath = "";
        ArrayList<Player> gamePlayers = new ArrayList<>();

        GameController gc = new GameController(gameId, gameFilepath, gamePlayers);
        CmdLineUI ui = new CmdLineUI();
        ui.runPlayerSetup(gc);
    }
}
