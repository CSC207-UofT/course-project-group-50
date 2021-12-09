package entities;

public abstract class Tile {
    protected String name;
    protected Boolean owned;

    public Tile(String name, Boolean owned) {
        this.name = name;
        this.owned = owned;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Returns whether a player has bought and currently owns this tile.
     *
     * @return whether a player owns this tile
     */
    public Boolean isOwned() {
        return this.owned;
    }

    public abstract void interact(Token token, TileOutputBoundary outBound);
}