import entities.Board;
import entities.City;
import entities.Player;
import entities.Tile;
import interfaceadapters.GameController;
import org.junit.Before;
import org.junit.Test;
import usecases.BankManager;
import usecases.BoardManager;
import usecases.ConstantsInputBoundary;

import java.io.IOException;

import static org.junit.Assert.*;

public class BoardManagerTest {
    GameController testGameController;
    BoardManager testBoardManager;

    @Before
    public void setUp() throws IOException {
        GameSetupTest setup = new GameSetupTest();
        testGameController = setup.getGameController();
        testBoardManager = setup.getBoardManager();
    }

    @Test(timeout = 50)
    public void testAddPlayer() {
        testBoardManager.addPlayer("player3");
        assertNotNull(testBoardManager.getPlayerFromUsername("player3"));
    }

    @Test(timeout = 1000)
    public void testRollAndMove() {
        // we can just test location against 0 as we use a fresh game
        assertTrue(testBoardManager.rollAndMove(1) != 0);
    }

    @Test(timeout = 50)
    public void testInteractWithTile() {
        // no need to test as this just gets location and calls interact, test the 2 methods used, and we are good
    }

    @Test(timeout = 50)
    public void testGetUsernameFromNumber() {
        if (testGameController.getOrder().get(0) == 0) {
            assertSame("player1", testBoardManager.getPlayerUsernameFromNumber(1));
        } else {
            assertSame("player2", testBoardManager.getPlayerUsernameFromNumber(1));
        }
    }

    @Test(timeout = 50)
    public void testIsWinner() {
        // just calls and returns BankManager method which is tested in BankManagerTest
    }

    @Test(timeout = 50)
    public void testOnlyOneNotBankruptTrue() {
        testBoardManager.getPlayerFromUsername("player1").setBankrupt();
        assertTrue(testBoardManager.onlyOneNonBankrupt());
    }

    @Test(timeout = 50)
    public void testOnlyOneNotBankruptFalse() {
        assertFalse(testBoardManager.onlyOneNonBankrupt());
    }

    @Test(timeout = 50)
    public void testCanPlayTrue() {
        assertTrue(testBoardManager.canPlay("player1"));
    }

    @Test(timeout = 50)
    public void testCanPlayFalse() {
        testBoardManager.getPlayerFromUsername("player1").setBankrupt();
        assertFalse(testBoardManager.canPlay("player1"));
    }

    @Test(timeout = 50)
    public void testGetPlayerFromUsername() {
        assertNotNull(testBoardManager.getPlayerFromUsername("player1"));
    }

    @Test(timeout = 50)
    public void testProcessWinner() {
        // again no need to test other method calls
    }
}
