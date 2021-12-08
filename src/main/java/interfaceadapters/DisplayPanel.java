package interfaceadapters;

import datatransfer.PlayerData;
import datatransfer.TileData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplayPanel extends JPanel {
    protected static final int BOARD_WIDTH = 700;
    protected static final int BOARD_HEIGHT = 700;
    protected static final int FRAME_WIDTH = 700;
    protected static final int FRAME_HEIGHT = 800;
    private int numOfPlayers;

    protected final int PRICE = 4;
    protected final int NAME = 5;

    protected JPanel boardPanel;
    protected JPanel playerPanel;

    private final Map<Point2D, List<JComponent>> positionToTile;

    private JPanel[] playerPanelHolder;
    private JPanel[][] boardPanelHolder;


    private Map<Integer, Color> blockToColour;
    private Map<Integer, Color> playerToColour;
    private final Map<String, Integer> usernameToNum;
    private Map<Integer, Point2D> tokenPositions;

    public DisplayPanel() {
        setLayout(new BorderLayout());

        initializeColours();
        this.usernameToNum = new HashMap<>();
        this.tokenPositions = new HashMap<>();
        this.positionToTile = new HashMap<>();

        initializeBoardPanel();
        initializePlayerPanel();
        add(this.boardPanel, "Center");
        add(this.playerPanel, "South");
    }

    private void initializeBoardPanel() {
        this.boardPanel = new JPanel();
        this.boardPanel.setLayout(new GridLayout(8, 8));
        this.boardPanel.setBackground(Color.white);
        this.boardPanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        this.boardPanelHolder = new JPanel[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JPanel tile = new JPanel();
                tile.setLayout(new BorderLayout());
                tile.setPreferredSize(new Dimension(BOARD_WIDTH / 8, BOARD_HEIGHT / 8));

                JTextField tilePrice = new JTextField("");
                tilePrice.setEditable(false);
                tilePrice.setHorizontalAlignment(JTextField.CENTER);
                tilePrice.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                tile.add(tilePrice, "North");

                List<JComponent> tileComponents = new ArrayList<>();

                JPanel tokenListPanel = new JPanel();
                tokenListPanel.setBackground(Color.WHITE);
                tokenListPanel.setLayout(new FlowLayout());
                for(int k = 1; k <= 4; k++) {
                    JPanel tokenPanel = new JPanel();
                    tokenPanel.setBackground(Color.WHITE);
                    tokenListPanel.add(tokenPanel);
                    tileComponents.add(tokenPanel);
                }
                tile.add(tokenListPanel, "South");

                JTextField tileName = new JTextField("");
                tileName.setEditable(false);
                tileName.setHorizontalAlignment(JTextField.CENTER);
                tileName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                tile.add(tileName, "Center");

                tileComponents.add(tilePrice);
                tileComponents.add(tileName);

                this.positionToTile.put(new Point2D.Double(i, j), tileComponents);

                this.boardPanelHolder[i][j] = tile;
                this.boardPanel.add(tile);
            }
        }
    }

    private void initializePlayerPanel(){
        this.playerPanel = new JPanel();
        this.playerPanel.setLayout(new FlowLayout());
        this.playerPanel.setBackground(Color.white);
        this.playerPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT - BOARD_HEIGHT));

        this.playerPanelHolder = new JPanel[4];

        for (int i = 0; i < 4; i++) {
            this.playerPanelHolder[i] = new JPanel();
            this.playerPanelHolder[i].setBackground(Color.WHITE);
            this.playerPanel.add(this.playerPanelHolder[i]);
        }
    }

    public void update(Map<String, TileData> boardData, Map<Integer, PlayerData> playerData) {
        resetTokens();
        updateBoard(boardData);
        updatePlayerStats(playerData);
        drawTokens();
    }

    private void resetTokens(){
        for(int i = 1; i <= this.numOfPlayers; i++){
            Point2D position = this.tokenPositions.get(i);
            List<JComponent> tileComponent = this.positionToTile.get(position);
            tileComponent.get(i - 1).setBackground(Color.WHITE);
        }
    }

    private void drawTokens(){
        for(int i = 1; i <= this.numOfPlayers; i++){
            Point2D position = this.tokenPositions.get(i);
            List<JComponent> tileComponent = this.positionToTile.get(position);
            tileComponent.get(i - 1).setBackground(this.playerToColour.get(i));
        }
    }

    private void updateBoard(Map<String, TileData> boardData){
        for(Map.Entry<String, TileData> city : boardData.entrySet()) {
            String key = city.getKey();
            TileData value = city.getValue();

            Point2D position = translate1DTo2D(value.position);

            JPanel tile = this.boardPanelHolder[(int) position.getY()][(int) position.getX()];
            tile.setBorder(BorderFactory.createLineBorder(Color.black));

            List<JComponent> tileComponents = this.positionToTile.get(position);

            JTextField tileName =  (JTextField) tileComponents.get(NAME);
            tileName.setText(key);
            tileName.setBackground(this.blockToColour.get(value.block));

            JTextField tileTag = (JTextField) tileComponents.get(PRICE);
            if (value.rent != 0) {
                tileTag.setText("Rent: $" + value.rent);
                if (value.owned) {
                    tileTag.setForeground(this.playerToColour.get(this.usernameToNum.get(value.owner)));
                } else if (value.price == 0){
                    tileTag.setForeground(Color.RED);
                } else {
                    tileTag.setText("Price: $" + value.price);
                    tileTag.setBackground(Color.WHITE);
                }
            }
        }
    }

    private void updatePlayerStats(Map<Integer, PlayerData> playerData) {
        this.numOfPlayers = playerData.size();

        for (int i = 1; i <= this.numOfPlayers; i++) {
            this.tokenPositions.put(i, translate1DTo2D(playerData.get(i).location));

            JPanel statBox = this.playerPanelHolder[i - 1];
            statBox.removeAll();
            statBox.setLayout(new BorderLayout());
            statBox.setBackground(Color.white);

            PlayerData value = playerData.get(i);

            JTextField username = new JTextField(value.username);
            if (this.usernameToNum.size() < this.numOfPlayers) {
                this.usernameToNum.put(value.username, i);
            }

            username.setEditable(false);
            username.setForeground(playerToColour.get(i));
            username.setBorder(javax.swing.BorderFactory.createEmptyBorder());

            JTextField cash = new JTextField("Cash: $" + value.cash);
            cash.setEditable(false);
            cash.setBorder(javax.swing.BorderFactory.createEmptyBorder());

            JTextField netWorth = new JTextField("Net Worth: $" + value.netWorth);
            netWorth.setEditable(false);
            netWorth.setBorder(javax.swing.BorderFactory.createEmptyBorder());

            statBox.add(username, "North");
            statBox.add(cash, "Center");
            statBox.add(netWorth, "South");
        }
    }

    private Point2D translate1DTo2D(int index1D){
        if (index1D < 8) {
            return new Point2D.Double(7 - index1D, 0);
        } else if(index1D <  14) {
            return new Point2D.Double(0, index1D % 7 );
        } else if (index1D < 22) {
            return new Point2D.Double(index1D - (7 * 2), 7);
        } else {
            return new Point2D.Double(7, 7 - (index1D % 7));
        }
    }

    private void initializeColours() {
        this.blockToColour = new HashMap<>();
        this.blockToColour.put(1, new Color(0, 158, 115));
        this.blockToColour.put(2, new Color(86, 180, 233));
        this.blockToColour.put(3, new Color(204, 121, 167));
        this.blockToColour.put(4, new Color(230, 159, 0));
        this.blockToColour.put(5,  new Color(0, 114, 178));
        this.blockToColour.put(6, new Color(240, 228, 66));
        this.blockToColour.put(7, new Color(213, 94, 0));
        this.blockToColour.put(8, Color.LIGHT_GRAY);
        this.blockToColour.put(9, Color.WHITE);

        this.playerToColour = new HashMap<>();
        this.playerToColour.put(1, new Color(153, 80, 0));
        this.playerToColour.put(2, new Color(0, 108, 209));
        this.playerToColour.put(3, new Color(26, 255, 26));
        this.playerToColour.put(4, new Color(75, 0, 146));
    }
}
