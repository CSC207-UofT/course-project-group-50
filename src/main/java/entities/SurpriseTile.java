package entities;

import java.io.Serializable;

public class SurpriseTile extends Tile implements Serializable {
    public SurpriseTile() {super("Surprise", false);}

    @Override
    public void interact(Token token, TileOutputBoundary outBound) {
        CardDeck cardDeck = new CardDeck();
        Card card = cardDeck.draw();
        if(card.getAction() == 0) {
            token.move(1);
        }
        else if(card.getAction() == 1) {
            token.move(-1);
        }
        else if(card.getAction() == 2) {
            outBound.cardTwo(token.getPlayer());
        } else if(card.getAction() == 3) {
            outBound.cardThree(token.getPlayer());
        }
        outBound.notifyUser(card.getMessage());
    }

}
