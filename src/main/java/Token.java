public class Token implements Movable{
    private int location;
    private String colour;

    public Token() {
        this.location = 0;
    }

    public Token(String colour) {
        this.location = 0;
        this.colour = colour;
    }

    public int getLocation() {
        return this.location;
    }

    public String getColour() {
        return this.colour;
    }

    public void move(int newPosition) {}
}
