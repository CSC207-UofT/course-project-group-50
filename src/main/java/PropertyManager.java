import java.util.Hashtable;
import java.util.List;

public class PropertyManager {
    private List<PropertyTile> properties;
    private Hashtable<PropertyTile, Player> propertiesOwned;

    public PropertyManager(){
        this.properties = Constants.properties;
        this.propertiesOwned = new Hashtable<>();
    }

    public void buyProperty(Player buyer, PropertyTile property){

    }
}
