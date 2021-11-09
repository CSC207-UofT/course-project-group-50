import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertyTileTest {
    PropertyTile test_property;
    Player test_player;

    @Before
    public void setUp(){
        test_property = new PropertyTile("samraj's house", 20, 200);
        test_player = new Player("samrooj");
    }

    @Test(timeout = 50)
    public void TestGetRent() {
        assertEquals(20, test_property.getRent());
    }

    @Test(timeout = 50)
    public void TestSetRent() {
        test_property.setRent(30);
        assertEquals(30, test_property.getRent());
    }

    @Test(timeout = 50)
    public void TestGetPrice() {
        assertEquals(200, test_property.getPrice());
    }

    @Test(timeout = 50)
    public void TestSetPrice() {
        test_property.setPrice(300);
        assertEquals(300, test_property.getPrice());
    }

    @Test(timeout = 50)
    public void TestIsOwnedFalse() {
        assertFalse(test_property.isOwned());
    }

    @Test(timeout = 50)
    public void TestIsOwnedTrue() {
        test_property.setOwner(test_player);
        assertTrue(test_property.isOwned());
    }

    @Test(timeout = 50)
    public void TestSetOwner_and_GetOwner() {
        test_property.setOwner(test_player);
        assertEquals(test_player, test_property.getOwner());
    }

    @Test(timeout = 50)
    public void Testpurchase() {
        test_property.purchase(test_player);
        assertTrue(test_property.isOwned());
        assertEquals(test_player, test_property.getOwner());
        assertEquals(800, test_player.getCash());
    }

    @Test(timeout = 50)
    public void Testsell() {
        test_property.purchase(test_player);
        test_property.sell();
        assertFalse(test_property.isOwned());
        assertNull(test_property.getOwner());
        assertEquals(960, test_player.getCash());
    }
}
