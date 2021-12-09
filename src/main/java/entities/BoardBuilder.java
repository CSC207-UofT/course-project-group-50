package entities;

import java.util.List;

public class BoardBuilder implements Builder {
    private Board board;

    public BoardBuilder() {
        this.reset();
    }

    /**
     * Resets board instance attribute, allowing for a new Board to be built
     */
    public void reset() {
        this.board = new Board();
    }

    /**
     * Arranges tiles to form the board.
     *
     * @param tiles The tiles that will be arranged to form the Board
     */
    public void createTiles(List<Tile> tiles) {
        board.setTiles(tiles);
    }

    /**
     * Returns the newly assembled Board object and prepares for next build request.
     *
     * @return The newly assembled Board.
     */
    public Board getProduct() {
        Board product = this.board;
        reset();
        return product;
    }
}
