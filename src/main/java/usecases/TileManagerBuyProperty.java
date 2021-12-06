package usecases;

import entities.Player;
import entities.PropertyTile;

public class TileManagerBuyProperty {

    private final PropertyManager propertyManager;
    private final BankManager bankManager;

    public TileManagerBuyProperty(PropertyManager propertyManager, BankManager bankManager) {
        this.propertyManager = propertyManager;
        this.bankManager = bankManager;
    }

    public boolean buyProperty(Player player, PropertyTile property) {
        if(player.getCash() >= property.getPrice()){
            propertyManager.buyProperty(player, property);
            bankManager.deductCostOfProperty(player, property);
            return true;
        }
        return false;
    }

}
