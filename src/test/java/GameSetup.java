import details.CmdLineUI;
import entities.Player;
import interfaceadapters.GameController;
import interfaceadapters.Presenter;
import usecases.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameSetup {
    private GameController gc;
    private BoardManager boardManager;

    public void GameSetUp(){
        this.gc = testGameController();
        this.boardManager = testBoardManager();
    }

    public GameController testGameController() {
        CmdLineUI ui = new CmdLineUI();
        ArrayList<Integer> order = new ArrayList<>(Arrays.asList(1, 2));
        Presenter presenter = new Presenter(order.size());
        Player player1 = new Player("player1", new ConstantsInputBoundary());
        Player player2 = new Player("player2", new ConstantsInputBoundary());
        List<String> usernames = new ArrayList<>(Arrays.asList(player1.getUsername(), player2.getUsername()));
        return new GameController(ui, order, presenter, usernames);
    }

    public BoardManager testBoardManager(){
        return new BoardManager(new BankManager(), new PropertyManager(), gc);
    }

    public TileManagerFacade testTileManagerFacade(){
        return new TileManagerFacade(gc, boardManager, new BankManager(), new PropertyManager());
    }

}
