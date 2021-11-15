import entities.Player;
import entities.SurpriseTile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class SurpriseTileTest {
    SurpriseTile testSurpriseTile;
    Player player1;

    @Before
    public void setUp() {
        testSurpriseTile = new SurpriseTile();
        player1 = new Player("entities.Player 1");
    }

    @Test(timeout = 50)
    public void testInteract() {
        testSurpriseTile.interact(player1);
        boolean condOne = player1.getToken().getLocation() == 1;
        boolean condTwo = player1.getToken().getLocation() == 23;
        boolean condThree = player1.getCash() == 1100;
        boolean condFour = player1.getCash() == 900;
        assertTrue(condOne || condTwo || condThree || condFour);
    }
}
