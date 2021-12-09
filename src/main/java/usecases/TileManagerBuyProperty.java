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

    /**
     * Manages the process of player buying property
     *
     * @param player   the Player that wants to buy the property
     * @param property the PropertyTile to be bought
     * @return whether the purchase was completed successfully
     */
    public boolean buyProperty(Player player, PropertyTile property) {
        if (player.getCash() >= property.getPrice()) {
            propertyManager.buyProperty(player, property);
            bankManager.deductCostOfProperty(player, property);
            return true;
        }
        return false;
    }

}
