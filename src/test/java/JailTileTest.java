import entities.JailTile;
import entities.Player;
import org.junit.Before;
import org.junit.Test;
import usecases.BoardManager;
import usecases.TileManagerFacade;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class JailTileTest {
    JailTile testJailTile;
    Player player1;
    Player player2;
    BoardManager testBoardManager;
    TileManagerFacade testTileManagerFacade;

    @Before
    public void setUp() throws IOException {
        GameSetupTest setup = new GameSetupTest();
        testTileManagerFacade = setup.getTileManagerFacade();
        testBoardManager = setup.getBoardManager();
        testJailTile = new JailTile();
        player2 = testBoardManager.getPlayerFromUsername("player2");
        player1 = testBoardManager.getPlayerFromUsername("player1");
    }

    @Test
    public void testInteractNotInJail() {
        testJailTile.interact(player1.getToken(), testTileManagerFacade);
        int expected = 3;
        assertEquals(expected, player1.getToken().getJailDays());
    }

    @Test
    public void testInteractToBeReleased() {
        int expected = 0;
        assertEquals(expected, player2.getToken().getJailDays());
        assertFalse(player2.getToken().isInJail());
    }

}
