package interfaceadapters;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class Presenter extends JFrame implements ActionListener, Serializable {

    protected static final int WIDTH = 700;
    protected static final int HEIGHT = 700;
    private static final int HEIGHT_OFFSET = 40;
    private static final int WIDTH_OFFSET = 18;
    protected GameBoardPanel boardPanel;
    private final GameSetUp gameSetUp;

    public Presenter(int numOfPlayers, GameSetUp gameSetUp) {
        setTitle("Simplified Monopoly");
        // Height + HEIGHT_OFFSET fixes rendering issues where JFrame doesn't show its entire dimensions
        // Same with WIDTH + WIDTH_OFFSET
        setSize(WIDTH + WIDTH_OFFSET, HEIGHT + HEIGHT_OFFSET);
        setVisible(true);
        setResizable(false);
        // TODO: Exit on close won't work with serialization because it will end the application
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        boardPanel = new GameBoardPanel(numOfPlayers);
        JButton quit = new JButton("Quit Game");
        setContentPane(boardPanel);
        getContentPane().add(quit);
        ImageIcon image = new ImageIcon("./images/monopoly.png");
        setIconImage(image.getImage());
        this.gameSetUp = gameSetUp;
        quit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Quit Game")){
            // dispose of the thread
            dispose();
            this.gameSetUp.quitGame(this.gameSetUp.getGc());
        }
    }
}
