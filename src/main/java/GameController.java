import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class GameController {
    private BoardManager boardManager;
    private long id;
    private final String filepath;
    private final int netWorthGoal;
    private final ArrayList<Player> players;

    public GameController(long id, String filepath, ArrayList<Player> players) {
        this.id = id;
        this.filepath = filepath;
        this.players = players;
        this.netWorthGoal = 5000;
        this.boardManager = new BoardManager();
    }

    public GameController(long id, String filepath, ArrayList<Player> players, int netWorthGoal) {
        this.id = id;
        this.filepath = filepath;
        this.players = players;
        this.netWorthGoal = netWorthGoal;
        this.boardManager = new BoardManager();
    }

    public void runPlayerSetUp(List<String> usernames){
        for(String s : usernames) {
            boardManager.addPlayer(s);
        }
        System.out.println("\n PLAYER LIST");
        System.out.println(boardManager);
    }

    public void runGame() {
        // not sure where we are getting the usernames from
        List<String> usernames = new ArrayList<>();
        runPlayerSetUp(usernames);
        List<Integer> order = generateOrder();

        while(!isWinner()) {
            for(int i : order) {
                Player currPlayer = this.boardManager.getPlayers().get(i);
                int currRoll = currPlayer.roll();
                currPlayer.getToken().move(currRoll);
            }
            updateBankruptcy();
        }
    }

    public List<Integer> generateOrder() {
        List<Integer> order = new ArrayList<>();
        for(int i = 0; i < this.boardManager.numOfPlayers(); i++) {
            order.add(i);
        }

        Collections.shuffle(order);
        return order;
    }

    public boolean isWinner() {
        for(Player p : this.boardManager.getPlayers()) {
            if(p.getNetWorth() == this.netWorthGoal) {
                return true;
            }
        }
        return false;
    }

    // there may be a more efficient way to do this but im unsure how our game loop
    // will go so i'm just leaving it here
    public void updateBankruptcy() {
        for(Player p: this.boardManager.getPlayers()) {
            if(p.getCash() < -500 || p.getNetWorth() == 0) {
                p.setBankrupt(true);
            }
        }
    }

    public String getFilepath() {
        return this.filepath;
    }



}
