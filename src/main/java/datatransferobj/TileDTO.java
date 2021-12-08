package datatransferobj;

// Simple data transfer object for passing tile information through layers according to Clean Architecture.
public class TileDTO {
    public String name;
    public int position;
    public Boolean owned;
    public int price;
    public int rent;
    public int block;
    public String owner;

    public TileDTO(String name, int position, Boolean owned, String owner, int price, int rent, int block) {
        this.name = name;
        this.position = position;
        this.owned = owned;
        this.owner = owner;
        this.price = price;
        this.rent = rent;
        this.block = block;
    }
}