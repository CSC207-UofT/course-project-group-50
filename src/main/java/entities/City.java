package entities;

import interfaces.Auctionable;
import interfaces.Buildable;
import interfaces.Buyable;

import java.io.Serializable;
import java.util.ArrayList;

public class City extends PropertyTile implements Buildable, Auctionable, Buyable, Serializable {
    private final String colour;
    private ArrayList<Building> buildings;

    // TODO: update the entities.Board class to use this constructor for entities.City
    public City(String name, int price, int rent, String colour) {
        super(name, price, rent);
        this.colour = colour;
        this.buildings = new ArrayList<>();
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
