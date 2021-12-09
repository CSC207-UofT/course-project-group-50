package usecases;

import entities.Building;
import entities.City;
import entities.Player;

public class TileManagerUpgradeProperty {

    private final UseCaseOutputBoundary outBound;

    public TileManagerUpgradeProperty(UseCaseOutputBoundary outBound) {
        this.outBound = outBound;
    }

    /**
     * Upgrades the City owned by Player by adding buildings.
     *
     * @param player the Player that owns the City
     * @param city   the City owned by the Player
     */
    public void upgradeProperty(Player player, City city) {
        int upgrade_cost = (int) Math.round(city.getPrice() * 0.4);
        if (player.getCash() >= upgrade_cost) {
            Building upgradeBuilding = new Building(upgrade_cost, city.getRent());
            player.subtractCash(upgrade_cost);
            city.addBuilding(upgradeBuilding);
            city.setPrice(city.getPrice() + upgrade_cost);
            city.setRent(city.getRent() * 2);
            this.outBound.notifyUser("Property has been upgraded, new rent is " + city.getRent());
        } else {
            this.outBound.notifyUser(player.getUsername() + ", you do not have enough to pay.");
        }
    }
}
