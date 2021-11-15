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
        testCity = new City("Test entities.City", 0, 0, "Red");
        testCitySameColour = new City("Test entities.City 2", 0, 0, "Red");
        testCityDifferentColour = new City("Test entities.City 3", 0, 0, "Blue");
        building1 = new Building(0, 0);
    }

    @Test(timeout = 50)
    public void testGetColour() {
        String expected = "Red";
        assertEquals(expected, testCity.getColour());
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
