package entities;

import java.io.Serializable;
import java.lang.Math;

public class PropertyTile extends Tile implements Buyable, Serializable {
    private int price;
    private int rent;
    private Player owner;

    /**
     * Initializes this PropertyTile.
     * @param name the name of this PropertyTile.
     * @param price the price of purchase for this PropertyTile.
     * @param rent the price of rent of this PropertyTile.
     */
    public PropertyTile(String name, int price, int rent){
        super(name, false);
        this.price = price;
        this.rent = rent;
        this.owner = null;
    }

    /**
     * Returns the name of this PropertyTile.
     * @return the name of this PropertyTile.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the amount of rent of this PropertyTile.
     * @return the amount of rent of this PropertyTile.
     */
    public int getRent() {
        return this.rent;
    }

    /**
     * Sets the rent of this PropertyTile to a new amount.
     * @param newRent new rent for this PropertyTile.
     */
    // we would call this when someone builds(upgrades) on a property
    public void setRent(int newRent) {
        this.rent = newRent;
    }

    /**
     * Returns the price of purchase for this PropertyTile.
     * @return the price of purchase for this PropertyTile.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Sets the price of this PropertyTile to a new amount.
     * @param newPrice new price for this PropertyTile.
     */
    // this is used when someone upgrades so that the sell price is indicative of total price
    public void setPrice(int newPrice) {
        this.price = newPrice;
    }

    /**
     * Returns the sale price for this PropertyTile.
     * @return the sale price for this PropertyTile.
     */
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

    /**
     * Returns the player who owns this PropertyTile.
     * @return the player who owns this PropertyTile.
     */
    public Player getOwner() {
        return this.owner;
    }

    public void interact(Token token, TileOutputBoundary outBound) {

    }
}
