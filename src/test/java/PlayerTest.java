import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Player test_player;

    @Before
    public void setUp(){
        test_player = new Player("test_player");
    }

    @Test(timeout = 50)
    public void TestGetCash() {
        assertEquals(1000, test_player.getCash());
    }

    @Test(timeout = 50)
    public void TestSetCash() {
        test_player.setCash(1500);
        assertEquals(1500, test_player.getCash());
    }

    @Test(timeout = 50)
    public void TestAddCash() {
        test_player.addCash(50);
        assertEquals(1050, test_player.getCash());
    }

    @Test(timeout = 50)
    public void TestGetNetWorth() {
        assertEquals(1000, test_player.getNetWorth());
    }

    @Test(timeout = 50)
    public void TestSetNetWorth() {
        test_player.setNetWorth(1050);
        assertEquals(1050, test_player.getNetWorth());
    }

    @Test(timeout = 50)
    public void TestToString() {
        assertEquals("Player test_player – Cash: $1000, Net Worth: $1000", test_player.toString());
    }
}
