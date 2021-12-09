package entities;

import datatransferobj.TileDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements Serializable {
    public static final int BOARD_SIZE = 28;
    public List<Tile> tiles;

    public Board() {
        this.tiles = new ArrayList<>();
    }

    /**
     * Return the tile at the given index
     *
     * @param index The index for the required tile
     * @return Tile at the requested index
     */
    public Tile getTileAt(int index) {
        return this.tiles.get(index);
    }

    /**
     * Set the tiles of the Board
     *
     * @param tiles List of tiles that the tiles on the board need to be set to
     */
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    /**
     * Return a formatted HashMap data structure that will eventually
     * be passed through output boundaries (respecting Clean Architecture) to the presenter
     *
     * @return A formatted HashMap data structure
     */
    public Map<String, TileDTO> getBoardDataTransferObj() {
        Map<String, TileDTO> boardData = new HashMap<>();
        TileDTO dto;

        for (int i = 0; i < BOARD_SIZE; i++) {
            Tile t = this.tiles.get(i);
            String name = t.getName();

            String owner = null;
            if (t instanceof City) {
                int price = ((City) t).getPrice();
                int rent = ((City) t).getRent();
                int block = ((City) t).getBlock();

                Boolean owned = t.isOwned();
                if (owned) {
                    owner = ((City) t).getOwner().getUsername();
                }

                dto = new TileDTO(name, i, owned, owner, price, rent, block);
            } else if (t instanceof PublicProperty) {
                int rent = ((PublicProperty) t).getRent();
                dto = new TileDTO(name, i, false, owner, 0, rent, 8);
            } else {
                dto = new TileDTO(name, i, false, owner, 0, 0, 9);
            }
            boardData.put(name, dto);
        }

        return boardData;
    }
}