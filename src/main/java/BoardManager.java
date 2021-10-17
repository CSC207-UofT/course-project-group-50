import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardManager {

    private static final int BOARD_SIZE = 24;
    private List<Player> players;
    private Map<Player, Integer> locations;
    private Tile[] board;

    public BoardManager() {
        this.players = new ArrayList<>();
        this.board = initializeBoard();
    }

    public void addPlayer(String username) {
        Player p = new Player(username);
        players.add(p);
    }

    public Tile[] initializeBoard() {
        // create array of tiles
        return new Tile[BOARD_SIZE];
    }

    public void initializeLocations() {
        for(Player p: this.players) {
            // players start on tile 0
            this.locations.put(p, 0);
        }
    }

    public void updatePos(Player player, int tilesToAdvance) {
        int tileOfPlayer = advance(player, tilesToAdvance);
        board[tileOfPlayer].interact();
    }

    public int advance(Player player, int tilesToAdvance) {
        int newLoc = (this.locations.get(player) + tilesToAdvance) % BOARD_SIZE;
        this.locations.put(player, newLoc);
        return newLoc;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public int numOfPlayers() {
        return this.players.size();
    }

    @Override
    public String toString() {
        String s = "";
        for (Player p : players) {
            s += p.toString();
            s += "\n";
        }
        return s;
    }
}
