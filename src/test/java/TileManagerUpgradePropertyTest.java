import entities.City;
import entities.Player;
import entities.PropertyTile;
import interfaceadapters.GameController;
import org.junit.Before;
import org.junit.Test;
import usecases.BoardManager;
import usecases.PropertyManager;
import usecases.TileManagerUpgradeProperty;

import java.io.IOException;

import static org.junit.Assert.*;

public class TileManagerUpgradePropertyTest {
    GameController testGameController;
    BoardManager testBoardManager;
    PropertyManager testPropertyManager;
    TileManagerUpgradeProperty testTileManagerUpgradeProperty;

    @Before
    public void setUp() throws IOException {
        GameSetupTest setup = new GameSetupTest();
        testGameController = setup.getGameController();
        testBoardManager = setup.getBoardManager();
        testPropertyManager = new PropertyManager();
        testTileManagerUpgradeProperty = new TileManagerUpgradeProperty(testGameController);
    }

    @Test(timeout = 50)
    public void testUpgradeProperty() {
        Player player = testBoardManager.getPlayerFromUsername(testBoardManager.getPlayerUsernameFromNumber(1));
        City city = new City("testCity", 100, 10, 1);
        testPropertyManager.buyProperty(player, city);
        testTileManagerUpgradeProperty.upgradeProperty(player, city);
        assertEquals(city.getRent(), 20);
        assertEquals(city.getPrice(), (int) Math.round(100 * 1.4));
        assertEquals(player.getCash(), 1000 - (int) Math.round(100 * 0.4));
        assertEquals(player.getNetWorth(), 1000);
        assertNotNull(city.getBuildings());

    }
}
