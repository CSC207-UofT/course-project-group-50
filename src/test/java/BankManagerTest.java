import entities.City;
import entities.Player;
import org.junit.Before;
import org.junit.Test;
import usecases.BankManager;
import usecases.ConstantsInputBoundary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BankManagerTest {
    BankManager testBankManager;
    Player player1;
    Player player2;
    City property1;

    @Before
    public void setUp() {
        testBankManager = new BankManager();
        player1 = new Player("entities.Player 1", new ConstantsInputBoundary());
        player2 = new Player("entities.Player 2", new ConstantsInputBoundary());
        property1 = new City("Property 1", 50, 10, 1);
    }

    @Test(timeout = 50)
    public void testPayRent() {
        testBankManager.payRent(player1, player2, property1);
        int expectedPlayer1 = 1010;
        int expectedPlayer2 = 990;
        assertEquals(expectedPlayer1, player1.getCash());
        assertEquals(expectedPlayer1, player1.getNetWorth());
        assertEquals(expectedPlayer2, player2.getCash());
        assertEquals(expectedPlayer2, player2.getNetWorth());
    }

    @Test(timeout = 50)
    public void testAnyNetworthGreaterTrue() {
        int targetNetWorth = 5000;
        player1.setNetWorth(targetNetWorth);
        List<Player> playerList = new ArrayList<>(Arrays.asList(player1, player2));
        assertTrue(testBankManager.anyNetworthGreater(playerList, targetNetWorth));
    }

    @Test(timeout = 50)
    public void testAnyNetworthGreaterFalse() {
        int targetNetWorth = 5000;
        player1.setNetWorth(targetNetWorth - 1);
        List<Player> playerList = new ArrayList<>(Arrays.asList(player1, player2));
        assertFalse(testBankManager.anyNetworthGreater(playerList, targetNetWorth));
    }

    @Test(timeout = 50)
    public void testCheckBankruptcyTrue() {
        player1.setNetWorth(0);
        assertTrue(testBankManager.checkBankruptcy(player1));
    }

    @Test(timeout = 50)
    public void testCheckBankruptcyFalse() {
        player1.setNetWorth(1000);
        assertFalse(testBankManager.checkBankruptcy(player1));
    }

    @Test(timeout = 50)
    public void testNetworthGreaterTrue() {
        int targetNetWorth = 5000;
        player1.setNetWorth(targetNetWorth);
        assertTrue(testBankManager.netWorthGreater(player1, targetNetWorth));
    }

    @Test(timeout = 50)
    public void testNetworthGreaterFalse() {
        int targetNetWorth = 5000;
        player1.setNetWorth(targetNetWorth - 1);
        assertFalse(testBankManager.netWorthGreater(player1, targetNetWorth));
    }

    @Test(timeout = 50)
    public void testDeductCostOfProperty() {
        testBankManager.deductCostOfProperty(player1, property1);
        assertEquals(950, player1.getCash());
        assertEquals(1000, player1.getNetWorth());
    }

    @Test(timeout = 50)
    public void testAddSellbackOfProperty() {
        testBankManager.addSellbackOfProperty(player1, property1);
        int expectedCash = 1000 + property1.getSalePrice();
        int expectedNetWorth = 1000 - (property1.getPrice() - property1.getSalePrice());
        assertEquals(expectedCash, player1.getCash());
        assertEquals(expectedNetWorth, player1.getNetWorth());
    }

    @Test(timeout = 50)
    public void testUpdateCashPropertySwap() {
        int expectedCash = player1.getCash() + 100;
        int expectedNetWorth = player1.getCash() + 100;
        testBankManager.updateCashPropertySwap(player1, player2);
        assertEquals(expectedCash, player1.getCash());
        assertEquals(expectedNetWorth, player1.getNetWorth());
        assertEquals(expectedCash, player2.getCash());
        assertEquals(expectedNetWorth, player2.getNetWorth());
    }
}
