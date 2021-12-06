package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class City extends PropertyTile implements Buildable, Auctionable, Buyable, Serializable {
    private final String colour;
    private ArrayList<Building> buildings;

    // TODO: update the entities.Board class to use this constructor for entities.City
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

    public String getColour() {return this.colour;}

    public boolean sameColourBlock(City c) {
        return this.colour.equals(c.getColour());
    }

    public void addBuilding(Building b) {
        buildings.add(b);
    }

    // TODO: before implementing removeBuilding, we should have a conversation about what types of
    //  buildings / building identifiers we want to use here
    // Steve: For the sake of interfaces, I am going to temporarily implement removeBuilding() Method.
    public void removeBuilding(Building b) {buildings.remove(b); }

    public ArrayList<Building> getBuildings(){
        return buildings;
    }
}
