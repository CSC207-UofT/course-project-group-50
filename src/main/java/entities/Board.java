package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    public List<Tile> tiles;

    public Board(){
        this.tiles = new ArrayList<>();
    }

    /**
     * Return the tile at the given index
     * @param index The index for the required tile
     * @return Tile at the requested index
     */
    public Tile getTileAt(int index){
        return this.tiles.get(index);
    }

    /**
     * Set the tiles of the Board
     * @param tiles List of tiles that the tiles on the board need to be set to
     */
    public void setTiles(List<Tile> tiles) {this.tiles = tiles;}

}
