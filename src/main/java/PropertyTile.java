import java.io.Serializable;
import java.lang.Math;

public class PropertyTile extends Tile implements Buyable, Serializable {
    private final String name;
    private int price;
    private int rent;
    private final int sale_price; // 80% of price
    private boolean owned;
    private Player owner;

    //  I think we initialize all properties at the beginning of the game from a hard coded list with rent and price
    public PropertyTile(String name, int price, int rent){
        this.name = name;
        this.price = price;
        this.rent = rent;
        this.sale_price = (int) Math.round(price * 0.8);
        this.owned = false;
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
        return this.sale_price;
    }

    public boolean isOwned(){
        return this.owned;
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

}
