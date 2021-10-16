import java.util.ArrayList;
import java.util.List;

public class BoardManager {

    private List<Player> players;

    public BoardManager() {
        players = new ArrayList<>();
    }

    public void addPlayer(String username) {
        Player p = new Player(username);
        players.add(p);
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
