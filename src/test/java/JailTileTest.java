import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class JailTileTest {
    JailTile testJailTile;
    Player player1;
    Player player2;

    @Before
    public void setUp() {
        testJailTile = new JailTile();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        player2.getToken().setInJail(true);
        player2.getToken().setJailDays(0);
    }

    @Test
    public void testInteractNotInJail() {
        testJailTile.interact(player1.getToken());
        int expected = 3;
        assertEquals(expected, player1.getToken().getJailDays());
    }

    @Test
    public void testInteractToBeReleased() {
        testJailTile.interact(player2.getToken());
        int expected = 0;
        assertEquals(expected, player2.getToken().getJailDays());
        assertFalse(player2.getToken().isInJail());
    }

}
