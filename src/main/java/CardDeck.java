import java.io.Serializable;
import java.util.*;

public class CardDeck implements Serializable {

    public static final int MOVE_FORWARD = 0;
    public static final int MOVE_BACKWARD = 1;
    public static final int GET_PAID = 2;
    public static final int PAY = 3;

    ArrayList<Card> cards = new ArrayList<>();

    public CardDeck() {
        cards.add(new Card(MOVE_FORWARD, "Move 1 forward."));
        cards.add(new Card(MOVE_BACKWARD, "Move 1 backward."));
        cards.add(new Card(GET_PAID, "Tax Refund. You receive $100 from the bank."));
        cards.add(new Card(PAY, "Tax Fraud. You'll pay $100 to the bank."));

        shuffle();
    }

    public void add(Card card) {
        cards.add(card);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        return cards.get(0);
    }
}