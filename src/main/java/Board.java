import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Board implements Serializable {
    public List<Tile> tiles;

    public Board(){
        this.tiles = new ArrayList<>();
    }

    public Tile getTileAt(int index){
        return this.tiles.get(index);
    }

    public void setTiles(List<Tile> tiles) {this.tiles = tiles;}

}
