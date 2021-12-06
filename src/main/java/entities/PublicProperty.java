package entities;

import java.io.Serializable;

public class PublicProperty extends PropertyTile implements Serializable {

    public PublicProperty(String name, int price, int rent) {
        super(name, price, rent);
    }

    @Override
    public void interact(Token token, TileOutputBoundary outBound) {
        outBound.payRent(token.getPlayer(), this);
    }
}
