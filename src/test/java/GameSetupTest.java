import details.CmdLineUI;
import entities.Player;
import interfaceadapters.GameController;
import interfaceadapters.Presenter;
import usecases.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameSetupTest {
    private final GameController gc;
    private final BoardManager boardManager;

    public GameSetupTest() throws IOException {
        this.gc = testGameController();
        this.boardManager = gc.getBoardManager();
    }

    private GameController testGameController() throws IOException {
        CmdLineUI ui = new CmdLineUI();
        ArrayList<Integer> order = new ArrayList<>(Arrays.asList(1, 2));
        Player player1 = new Player("player1", new ConstantsInputBoundary());
        Player player2 = new Player("player2", new ConstantsInputBoundary());
        List<String> usernames = new ArrayList<>(Arrays.asList(player1.getUsername(), player2.getUsername()));
        GameController testGameController = new GameController(ui, order, usernames);
        testGameController.runPlayerSetUp();
        return testGameController;
    }

    public GameController getGameController(){
        return this.gc;
    }

    public BoardManager getBoardManager(){
        return gc.getBoardManager();
    }

    public TileManagerFacade getTileManagerFacade(){
        return new TileManagerFacade(gc, boardManager, new BankManager(), new PropertyManager());
    }

}
