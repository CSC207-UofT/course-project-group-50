import entities.Building;
import entities.City;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CityTest {
    City testCity;
    City testCitySameColour;
    City testCityDifferentColour;
    Building building1;

    @Before
    public void setUp() {
        testCity = new City("Test entities.City", 0, 0, 1);
        testCitySameColour = new City("Test entities.City 2", 0, 0, 1);
        testCityDifferentColour = new City("Test entities.City 3", 0, 0, 2);
        building1 = new Building(0, 0);
    }

    @Test(timeout = 50)
    public void testGetColour() {
        int expected = 1;
        assertEquals(expected, testCity.getBlock());
    }

    @Test(timeout = 50)
    public void testSameColourBlockSameColour() {
        assertTrue(testCity.sameColourBlock(testCitySameColour));
    }

    @Test(timeout = 50)
    public void testSameColourBlockDiffColour() {
        assertFalse(testCity.sameColourBlock(testCityDifferentColour));
    }

    @Test(timeout = 50)
    public void testAddBuilding() {
        testCity.addBuilding(building1);
        assertTrue(testCity.getBuildings().contains(building1));
    }

    @Test(timeout = 50)
    public void testRemoveBuilding() {
        testCity.addBuilding(building1);
        assertTrue(testCity.getBuildings().contains(building1));
        testCity.removeBuilding(building1);
        assertFalse(testCity.getBuildings().contains(building1));
    }
}
