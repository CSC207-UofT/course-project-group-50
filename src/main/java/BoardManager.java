import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardManager implements Serializable {

    public static final int BOARD_SIZE = 28;
    private List<Player> players;
    private Map<Player, Integer> locations;
    private final Board board;

    public BoardManager() {
        this.players = new ArrayList<>();
        this.board = new Board();
    }

    public void addPlayer(String username) {
        Player p = new Player(username);
        players.add(p);
    }

    public Tile[] initializeBoard() {
        // create array of tiles
        return new Tile[BOARD_SIZE];
    }
    
    public List<Player> getPlayers() {
        return this.players;
    }

    public int numOfPlayers() {
        return this.players.size();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Player p : players) {
            s.append(p.toString());
            s.append("\n");
        }
        return s.toString();
    }

    public Board getBoard(){
        return this.board;
    }

    public List<Tile> getBoardList(){
        return this.board.tiles;
    }
}
