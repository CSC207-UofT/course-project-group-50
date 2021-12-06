package entities;

import java.io.Serializable;

public class AuctionTile extends SpecialTile implements Serializable {

    @Override
    public void interact(Token token, TileOutputBoundary outBound) {
        outBound.auction(token.getPlayer());
    }
}
