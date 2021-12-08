package interfaceadapters;

import datatransferobj.TileDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardPanel extends JPanel {
    protected static final int BOARD_WIDTH = 700;
    protected static final int BOARD_HEIGHT = 700;

    protected final int PRICE = 4;
    protected final int NAME = 5;

    private final Map<Point2D, List<JComponent>> positionToTile;
    private JPanel[][] boardPanelHolder;

    private Map<Integer, Color> blockToColour;
    private Map<Integer, Point2D> tokenPositions;

    public BoardPanel(){
        setLayout(new GridLayout(8, 8));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        this.positionToTile = new HashMap<>();
        this.tokenPositions = new HashMap<>();

        initializeBoardColours();
        buildTemplate();
    }

    private void buildTemplate() {
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
                add(tile);
            }
        }
    }

    public void update(int numOfPlayers, Map<String, TileDTO> boardData, Map<Integer, Color> playerToColour,
                       Map<String, Integer> usernameToNum, Map<Integer, Integer> tokenTo1DPosition){
        if(tokenPositions.size() > 0){
            resetTokens(numOfPlayers, tokenPositions);
        }

        updateTokenPositions(tokenTo1DPosition);
        drawTokens(numOfPlayers, tokenPositions, playerToColour);
        refreshBoard(boardData, playerToColour, usernameToNum);
    }

    private void updateTokenPositions(Map<Integer, Integer> tokenTo1DPosition) {
        for(Map.Entry<Integer, Integer> token : tokenTo1DPosition.entrySet()){
            this.tokenPositions.put(token.getKey(), translate1DTo2D(token.getValue()));
        }
    }

    private void refreshBoard(Map<String, TileDTO> boardData, Map<Integer, Color> playerToColour,
                              Map<String, Integer> usernameToNum){
        for(Map.Entry<String, TileDTO> city : boardData.entrySet()) {
            String key = city.getKey();
            TileDTO value = city.getValue();

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
                    tileTag.setForeground(playerToColour.get(usernameToNum.get(value.owner)));
                } else if (value.price == 0){
                    tileTag.setForeground(Color.RED);
                } else {
                    tileTag.setText("Price: $" + value.price);
                    tileTag.setBackground(Color.WHITE);
                }
            }
        }
    }

    private Point2D translate1DTo2D(int index1D){
        if(index1D < 8) {
            return new Point2D.Double(7 - index1D, 0);
        } else if(index1D <  14) {
            return new Point2D.Double(0, index1D % 7 );
        } else if (index1D < 22) {
            return new Point2D.Double(index1D - (7 * 2), 7);
        } else {
            return new Point2D.Double(7, 7 - (index1D % 7));
        }
    }

    private void initializeBoardColours() {
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
    }

    private void resetTokens(int numOfPlayers, Map<Integer, Point2D> tokenPositions){
        for(int i = 1; i <= numOfPlayers; i++){
            Point2D position = tokenPositions.get(i);
            List<JComponent> tileComponent = this.positionToTile.get(position);
            tileComponent.get(i - 1).setBackground(Color.WHITE);
        }
    }

    private void drawTokens(int numOfPlayers, Map<Integer, Point2D> tokenPositions,
                            Map<Integer, Color> playerToColour){
        for(int i = 1; i <= numOfPlayers; i++){
            Point2D position = tokenPositions.get(i);
            List<JComponent> tileComponent = this.positionToTile.get(position);
            tileComponent.get(i - 1).setBackground(playerToColour.get(i));
        }
    }
}
