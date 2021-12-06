package entities;

import java.io.Serializable;
import java.lang.Math;

public class PropertyTile extends Tile implements Buyable, Serializable {
    private int price;
    private int rent;
    private Player owner;

    //  I think we initialize all properties at the beginning of the game from a hard coded list with rent and price
    public PropertyTile(String name, int price, int rent){
        super(name, false);
        this.price = price;
        this.rent = rent;
        this.owner = null;
    }

    public String getName() {
        return this.name;
    }

    public int getRent() {
        return this.rent;
    }

    // we would call this when someone builds(upgrades) on a property
    public void setRent(int new_rent) {
        this.rent = new_rent;
    }

    public int getPrice() {
        return this.price;
    }

    // this is used when someone upgrades so that the sell price is indicative of total price
    public void setPrice(int new_price) {
        this.price = new_price;
    }

    public int getSalePrice() {
        return (int) Math.round(price * 0.8);
    }


    public void setOwner(Player owner){
        if(owner != null){
            this.owner = owner;
            this.owned = true;
        }else{
            this.owner = null;
            this.owned = false;
        }

    }

    public Player getOwner() {
        return this.owner;
    }

    public void interact(Token token, TileOutputBoundary outBound) {

    }
}
