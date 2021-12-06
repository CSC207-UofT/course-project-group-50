package usecases;

import entities.City;
import entities.Player;
import entities.PropertyTile;

import java.io.Serializable;
import java.util.List;

public class BankManager implements Serializable {

    public void payRent(Player renter, Player payee, PropertyTile property){
        int rent = property.getRent();

        payee.subtractCash(rent);
        payee.subtractNetWorth(rent);

        // if the property is a public property, renter would be null, so we need this check
        if(property instanceof City) {
            renter.addCash(rent);
            renter.addNetWorth(rent);
        }
    }

    /**
     * Determine whether any player's net worth is greater than or equal to a certain value
     * @param players The players whose net worths we are checking
     * @param netWorth The net worth goal we wish to compare to
     * @return Whether any player's net worth is greater than or equal to netWorth
     */
    public boolean anyNetworthGreater(List<Player> players, int netWorth) {
        for(Player p : players) {
            if(p.getNetWorth() >= netWorth) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if a player is bankrupt
     * @param player The player whose bankruptcy status is checked
     * @return Return whether player is bankrupt
     */
    public boolean checkBankruptcy(Player player) {
        return player.getNetWorth() <= 0 || player.getCash() <= -500;
    }

    /**
     * Determine whether the player's net worth is greater than or equal to a certain value
     * @param player The player whose net worths we are checking
     * @param netWorth The net worth goal we wish to compare to
     * @return Whether the given player's net worth is greater than or equal to netWorth
     */
    public boolean netWorthGreater(Player player, int netWorth) {
        return player.getNetWorth() >= netWorth;
    }

    public void deductCostOfProperty(Player buyer, PropertyTile property){
        buyer.subtractCash(property.getPrice());
        // buyer.subtractNetWorth(property.getPrice());
        // no net worth subtraction as asset of property equal to cost in cash
    }

    public void addSellbackOfProperty(Player seller, PropertyTile property) {
        seller.addCash(property.getSalePrice());
        // net worth goes down the loss the seller incurs
        seller.subtractNetWorth(property.getPrice() - property.getSalePrice());
    }

    public void updateCashPropertySwap(Player player1, Player player2) {
        player1.addCash(100);
        player2.addCash(100);
        player1.addNetWorth(100);
        player2.addNetWorth(100);
    }

    public void passStart(Player player){
        player.addCash(200);
        player.addNetWorth(200);
    }

    public void addMoney(Player player, int amount){
        player.addCash(amount);
        player.addNetWorth(amount);
    }

    public void subtractMoney(Player player, int amount){
        player.subtractCash(amount);
        player.subtractNetWorth(amount);
    }
}