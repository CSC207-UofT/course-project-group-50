package entities;

import java.io.Serializable;

public class AuctionTile extends SpecialTile implements Serializable {

    @Override
    public void interact(Token token, TileOutputBoundary outBound) {
        outBound.auction(token.getPlayer());
    }

    // TODO: This method is not being used currently. We need to decide if we want this class or not.
//    public void interact(Player player1, Player player2, PropertyManager propertyManager, BankManager bankManager) {
//        propertyManager.tradeProperties(player1, player2, bankManager);
//    }
}
