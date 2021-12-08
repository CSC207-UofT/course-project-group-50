package interfaceadapters;

import datatransferobj.PlayerDTO;
import datatransferobj.TileDTO;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {
    protected static final int FRAME_WIDTH = 700;
    protected static final int FRAME_HEIGHT = 800;
    private int numOfPlayers;

    protected BoardPanel boardPanel;
    protected PlayerPanel playerPanel;

    private Map<Integer, Color> playerToColour;
    private final Map<String, Integer> usernameToNum;
    private Map<Integer, Integer> tokenTo1DPosition;

    public GamePanel() {
        setLayout(new BorderLayout());

        initializePlayerColours();

        this.boardPanel = new BoardPanel();
        this.playerPanel = new PlayerPanel();

        this.usernameToNum = new HashMap<>();
        this.tokenTo1DPosition = new HashMap<>();

        add(this.boardPanel, "Center");
        add(this.playerPanel, "South");
    }

    public void update(Map<String, TileDTO> boardData, Map<Integer, PlayerDTO> playerData) {
        if(this.numOfPlayers == 0){
            constantSetUp(playerData);
        }

        initializeMaps(boardData, playerData);
        this.boardPanel.update(this.numOfPlayers, boardData, this.playerToColour, this.usernameToNum,
                this.tokenTo1DPosition);
        this.playerPanel.update(this.numOfPlayers, playerData, this.playerToColour);
    }

    private void constantSetUp(Map<Integer, PlayerDTO> playerData){
        this.numOfPlayers = playerData.size();
        for (int i = 1; i <= numOfPlayers; i++) {
            this.usernameToNum.put(playerData.get(i).username, i);
        }

    }

    private void initializeMaps(Map<String, TileDTO> boardData, Map<Integer, PlayerDTO> playerData){
        for (int i = 1; i <= numOfPlayers; i++) {
            this.tokenTo1DPosition.put(i, playerData.get(i).location);
        }
    }

    private void initializePlayerColours() {
        this.playerToColour = new HashMap<>();
        this.playerToColour.put(1, new Color(153, 80, 0));
        this.playerToColour.put(2, new Color(0, 108, 209));
        this.playerToColour.put(3, new Color(26, 255, 26));
        this.playerToColour.put(4, new Color(75, 0, 146));
    }
}
