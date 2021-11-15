package entities;

import usecases.BankManager; // TODO: remove use cases from entity
import usecases.PropertyManager;

import java.io.Serializable;

public class AuctionTile extends SpecialTile implements Serializable {

    // TODO: This method is not being used currently. We need to decide if we want this class or not.
    public void interact(Player player1, Player player2, PropertyManager propertyManager, BankManager bankManager) {
        propertyManager.tradeProperties(player1, player2, bankManager);
    }
}
