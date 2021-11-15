import entities.Player;
import entities.PropertyTile;
import org.junit.Before;
import org.junit.Test;
import usecases.BankManager;

import static org.junit.Assert.assertEquals;

public class BankManagerTest {
    BankManager testBankManager;
    Player player1;
    Player player2;
    PropertyTile property1;

    @Before
    public void setUp() {
        testBankManager = new BankManager();
        player1 = new Player("entities.Player 1");
        player2 = new Player("entities.Player 2");
        property1 = new PropertyTile("Property 1", 50, 10);
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
    public void testDeductCostOfProperty() {
        testBankManager.deductCostOfProperty(player1, property1);
        int expected = 950;
        assertEquals(expected, player1.getCash());
        assertEquals(expected, player1.getNetWorth());
    }

    @Test(timeout = 50)
    public void testAddSellbackOfProperty() {
        testBankManager.addSellbackOfProperty(player1, property1);
        int expectedCash = 1000 + property1.getSalePrice();
        int expectedNetWorth = 1000 - (property1.getPrice() - property1.getSalePrice());
        assertEquals(expectedCash, player1.getCash());
        assertEquals(expectedNetWorth, player1.getNetWorth());
    }
}
