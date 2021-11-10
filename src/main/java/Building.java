public class Building {
    private final float cost;
    private final float rentIncrease;

    public Building(float cost, float rentIncrease) {
        this.cost = cost;
        this.rentIncrease = rentIncrease;
    }

    public float getCost() {return this.cost;}

    public float getRentIncrease() {return this.rentIncrease;}
}
