//package interfaceadapters;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class GameBoardPanel extends JPanel {
//    private static final int TOKEN_DIAMETER = 10;
//    private static final int TOKEN_1_OFFSET = -35;
//    private static final int TOKEN_2_OFFSET = -15;
//    private static final int TOKEN_3_OFFSET = 5;
//    private static final int TOKEN_4_OFFSET = 25;
//    private final int numOfPlayers;
//    private Map<Integer, Integer[]> tokenBoardLocs;
//
//    public GameBoardPanel() {
//        this.numOfPlayers = 2;
//        setBackground(Color.white);
//        this.tokenBoardLocs = initializeTokenBoardLocs(numOfPlayers);
//    }
//
//    /**
//     * Draw the initial state of the Simplified Monopoly Board
//     */
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        drawGridLines(g);
//        drawTileNames(g);
//        drawTokens(g);
//        //drawPlayerStats(g);
//    }
//
//    /**
//     * Update the Game Board when a Token moves
//     * @param i Index corresponding to the token that should be moved
//     * @param tile Tile number that the token should be drawn on
//     * Preconditions: - 0 <= tile <= 23
//     *                - 1 <= i <= 4
//     */
//    public void updateBoard(int i, int tile) {
//        // Get the updated coordinates corresponding to the tile that the token is now on and update
//        // tokeBoardLocs to reflect this change
//        Integer[] newTileCoords = GameBoardConstants.NUM_TO_LOC.get(tile);
//        this.tokenBoardLocs.replace(i, newTileCoords);
//
//        // Repaint the board
//        revalidate();
//        repaint();
//    }
//
////    /**
////     * Draw the player stats (username, net worth, and cash) below the board.
////     */
////    public void drawPlayerStats(g) {
////
////    }
//
//    /**
//     * Draw the grid lines of the game board
//     */
//    private void drawGridLines(Graphics g) {
//        g.setColor(Color.black);
//        // draw grid lines on top row
//        for(int i = 0; i < 7; i++) {
//            g.drawLine(i * Presenter.BOARD_WIDTH / 7, 0, i * Presenter.BOARD_WIDTH / 7,  Presenter.BOARD_HEIGHT / 7);
//        }
//        // line to close boundary of squares
//        g.drawLine(0, Presenter.BOARD_HEIGHT / 7, Presenter.BOARD_WIDTH, Presenter.BOARD_HEIGHT / 7);
//        // draw grid lines on bottom row
//        for(int i = 0; i < 7; i++) {
//            g.drawLine(i * Presenter.BOARD_WIDTH / 7, Presenter.BOARD_HEIGHT,
//                    i * Presenter.BOARD_WIDTH / 7,  Presenter.BOARD_HEIGHT - (Presenter.BOARD_HEIGHT / 7));
//        }
//        // line to close boundary of squares
//        g.drawLine(0, Presenter.BOARD_HEIGHT - (Presenter.BOARD_HEIGHT / 7), Presenter.BOARD_WIDTH,
//                Presenter.BOARD_HEIGHT - (Presenter.BOARD_HEIGHT / 7));
//        //draw grid lines on left side
//        for(int i = 1; i < 6; i++) {
//            g.drawLine(0, i * Presenter.BOARD_HEIGHT / 7, Presenter.BOARD_WIDTH / 7,  i * Presenter.BOARD_HEIGHT / 7);
//        }
//        // line to close boundary of squares
//        g.drawLine(Presenter.BOARD_WIDTH / 7, 0, Presenter.BOARD_WIDTH / 7, Presenter.BOARD_HEIGHT);
//        // draw grid lines on right side
//        for(int i = 1; i < 6; i++) {
//            g.drawLine(Presenter.BOARD_WIDTH, i * Presenter.BOARD_HEIGHT / 7,
//                    Presenter.BOARD_WIDTH - (Presenter.BOARD_WIDTH / 7),  i * Presenter.BOARD_HEIGHT / 7);
//        }
//        // line to close boundary of squares
//        g.drawLine(Presenter.BOARD_WIDTH - (Presenter.BOARD_WIDTH / 7), 0,
//                Presenter.BOARD_WIDTH - (Presenter.BOARD_WIDTH / 7), Presenter.BOARD_HEIGHT);
//    }
//
//    /**
//     * Draw the names of the tiles on the game board
//     */
//    private void drawTileNames(Graphics g) {
//        g.setColor(Color.black);
//        g.setFont(new Font("default", Font.BOLD, 12));
//        g.drawString("Start", GameBoardConstants.START_X, GameBoardConstants.START_Y);
//        g.drawString("Rio", GameBoardConstants.RIO_X, GameBoardConstants.RIO_Y);
//        g.drawString("Delhi", GameBoardConstants.DELHI_X - 5, GameBoardConstants.DELHI_Y);
//        g.drawString("Harbour", GameBoardConstants.HARBOUR_X - 10, GameBoardConstants.HARBOUR_Y);
//        g.drawString("Cairo", GameBoardConstants.CAIRO_X - 5, GameBoardConstants.CAIRO_Y);
//        g.drawString("Madrid", GameBoardConstants.MADRID_X - 7, GameBoardConstants.MADRID_Y);
//        g.drawString("Surprise", GameBoardConstants.SURPRISE_X - 12, GameBoardConstants.SURPRISE_Y);
//        g.drawString("Jail", GameBoardConstants.JAIL_X, GameBoardConstants.JAIL_Y);
//        g.drawString("Seoul", GameBoardConstants.SEOUL_X - 5, GameBoardConstants.SEOUL_Y);
//        g.drawString("Toronto", GameBoardConstants.TORONTO_X- 12, GameBoardConstants.TORONTO_Y);
//        g.drawString("Railway", GameBoardConstants.RAILWAY_X - 12, GameBoardConstants.RAILWAY_Y);
//        g.drawString("Berlin", GameBoardConstants.BERLIN_X - 7, GameBoardConstants.BERLIN_Y);
//        g.drawString("Jakarta", GameBoardConstants.JAKARTA_X - 12, GameBoardConstants.JAKARTA_Y);
//        g.drawString("Zurich", GameBoardConstants.ZURICH_X - 10, GameBoardConstants.ZURICH_Y);
//        g.drawString("Riyadh", GameBoardConstants.RIYADH_X - 10, GameBoardConstants.RIYADH_Y);
//        g.drawString("Electricity", GameBoardConstants.ELECTRICITY_X - 20, GameBoardConstants.ELECTRICITY_Y);
//        g.drawString("Beijing", GameBoardConstants.BEIJING_X - 10, GameBoardConstants.BEIJING_Y);
//        g.drawString("Dubai", GameBoardConstants.DUBAI_X - 5, GameBoardConstants.DUBAI_Y);
//        g.drawString("Auction",  GameBoardConstants.AUCTION_X - 10, GameBoardConstants.AUCTION_Y);
//        g.drawString("Paris",  GameBoardConstants.PARIS_X - 5, GameBoardConstants.PARIS_Y);
//        g.drawString("Hong Kong",  GameBoardConstants.HONG_KONG_X - 17, GameBoardConstants.HONG_KONG_Y);
//        g.drawString("Airport",  GameBoardConstants.AIRPORT_X - 10, GameBoardConstants.AIRPORT_Y);
//        g.drawString("Tokyo",  GameBoardConstants.TOKYO_X - 5, GameBoardConstants.TOKYO_Y);
//        g.drawString("New York",  GameBoardConstants.NEW_YORK_X - 12, GameBoardConstants.NEW_YORK_Y);
//    }
//
//    /**
//     * Draw tokens at their current positions on game board
//     * Preconditions: - 2 <= i <= 4
//     */
//    private void drawTokens(Graphics g) {
//        for(int i = 1; i <= this.numOfPlayers; i++) {
//            // Draw token corresponding to index i on its correct tile
//            drawToken(g, i, this.tokenBoardLocs.get(i));
//        }
//    }
//
//
//    /**
//     * Draw the corresponding token on the screen. Helper method for drawTokens.
//     * @param g Graphics object used to do the drawing
//     * @param i Index corresponding to the token that should be drawn
//     * @param tileCoords Coordinates of the tile that the token should be drawn on
//     * Preconditions: - 0 <= tile <= 23
//     *                - 1 <= i <= 4
//     */
//    private void drawToken(Graphics g, int i, Integer[] tileCoords) {
//        int x = tileCoords[0];
//        int y = tileCoords[1];
//
//        switch (i) {
//            case 1: g.setColor(Color.red);
//                g.fillOval(x, y + TOKEN_1_OFFSET, TOKEN_DIAMETER, TOKEN_DIAMETER);
//                break;
//            case 2:
//                g.setColor(Color.blue);
//                g.fillOval(x, y + TOKEN_2_OFFSET, TOKEN_DIAMETER, TOKEN_DIAMETER);
//                break;
//            case 3:
//                g.setColor(Color.green);
//                g.fillOval(x, y + TOKEN_3_OFFSET, TOKEN_DIAMETER, TOKEN_DIAMETER);
//                break;
//            case 4:
//                g.setColor(Color.orange);
//                g.fillOval(x, y + TOKEN_4_OFFSET, TOKEN_DIAMETER, TOKEN_DIAMETER);
//        }
//    }
//
//    /**
//     * Initialize the locations of each token at the beginning of the game
//     */
//    private HashMap<Integer, Integer[]> initializeTokenBoardLocs(int numOfPlayers) {
//        HashMap<Integer, Integer[]> tokenBoardLocs = new HashMap<>();
//        for(int i = 1; i <= numOfPlayers; i++) {
//            // Put each token on the start tile at the beginning of the game
//            tokenBoardLocs.put(i, new Integer[]{GameBoardConstants.START_X, GameBoardConstants.START_Y});
//        }
//        return tokenBoardLocs;
//    }
//
//}
