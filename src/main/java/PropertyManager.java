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
    public void sellProperty(Player seller, PropertyTile property){
        propertiesOwned.remove(property);
        property.setOwner(null);
    }

    public List<String> propertiesOwnedByPlayer(Player player){
        ArrayList<String> owned = new ArrayList<>();
        for(PropertyTile property: this.propertiesOwned.keySet()){
            if(property.getOwner() == player){
                owned.add(property.getName());
            }
        }
        return owned;
    }

    public void tradeProperties(Player player1, Player player2, BankManager bankManager) {
        // only need to ask the second player if they want to trade, because BoardManager
        // already asks the first player. We may want to consider just asking every player
        // in BoardManager instead of splitting it up like this.
        if (is_trading(player2)) {
            String propertyOneString;
            // TODO: Don't let a player trade if they have no properties
            System.out.println(player1.getUsername() + ", please enter the name of the property " +
                    "you are willing to trade, or enter C to cancel.");
            propertyOneString = CmdLineUI.scanner.nextLine();
            if (propertyOneString.equalsIgnoreCase("C")) {
                return;
            }
            PropertyTile propertyOne = stringToPropertyTile(propertyOneString);
            while(propertyOne == null || !propertiesOwned.get(propertyOne).equals(player1)) {
                System.out.println("Invalid input. Please enter the name of the property you are willing" +
                        " to trade, or enter C to cancel.");
                propertyOneString = CmdLineUI.scanner.nextLine();
                if(propertyOneString.equalsIgnoreCase("C")) {
                    return;
                }
                propertyOne = stringToPropertyTile(propertyOneString);
            }

            String propertyTwoString;

            System.out.println(player2.getUsername() + ", please enter the name of the property " +
                    "you are willing to trade, or enter C to cancel.");
            propertyTwoString = CmdLineUI.scanner.nextLine();
            if (propertyTwoString.equalsIgnoreCase("C")) {
                return;
            }
            PropertyTile propertyTwo = stringToPropertyTile(propertyTwoString);
            while(propertyTwo == null || !propertiesOwned.get(propertyTwo).equals(player2)) {
                System.out.println("Invalid input. Please enter the name of the property you are willing" +
                        " to trade, or enter C to cancel.");
                propertyTwoString = CmdLineUI.scanner.nextLine();
                if(propertyTwoString.equalsIgnoreCase("C")) {
                    return;
                }
                propertyTwo = stringToPropertyTile(propertyTwoString);
            }

            swap_properties(player1, player2, propertyOneString, propertyTwoString);
            // if the trade completes, both users receive $100 each
            bankManager.updateCashPropertySwap(player1, player2);
            System.out.println("Congratulations! By trading properties, you both receive $100!");
        } else {
            System.out.println("At least one of the players are not willing to trade, moving on to the next round.");
        }
    }

    public boolean is_trading(Player player) {
        String input;
        System.out.println(player.getUsername() + ", would you like to trade one of your properties?" +
                " Enter Y / N.");
        input = CmdLineUI.scanner.nextLine();
        return input.equalsIgnoreCase("Y");
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
        System.out.println("Properties successfully traded!");
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
