import java.util.List;

public class GameController {
    private BoardManager boardManager;

    public GameController(){
        this.boardManager = new BoardManager();
    }

    public void runPlayerSetUp(List<String> usernames){
        for(String s : usernames) {
            boardManager.addPlayer(s);
        }
        System.out.println("\n PLAYER LIST");
        System.out.println(boardManager);
    }
}
