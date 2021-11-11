import java.util.Hashtable;
import java.util.List;

public class PropertyManager {
    private Hashtable<PropertyTile, Player> propertiesOwned;

    public PropertyManager(){
        // not sure if properties list is needed
        List<PropertyTile> properties = Constants.properties;
        this.propertiesOwned = new Hashtable<>();
    }

    public void buyProperty(Player buyer, PropertyTile property){
        propertiesOwned.put(property, buyer);
        property.setOwner(buyer);
    }

    public void sellProperty(Player seller, PropertyTile property){
        propertiesOwned.remove(property);
        property.setOwner(null);
    }

    // since this is used when player is bankrupt we do not update money
    public void resetProperties(Player owner){
        for(PropertyTile property: propertiesOwned.keySet()){
            if(owner == property.getOwner()){
                sellProperty(owner, property);
            }
        }
    }
}
