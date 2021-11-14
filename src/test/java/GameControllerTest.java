import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameControllerTest {
    GameController testGameController;
    GameController testGameController2;
    ArrayList<String> players;
    long gameId;

    @Before
    public void setUp() {
        gameId = new Random().nextLong();
        players = new ArrayList<>();
        players.add("Player 1");
        players.add("Player 2");
        players.add("Player3");
        testGameController = new GameController(gameId, "path", players);
        testGameController.runPlayerSetUp(players);
        testGameController2 = new GameController(gameId, "path", players, 900);
        testGameController2.runPlayerSetUp(players);
    }

    @Test(timeout = 50)
    public void testGenerateOrder() {
        List<Integer> order = testGameController.generateOrder();
        assertTrue(order.contains(0) && order.contains(1) && order.contains(2));
    }

    @Test(timeout = 50)
    public void testIsWinnerTrue() {
        assertTrue(testGameController2.isWinner());
    }

    @Test(timeout = 50)
    public void testIsWinnerFalse() {
        assertFalse(testGameController.isWinner());
    }
}
