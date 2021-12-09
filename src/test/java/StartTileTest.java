import entities.Player;
import entities.StartTile;
import interfaceadapters.GameSetUp;
import org.junit.Before;
import org.junit.Test;
import usecases.ConstantsInputBoundary;
import usecases.TileManagerFacade;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class StartTileTest {
    StartTile testStartTile;
    Player player1;
    TileManagerFacade testTileManagerFacade;

    @Before
    public void setUp() throws IOException {
        testStartTile = new StartTile();
        player1 = new Player("entities.Player 1", new ConstantsInputBoundary());
        testTileManagerFacade = new GameSetupTest().getTileManagerFacade();
    }

    @Test(timeout = 50)
    public void testInteract() {
        testStartTile.interact(player1.getToken(), testTileManagerFacade);
        int expected = 1200;
        assertEquals(expected, player1.getCash());
    }

}
