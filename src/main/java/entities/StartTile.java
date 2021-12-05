package entities;

import java.io.Serializable;

public class StartTile extends SpecialTile implements Serializable {

    /**
     * Interact with the tile
     * @param token The token object that is interacting with the tile
     * @param outBound TileOutputBoundary object which allows the tile to interact with the player while following
     *                 clean architecture
     */
    @Override
    public void interact(Token token, TileOutputBoundary outBound) {
        outBound.passStart(token.getPlayer());
    }
}
