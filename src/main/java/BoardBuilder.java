import java.util.List;

public class BoardBuilder implements Builder {
    private Board board;

    public BoardBuilder() {
        this.reset();
    }

    public void reset() {
        this.board = new Board();
    }

    public void createTiles(List<Tile> tiles) {
        board.setTiles(tiles);
    }

    public Board getProduct() {
        Board product = this.board;
        reset();
        return product;
    }
}
