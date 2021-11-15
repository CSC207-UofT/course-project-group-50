package entities;

import java.io.Serializable;

public class SurpriseTile extends SpecialTile implements Serializable {
    public void interact(Player player) {
        CardDeck cardDeck = new CardDeck();
        Card card = cardDeck.draw();
        if (card.getAction() == 0) {
            player.getToken().move(1);
        }
        else if (card.getAction() == 1) {
            player.getToken().move(-1);
        }
        // TODO: Move this into usecases.BankManager and remove interact method
        else if (card.getAction() == 2) {
            player.addCash(100);
            player.addNetWorth(100);
        }
        else if (card.getAction() == 3) {
            player.subtractCash(100);
            player.subtractNetWorth(100);
        }
    }
    }
