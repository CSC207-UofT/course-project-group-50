import interfaceadapters.GameController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import usecases.BankManager;
import usecases.BoardManager;
import usecases.PropertyManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameControllerTest {
    GameController testGameController;
    BoardManager testBoardManager;
    PropertyManager testPropertyManager;
    BankManager testBankManager;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @Before
    public void setUp() throws IOException {
        System.setOut(new PrintStream(outContent));
        GameSetupTest setup = new GameSetupTest();
        testGameController = setup.getGameController();
        testBoardManager = setup.getBoardManager();
        testPropertyManager = new PropertyManager();
        testBankManager = new BankManager();
    }

    @Test(timeout = 50)
    public void testRunPlayerSetUp() {
        // this is done in gamesetup as we use this method in all testing
    }

    @Test(timeout = 50)
    public void testNotifyUser() {
        testGameController.notifyUser("hey");
        assertEquals("hey" + "\n", outContent.toString());
    }

    @Test(timeout = 50)
    public void testGetUsernames() {
        List<String> usernames = testGameController.getUsernames();
        List<String> expected = new ArrayList<>(Arrays.asList("player1", "player2"));
        assertEquals(expected, usernames);
    }

    @Test(timeout = 50)
    public void testGetBoardManager() {
        assertEquals(testGameController.getBoardManager(), testBoardManager);
    }

    @Test(timeout = 50)
    public void testGetOrder() {
        assertEquals(testGameController.getOrder(), new ArrayList<>(Arrays.asList(1, 2)));
    }

    @After
    public void restoreStream() {
        System.setOut(originalOut);
    }
}
