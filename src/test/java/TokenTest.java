import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TokenTest {
    Token testToken;

    @Before
    public void setUp() {
        testToken = new Token("Red", new Player("Player 1"));
    }

    @Test(timeout = 50)
    public void testGetColour() {
        String expected = "Red";
        assertEquals(expected, testToken.getColour());
    }

    @Test(timeout = 50)
    public void testGetLocation() {
        int expected = 0;
        assertEquals(expected, testToken.getLocation());
    }

    @Test(timeout = 50)
    public void testIsInJail() {
        assertFalse(testToken.isInJail());
    }

    @Test(timeout = 50)
    public void testGetJailDays() {
        int expected = 0;
        assertEquals(expected, testToken.getJailDays());
    }

    @Test(timeout = 50)
    public void testSetJailDays() {
        testToken.setJailDays(1);
        int expected = 1;
        assertEquals(expected, testToken.getJailDays());
    }

    @Test(timeout = 50)
    public void testMoveNoMod() {
        testToken.move(3);
        int expected = 3;
        assertEquals(expected, testToken.getLocation());
    }

    @Test(timeout = 50)
    public void testMovePosMod() {
        testToken.move(55);
        int expected = 7;
        assertEquals(expected, testToken.getLocation());
    }

    @Test(timeout = 50)
    public void testMoveNegMod() {
        testToken.move(-3);
        int expected = 21;
        assertEquals(expected, testToken.getLocation());
    }
}
