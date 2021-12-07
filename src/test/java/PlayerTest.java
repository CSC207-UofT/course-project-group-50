import entities.Player;
import org.junit.Before;
import org.junit.Test;
import usecases.ConstantsInputBoundary;

import static org.junit.Assert.*;

public class PlayerTest {
    Player test_player;

    @Before
    public void setUp(){
        test_player = new Player("test_player", new ConstantsInputBoundary());
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
        assertEquals("entities.Player test_player – Cash: $1000, Net Worth: $1000", test_player.toString());
    }

    @Test(timeout = 50)
    public void TestSetBankrupt() {
        test_player.setBankrupt();
        assertTrue(test_player.isBankrupt());
        assertEquals(test_player.getNetWorth(), 0);
        assertEquals(test_player.getCash(), 0);
        assertNull(test_player.getToken());
    }
}
