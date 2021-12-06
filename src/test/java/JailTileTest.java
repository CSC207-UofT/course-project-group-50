import details.CmdLineUI;
import entities.JailTile;
import entities.Player;
import interfaceadapters.GameController;
import interfaceadapters.GameSetUp;
import interfaceadapters.Presenter;
import org.junit.Before;
import org.junit.Test;
import usecases.BoardManager;
import usecases.ConstantsInputBoundary;
import usecases.TileManagerFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class JailTileTest {
    JailTile testJailTile;
    Player player1;
    Player player2;
    GameController testGameController;
    BoardManager testBoardManager;
    TileManagerFacade testTileManagerFacade;

    @Before
    public void setUp() {
        testJailTile = new JailTile();
        player2.getToken().setInJail(true);
        player2.getToken().setJailDays(0);
    }

    @Test
    public void testInteractNotInJail() {
        testJailTile.interact(player1.getToken(), testTileManagerFacade);
        int expected = 3;
        assertEquals(expected, player1.getToken().getJailDays());
    }

    @Test
    public void testInteractToBeReleased() {
        testJailTile.interact(player2.getToken(), testTileManagerFacade);
        int expected = 0;
        assertEquals(expected, player2.getToken().getJailDays());
        assertFalse(player2.getToken().isInJail());
    }

}
