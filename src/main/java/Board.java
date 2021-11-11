import java.util.List;

public class Board {
    public final List<Tile> tiles;

    public Board(List<Tile> tiles){
        // this.tiles = Constants.Board
        this.tiles = tiles;
    }

    public Tile getTileAt(int index){
        return this.tiles.get(index);
    }

}
