package entities;

import java.io.Serializable;

public class AuctionTile extends Tile implements Serializable {
    public AuctionTile() {
        super("Auction", false);
    }

    @Override
    public void interact(Token token, TileOutputBoundary outBound) {
        outBound.auction(token.getPlayer());
    }
}
