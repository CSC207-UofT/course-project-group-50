import entities.Card;
import entities.CardDeck;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CardDeckTest {
    CardDeck testCardDeck;

    @Before
    public void setUp() {
        testCardDeck = new CardDeck();
    }

    @Test(timeout = 50)
    public void testDraw() {
        Card drawnCard = testCardDeck.draw();
        List<String> messages = new ArrayList<>();
        messages.add("Move 1 forward.");
        messages.add("Move 1 backward.");
        messages.add("Tax Refund. You receive $100 from the bank.");
        messages.add("Tax Fraud. You'll pay $100 to the bank.");
        assertTrue(messages.contains(drawnCard.getMessage()));
    }

}
