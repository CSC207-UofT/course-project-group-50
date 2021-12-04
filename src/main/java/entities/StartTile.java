package entities;

import java.io.Serializable;

public class StartTile extends SpecialTile implements Serializable {

    @Override
    public void interact(Token token, TileOutputBoundary outBound) {
        outBound.passStart(token.getPlayer());
    }

    // public void interact(Token token){
    //     token.getPlayer().addCash(200);
    // }
}
