import entities.City;
import entities.Player;
import interfaceadapters.GameController;
import org.junit.Before;
import org.junit.Test;
import usecases.*;

import static org.junit.Assert.*;

public class TileManagerBuyPropertyTest {
    GameController testGameController;
    BoardManager testBoardManager;
    PropertyManager testPropertyManager;
    BankManager testBankManager;
    TileManagerBuyProperty testTileManagerBuyProperty;

    @Before
    public void setUp() {
        GameSetup setup =  new GameSetup();
        testGameController = setup.getGameController();
        testBoardManager = setup.getBoardManager();
        testPropertyManager = new PropertyManager();
        testBankManager = new BankManager();
        testTileManagerBuyProperty = new TileManagerBuyProperty(testPropertyManager, testBankManager);
    }

    @Test(timeout = 50)
    public void testBuyPropertyPlayerHasEnough() {
        Player player = testBoardManager.getPlayerFromUsername(testBoardManager.getPlayerUsernameFromNumber(1));
        City city = new City("testCity", 100, 10, "red");
        testTileManagerBuyProperty.buyProperty(player, city);
        assertTrue(testPropertyManager.getPropertiesOwned().containsKey(city) &&
                testPropertyManager.getPropertiesOwned().get(city) == player);
        assertSame(city.getOwner(), player);
        assertEquals(900, player.getCash());
        assertEquals(1000, player.getNetWorth());
    }

    @Test(timeout = 50)
    public void testBuyPropertyPlayerNotEnough() {
        Player player = testBoardManager.getPlayerFromUsername(testBoardManager.getPlayerUsernameFromNumber(1));
        player.setCash(50);
        City city = new City("testCity", 100, 10, "red");
        testTileManagerBuyProperty.buyProperty(player, city);
        assertFalse(testPropertyManager.getPropertiesOwned().containsKey(city) &&
                testPropertyManager.getPropertiesOwned().get(city) == player);
        assertNotSame(city.getOwner(), player);
        assertNotEquals(player.getCash(), 900);
        assertEquals(player.getNetWorth(), 1000);
    }
}
