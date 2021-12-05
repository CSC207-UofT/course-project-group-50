package entities;

import java.util.List;

public interface Builder {
    void reset();
    void createTiles(List<Tile> tiles);
}
