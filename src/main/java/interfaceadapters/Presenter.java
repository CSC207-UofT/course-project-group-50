package interfaceadapters;

import javax.swing.*;

public class Presenter extends JFrame {

    protected static final int WIDTH = 700;
    protected static final int HEIGHT = 700;
    private static final int HEIGHT_OFFSET = 40;
    private static final int WIDTH_OFFSET = 18;

    public Presenter() {
        setTitle("Simplified Monopoly");
        // Height + HEIGHT_OFFSET fixes rendering issues where JFrame doesn't show its entire dimensions
        // Same with WIDTH + WIDTH_OFFSET
        setSize(WIDTH + WIDTH_OFFSET, HEIGHT + HEIGHT_OFFSET);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel boardPanel = new GameBoardPanel();
        setContentPane(boardPanel);
    }

    /**
     * FOR TESTING ONLY.
     * TODO: DELETE AFTER DONE TESTING
     */
    public static void main(String[] args) {
        Presenter p = new Presenter();
    }

}
