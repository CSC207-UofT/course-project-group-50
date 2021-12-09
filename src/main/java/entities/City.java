package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class City extends PropertyTile implements Buildable, Auctionable, Buyable, Serializable {
    private final int block;
    private final ArrayList<Building> buildings;

    /**
     * Creates a new City Tile.
     *
     * @param name  The name of this city.
     * @param price The price of purchase this city.
     * @param rent  The amount of rent of this city.
     * @param block The block of this city, indicates the colour of the tile on the board
     */
    public City(String name, int price, int rent, int block) {
        super(name, price, rent);
        this.block = block;
        this.buildings = new ArrayList<>();
    }

    /**
     * Manages the sequence of events after a player lands on a City tile.
     *
     * @param token    the player's token
     * @param outBound instance of an object that implements TileOutputBoundary interface to pass back
     *                 information to outer layers while adhering to Clean Architecture
     */
    public void interact(Token token, TileOutputBoundary outBound) {
        Player player = token.getPlayer();
        List<String> acceptedResponses = new ArrayList<>();
        acceptedResponses.add("y");
        acceptedResponses.add("n");
        if (this.isOwned() && this.getOwner().equals(player)) {
            String response = outBound.getResponse("Would you like to build on your property: " +
                    this.getName() + "? Please enter Y / N.", acceptedResponses);
            if (response.equalsIgnoreCase("Y")) {
                outBound.upgradeProperty(player, this);
            } // no else case because response must be "N" or "n" so we can just go to the next move
        } else if (this.isOwned()) {
            outBound.payRent(player, this);
        } else {
            String response = outBound.getResponse("Would you like to buy " + this.getName() +
                    " for " + this.getPrice() + "? Please enter Y / N.", acceptedResponses);
            if (response.equalsIgnoreCase("Y")) {
                boolean propertyBought = outBound.buyProperty(player, this);
                if (propertyBought) {
                    outBound.notifyUser("You just bought " + this.getName() + "!");
                } else {
                    outBound.notifyUser(player.getUsername() + ", you do not have enough to buy this.");
                }
            }
            // no else case because input must be "N" or "n" so we can just go to the next move
        }
    }

    /**
     * Returns the block number of this city.
     *
     * @return the block number of this city.
     */
    public int getBlock() {
        return this.block;
    }

    /**
     * Returns true if and only if this City's block is the same as the City c.
     *
     * @param c A city.
     * @return A boolean which indicates whether City c's block is the same as this city.
     */
    public boolean sameColourBlock(City c) {
        return this.block == c.getBlock();
    }

    /**
     * Add a building to this city.
     *
     * @param b A building that is being added.
     */
    public void addBuilding(Building b) {
        buildings.add(b);
    }

    /**
     * Remove a building from this city.
     *
     * @param b A building that is being removed.
     */
    public void removeBuilding(Building b) {
        buildings.remove(b);
    }

    /**
     * Returns a list of buildings this city possesses.
     *
     * @return A list of buildings.
     */
    public ArrayList<Building> getBuildings() {
        return buildings;
    }
}
