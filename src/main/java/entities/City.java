package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class City extends PropertyTile implements Buildable, Auctionable, Buyable, Serializable {
    private final String colour;
    private ArrayList<Building> buildings;

    // TODO: update the entities.Board class to use this constructor for entities.City

    /**
     * Creates a new City Tile.
     * @param name The name of this city.
     * @param price The price of this city.
     * @param rent The amount of rent paid if a player lands on this city tile.
     * @param colour The colour of this city.
     */
    public City(String name, int price, int rent, String colour) {
        super(name, price, rent);
        this.colour = colour;
        this.buildings = new ArrayList<>();
    }

    public void interact(Token token, TileOutputBoundary outBound) {
        Player player = token.getPlayer();
        List<String> acceptedResponses = new ArrayList<>();
        acceptedResponses.add("y");
        acceptedResponses.add("n");
        if(this.isOwned() && this.getOwner().equals(player)) {
            String response = outBound.getResponse("Would you like to build on your property: " +
                    this.getName() + "? Please enter Y / N.", acceptedResponses);
            if(response.equalsIgnoreCase("Y")) {
                outBound.upgradeProperty(player, this);
            } // no else case because response must be "N" or "n" so we can just go to the next move
        } else if(this.isOwned()) {
            outBound.payRent(player, this);
        } else {
            String response = outBound.getResponse("Would you like to buy " + this.getName() +
                    " for " + this.getPrice() + "? Please enter Y / N.", acceptedResponses);
            if(response.equalsIgnoreCase("Y")) {
                boolean propertyBought = outBound.buyProperty(player, this);
                if(propertyBought) {
                    outBound.notifyUser("You just bought " + this.getName() + "!");
                } else {
                    outBound.notifyUser(player.getUsername() + ", you do not have enough to buy this.");
                }
            }
            // no else case because input must be "N" or "n" so we can just go to the next move
        }
    }

    /**
     * Returns the colour of this city.
     * @return the colour of this city.
     */
    public String getColour() {return this.colour;}

    /**
     * Returns true if and only if this City's colour is the same as the City c.
     * @param c A city.
     * @return A boolean which indicates whether City c's colour is the same as this city.
     */
    public boolean sameColourBlock(City c) {
        return this.colour.equals(c.getColour());
    }

    /**
     * Add a building to this city.
     * @param b A building that is being added.
     */
    public void addBuilding(Building b) {
        buildings.add(b);
    }

    // TODO: before implementing removeBuilding, we should have a conversation about what types of
    //  buildings / building identifiers we want to use here
    // Steve: For the sake of interfaces, I am going to temporarily implement removeBuilding() Method.

    /**
     * Remove a building from this city.
     * @param b A building that is being removed.
     */
    public void removeBuilding(Building b) {buildings.remove(b); }

    /**
     * Returns a list of buildings this city possesses.
     * @return A list of buildings.
     */
    public ArrayList<Building> getBuildings(){
        return buildings;
    }
}
