public class Card {

    private final int action;
    private final String message;

    public Card(int action, String message) {
        this.action = action;
        this.message = message;
    }

    public int getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }

    public String toString () {
        return message;
    }
}