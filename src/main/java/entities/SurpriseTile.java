package entities;

import java.io.Serializable;

public class SurpriseTile extends Tile implements Serializable {
    public SurpriseTile() {
        super("Surprise", false);
    }

    /**
     * Interact with this tile
     *
     * @param token    the token that landed on this tile
     * @param outBound object which allows the tile to interact with the player while following
     *                 clean architecture
     */
    @Override
    public void interact(Token token, TileOutputBoundary outBound) {
        CardDeck cardDeck = new CardDeck();
        Card card = cardDeck.draw();
        if(card.getAction() == 0) {
            outBound.cardZero(token.getPlayer());
        } else if(card.getAction() == 1) {
            outBound.cardOne(token.getPlayer());
        } else if(card.getAction() == 2) {
            outBound.cardTwo(token.getPlayer());
        } else if (card.getAction() == 3) {
            outBound.cardThree(token.getPlayer());
        }
        outBound.notifyUser(card.getMessage());
    }

}
