import java.util.Arrays;
import java.util.List;

public class Board {
    public final List<Tile> tiles;

    public Board(){
        this.tiles = Arrays.asList(new StartTile(), new City(), new City(), new City(), new SpecialTile(),
                new City(), new City(), new SpecialTile(), new City(), new City(), new City(), new PublicProperty(),
                new City(), new City(), new JailTile(), new City(), new City(), new City(), new PublicProperty(),
                new City(), new City(), new City(), new City(), new City());
    }

    public Tile getTileAt(int index){
        return this.tiles.get(index);
    }

}
