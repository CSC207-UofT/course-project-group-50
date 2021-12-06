package datatransfer;

import java.awt.geom.Point2D;

/**
 * Simple struct/Data Transfer object for passing tile information through layers according to Clean Architecture.
 */
public class TileData {
    public String name;
    public Point2D position;
    public Boolean owned;
    public int price;
    public int rent;
    public int block;
    public String owner;

    public TileData(String name, Point2D position, Boolean owned, String owner, int price, int rent, int block) {
        this.name = name;
        this.position = position;
        this.owned = owned;
        this.owner = owner;
        this.price = price;
        this.rent = rent;
        this.block = block;
    }
}
