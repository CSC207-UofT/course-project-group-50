import entities.Player;
import entities.PropertyTile;
import org.junit.Before;
import org.junit.Test;
import usecases.ConstantsInputBoundary;
import usecases.PropertyManager;

import static org.junit.Assert.*;

public class PropertyManagerTest {
    Player player1;
    Player player2;
    PropertyManager propertyManager;
    PropertyTile property1;
    PropertyTile property2;

    @Before
    public void setUp() {
        player1 = new Player("entities.Player 1", new ConstantsInputBoundary());
        player2 = new Player("entities.Player 2", new ConstantsInputBoundary());
        propertyManager = new PropertyManager();
        property1 = new PropertyTile("Property 1", 0, 0);
        property2 = new PropertyTile("Property 2", 0, 0);
    }

    @Test(timeout = 50)
    public void testBuyPropertyHashtable() {
        propertyManager.buyProperty(player1, property1);
        assertTrue(propertyManager.getPropertiesOwned().containsKey(property1) &&
                propertyManager.getPropertiesOwned().get(property1) == player1);
    }

    @Test(timeout = 50)
    public void testBuyPropertyTileUpdate() {
        propertyManager.buyProperty(player1, property1);
        assertSame(property1.getOwner(), player1);
    }

    @Test(timeout = 50)
    public void testSellPropertyHashtable() {
        propertyManager.buyProperty(player1, property1);
        propertyManager.sellProperty(property1);
        assertFalse(propertyManager.getPropertiesOwned().containsKey(property1));
    }

    @Test(timeout = 50)
    public void testSellPropertyTileUpdate() {
        propertyManager.buyProperty(player1, property1);
        propertyManager.sellProperty(property1);
        assertNull(property1.getOwner());
    }

    @Test(timeout = 50)
    public void testSwapProperties() {
        propertyManager.buyProperty(player1, property1);
        propertyManager.buyProperty(player2, property2);
        propertyManager.swap_properties(property1, property2);
        assertTrue(propertyManager.getPropertiesOwned().get(property1) == player2 &&
                propertyManager.getPropertiesOwned().get(property2) == player1);
    }

    @Test(timeout = 50)
    public void testResetProperties() {
        propertyManager.buyProperty(player1, property1);
        propertyManager.buyProperty(player1, property2);
        propertyManager.resetProperties(player1);
        assertFalse(propertyManager.getPropertiesOwned().containsKey(property1) &&
                propertyManager.getPropertiesOwned().containsKey(property2));
    }



}
