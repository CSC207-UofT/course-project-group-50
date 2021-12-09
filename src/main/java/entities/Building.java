package entities;

import java.io.Serializable;

public class Building implements Serializable {
    private final float cost;
    private final float rentIncrease;

    /**
     * Creates a new Building.
     *
     * @param cost         the cost of this building.
     * @param rentIncrease the amount of rent increased.
     */
    public Building(float cost, float rentIncrease) {
        this.cost = cost;
        this.rentIncrease = rentIncrease;
    }

    /**
     * Returns the cost of this building.
     *
     * @return The cost of the building.
     */
    public float getCost() {
        return this.cost;
    }

    /**
     * Returns the amount of rent increased.
     *
     * @return the amount of rent increased.
     */
    public float getRentIncrease() {
        return this.rentIncrease;
    }
}
