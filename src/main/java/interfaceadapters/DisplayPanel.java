package interfaceadapters;

import datatransfer.PlayerData;
import datatransfer.TileData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class DisplayPanel extends JPanel {
    protected static final int BOARD_WIDTH = 700;
    protected static final int BOARD_HEIGHT = 700;
    protected static final int FRAME_WIDTH = 700;
    protected static final int FRAME_HEIGHT = 800;
    private int numOfPlayers;

    protected JPanel boardPanel;
    protected JPanel playerPanel;

    private JPanel[] playerPanelHolder;
    private JPanel[][] boardPanelHolder;

    private Map<Integer, Color> blockToColour;
    private Map<Integer, Color> playerToColour;
    private Map<Integer, Point2D> tokenPositions;

    public DisplayPanel() {
        setLayout(new BorderLayout());

        initializeColours();
        initializeBoardPanel();
        initializePlayerPanel();
        add(this.boardPanel, "Center");
        add(this.playerPanel, "South");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTokens(g);
    }

    private void drawTokens(Graphics g) {
        for(int i = 1; i <= this.numOfPlayers; i++) {
            drawToken(g, i, this.tokenPositions.get(i));
        }
    }

    private void drawToken(Graphics g, int i, Point2D position) {
        int tileX = (int) position.getX();
        int tileY = (int) position.getY();

        int cX = boardPanelHolder[tileY][tileX].getX();
        int cY = boardPanelHolder[tileY][tileX].getY();

        g.setColor(this.playerToColour.get(i));
        g.fillOval(cX, cY, 5, 5);
    }

    private void initializeBoardPanel() {
        this.boardPanel = new JPanel();
        this.boardPanel.setLayout(new GridLayout(8, 8));
        this.boardPanel.setBackground(Color.white);
        this.boardPanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        this.boardPanelHolder = new JPanel[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.boardPanelHolder[i][j] = new JPanel();
                this.boardPanelHolder[i][j].setPreferredSize(new Dimension(BOARD_WIDTH / 8, BOARD_HEIGHT / 8));
                this.boardPanel.add(this.boardPanelHolder[i][j]);
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
        updateBoard(boardData);
        updatePlayerStats(playerData);
        revalidate();
        repaint();
    }

    private void updateBoard(Map<String, TileData> boardData){
        for(Map.Entry<String, TileData> city : boardData.entrySet()) {
            String key = city.getKey();
            TileData value = city.getValue();

            int x = (int) value.position.getX();
            int y = (int) value.position.getY();

            JPanel tilePanel =  this.boardPanelHolder[y][x];
            tilePanel.setLayout(new BorderLayout());
            tilePanel.setBorder(BorderFactory.createLineBorder(Color.black));

            JTextField tileName = new JTextField(key);
            tileName.setEditable(false);
            tileName.setBackground(this.blockToColour.get(value.block));
            tileName.setHorizontalAlignment(JTextField.CENTER);
            tileName.setBorder(javax.swing.BorderFactory.createEmptyBorder());

            tilePanel.add(tileName, "Center");

            JTextField tileTag;
            if (value.rent != 0) {
                tileTag = new JTextField("$" + String.valueOf(value.rent));
                if (value.owned) {
                    // TODO: replace this with owner
                    tileTag.setForeground(this.playerToColour.get(1));
                } else if (value.price == 0){
                    tileTag.setForeground(Color.RED);
                } else {
                    tileTag = new JTextField("$" + String.valueOf(value.price));
                    tileTag.setBackground(Color.WHITE);
                }
                tileTag.setEditable(false);
                tileTag.setHorizontalAlignment(JTextField.CENTER);
                tileTag.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                tilePanel.add(tileTag, "North");
            }
        }
    }

    private void updatePlayerStats(Map<Integer, PlayerData> playerData) {
        this.numOfPlayers = playerData.size();
        this.tokenPositions = new HashMap<>();

        for (int i = 1; i <= this.numOfPlayers; i++) {
            this.tokenPositions.put(i, translate1DTo2D(i));

            JPanel statBox = this.playerPanelHolder[i - 1];
            statBox.setLayout(new BorderLayout());
            statBox.setBackground(Color.white);

            PlayerData value = playerData.get(i);

            JTextField username = new JTextField(value.username);
            username.setEditable(false);
            username.setForeground(playerToColour.get(i));
            username.setBorder(javax.swing.BorderFactory.createEmptyBorder());

            JTextField cash = new JTextField("Cash: $" + String.valueOf(value.cash));
            cash.setEditable(false);
            cash.setBorder(javax.swing.BorderFactory.createEmptyBorder());

            JTextField netWorth = new JTextField("Net Worth: $" + String.valueOf(value.netWorth));
            netWorth.setEditable(false);
            netWorth.setBorder(javax.swing.BorderFactory.createEmptyBorder());

            statBox.add(username, "North");
            statBox.add(cash, "Center");
            statBox.add(netWorth, "South");
        }
    }

    private Point2D translate1DTo2D(int index1D){
        if (index1D < 8) {
            return new Point2D.Double(0, 7 - index1D);
        } else if(index1D <  14) {
            return new Point2D.Double(index1D % 7, 0);
        } else if (index1D < 22) {
            return new Point2D.Double(7, index1D - (7 * 2));
        } else {
            return new Point2D.Double(7 - (index1D % 7), 7);
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
