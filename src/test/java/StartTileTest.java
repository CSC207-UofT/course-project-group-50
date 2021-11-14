import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StartTileTest {
    StartTile testStartTile;
    Player player1;

    @Before
    public void setUp() {
        testStartTile = new StartTile();
        player1 = new Player("Player 1");
    }

    @Test(timeout = 50)
    public void testInteract() {
        testStartTile.interact(player1);
        int expected = 1200;
        assertEquals(expected, player1.getCash());
    }

}
