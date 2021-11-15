import entities.Player;
import entities.StartTile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StartTileTest {
    StartTile testStartTile;
    Player player1;

    @Before
    public void setUp() {
        testStartTile = new StartTile();
        player1 = new Player("entities.Player 1");
    }

    @Test(timeout = 50)
    public void testInteract() {
        testStartTile.interact(player1.getToken());
        int expected = 1200;
        assertEquals(expected, player1.getCash());
    }

}
