import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PropertyManager {
    private HashMap<PropertyTile, Player> propertiesOwned;

    public PropertyManager(){
        this.propertiesOwned = new HashMap<>();
    }

    public void buyProperty(Player buyer, PropertyTile property){
        propertiesOwned.put(property, buyer);
        property.setOwner(buyer);
    }

    // we don't need the seller argument here guys
    public void sellProperty(Player seller, PropertyTile property){
        propertiesOwned.remove(property);
        property.setOwner(null);
    }

    public void tradeProperties(Player player1, Player player2) {
        if (is_trading(player1) && is_trading(player2)) {
            String property1;
            System.out.println("Please enter the name of the property you are willing to trade.");
            property1 = CmdLineUI.scanner.nextLine();
            String property2;
            System.out.println("Please enter the name of the property you are willing to trade.");
            property2 = CmdLineUI.scanner.nextLine();
            swap_properties(player1, player2, property1, property2);
            // if the trade completes, both users receive $100 each
            player1.addCash(100);
            player2.addCash(100);
            player1.addNetWorth(100);
            player2.addNetWorth(100);
        } else {
            System.out.println("At least one of the players are not willing to trade, moving on to the next round.");
        }
    }

    public boolean is_trading(Player player) {
        String input;
        System.out.println(player.getUsername() + ", would you like to trade one of your properties?" +
                " Enter Y for yes and N for no.");
        input = CmdLineUI.scanner.nextLine();
        return Objects.equals(input, "Y");
    }

    // this method is only called if both users are willing to trade
    public void swap_properties(Player player1, Player player2, String property1, String property2) {
        for (PropertyTile key : this.propertiesOwned.keySet()) {
            if (Objects.equals(key.getName(), property1)) {
                this.propertiesOwned.replace(key, player1, player2);
                key.setOwner(player2);
            }
            if (Objects.equals(key.getName(), property2)) {
                this.propertiesOwned.replace(key, player2, player1);
                key.setOwner(player1);
            }
        }
    }

    // since this is used when player is bankrupt we do not update money
    public void resetProperties(Player owner){
        List<PropertyTile> propertiesToSell = new ArrayList<>();
        for(PropertyTile property: propertiesOwned.keySet()){
            if(owner.equals(property.getOwner())){
                propertiesToSell.add(property);
            }
        }
        for(PropertyTile property : propertiesToSell) {
            sellProperty(owner, property);
        }
    }

    public HashMap<PropertyTile, Player> getPropertiesOwned() {
        return this.propertiesOwned;
    }
    public PropertyTile stringToPropertyTile(String property_string){
        PropertyTile return_property_tile = null;
        for (PropertyTile key : this.propertiesOwned.keySet()){
            if (Objects.equals(key.getName(), property_string)) {
                return_property_tile = key;
                break;
            }
        }
        return return_property_tile;
    }
}
