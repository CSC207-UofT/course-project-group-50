package interfaceadapters;

import datatransferobj.PlayerDTO;
import datatransferobj.TileDTO;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds the GUI from constituent BoardPanel and PlayerPanel objects.
 */
public class GamePanel extends JPanel {
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

    /**
     * Refreshes the GUI using information from DTOs, delegating to BoardPanel and PlayerPanel
     * @param boardData a DTO containing an updated copy of board information, including prices, ownership, and rent
     * @param playerData a DTO containing raw information on player name, number, cash, net worth, etc
     */
    public void update(Map<String, TileDTO> boardData, Map<Integer, PlayerDTO> playerData) {
        if(this.numOfPlayers == 0){
            constantSetUp(playerData);
        }

        initializeMaps(boardData, playerData);
        this.boardPanel.update(this.numOfPlayers, boardData, this.playerToColour, this.usernameToNum,
                this.tokenTo1DPosition);
        this.playerPanel.update(this.numOfPlayers, playerData, this.playerToColour);
    }

    /**
     * Instantiates the attributes and objects that are constant throughout the game, and only need to be "updated" once,
     * i.e. when we first receive a copy of the DTOs
     * @param playerData a DTO containing raw information on player name, number, cash, net worth, etc
     */
    private void constantSetUp(Map<Integer, PlayerDTO> playerData){
        this.numOfPlayers = playerData.size();
        for (int i = 1; i <= numOfPlayers; i++) {
            this.usernameToNum.put(playerData.get(i).username, i);
        }

    }

    /**
     * Initializes a map that takes each token's number to its 1D location in the tile array.
     * @param boardData a DTO containing an updated copy of board information, including prices, ownership, and rent
     * @param playerData a DTO containing raw information on player name, number, cash, net worth, etc
     */
    private void initializeMaps(Map<String, TileDTO> boardData, Map<Integer, PlayerDTO> playerData){
        for (int i = 1; i <= numOfPlayers; i++) {
            this.tokenTo1DPosition.put(i, playerData.get(i).location);
        }
    }

    /**
     * Initializes a map that takes each block number to a Colour optimized for colour-blind users.
     */
    private void initializePlayerColours() {
        this.playerToColour = new HashMap<>();
        this.playerToColour.put(1, new Color(153, 80, 0));
        this.playerToColour.put(2, new Color(0, 108, 209));
        this.playerToColour.put(3, new Color(26, 255, 26));
        this.playerToColour.put(4, new Color(75, 0, 146));
    }
}
