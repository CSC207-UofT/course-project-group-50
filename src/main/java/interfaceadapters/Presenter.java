package interfaceadapters;

import javax.swing.*;

public class Presenter extends JFrame {

    protected static final int WIDTH = 700;
    protected static final int HEIGHT = 700;
    private static final int HEIGHT_OFFSET = 40;
    private static final int WIDTH_OFFSET = 18;
    protected GameBoardPanel boardPanel;

    public Presenter(int numOfPlayers) {
        setTitle("Simplified Monopoly");
        // Height + HEIGHT_OFFSET fixes rendering issues where JFrame doesn't show its entire dimensions
        // Same with WIDTH + WIDTH_OFFSET
        setSize(WIDTH + WIDTH_OFFSET, HEIGHT + HEIGHT_OFFSET);
        setVisible(true);
        setResizable(false);
        // TODO: Exit on close won't work with serialization because it will end the application
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        boardPanel = new GameBoardPanel(numOfPlayers);
        setContentPane(boardPanel);
    }

}
