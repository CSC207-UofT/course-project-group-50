package usecases;

import entities.Player;
import entities.PropertyTile;
import details.CmdLineUI; // TODO: remove ui from entity

import java.io.Serializable;
import java.util.*;

public class PropertyManager implements Serializable {
    private HashMap<PropertyTile, Player> propertiesOwned;

    public PropertyManager(){
        this.propertiesOwned = new HashMap<>();
    }

    public void buyProperty(Player buyer, PropertyTile property){
        propertiesOwned.put(property, buyer);
        property.setOwner(buyer);
    }

    // we don't need the seller argument here guys
    public void sellProperty(PropertyTile property){
        propertiesOwned.remove(property);
        property.setOwner(null);
    }

    /** Returns a list of strings of properties that is owned by a player.
     * @param player A Monopoly player.
     * @return A list of properties that is owned by the player.
     */
    public List<String> propertiesOwnedByPlayer(Player player){
        ArrayList<String> owned = new ArrayList<>();
        for(PropertyTile property: this.propertiesOwned.keySet()){
            if(property.getOwner() == player){
                owned.add(property.getName());
            }
        }
        return owned;
    }

    /** Swaps the properties between two players involved in an auction.
     * @param property1 The first property that is being traded.
     * @param property2 The second property that is being traded.
     */
    public void swap_properties(PropertyTile property1, PropertyTile property2) {
        Player property1OriginalOwner = property1.getOwner();
        Player property2OriginalOwner = property2.getOwner();
        this.propertiesOwned.replace(property1, property1OriginalOwner, property2OriginalOwner);
        this.propertiesOwned.replace(property2, property2OriginalOwner, property1OriginalOwner);
    }

    /** Sell all the properties owned by the player.
     * @param owner The player who is bankrupt.
     */
    // since this is used when player is bankrupt we do not update money
    public void resetProperties(Player owner){
        List<PropertyTile> propertiesToSell = new ArrayList<>();
        for(PropertyTile property: propertiesOwned.keySet()){
            if(owner.equals(property.getOwner())){
                propertiesToSell.add(property);
            }
        }
        for(PropertyTile property : propertiesToSell) {
            sellProperty(property);
        }
    }

    public HashMap<PropertyTile, Player> getPropertiesOwned() {
        return this.propertiesOwned;
    }

    public PropertyTile stringToPropertyTile(String property_string){
        PropertyTile ReturnPropertyTile = null;
        for (PropertyTile key : this.propertiesOwned.keySet()){
            if (Objects.equals(key.getName(), property_string)) {
                ReturnPropertyTile = key;
                break;
            }
        }
        return ReturnPropertyTile;
    }

}
