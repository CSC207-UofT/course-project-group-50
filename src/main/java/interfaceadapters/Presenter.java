package interfaceadapters;

import datatransferobj.PlayerDTO;
import datatransferobj.TileDTO;
import usecases.GameOutputBoundary;

import javax.swing.*;
import java.io.Serializable;
import java.util.Map;

public class Presenter extends JFrame implements Serializable, GameOutputBoundary { //, ActionListener {
    protected GamePanel gamePanel;
    protected static final int FRAME_WIDTH = 600;
    protected static final int FRAME_HEIGHT = 700;

    public Presenter() {
        setTitle("Simplified Monopoly");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.gamePanel = new GamePanel();
        getContentPane().add(gamePanel);

        setVisible(true);
        setResizable(false);

//        JButton quit = new JButton("Quit Game");
//        getContentPane().add(quit);
//        quit.addActionListener(this);
    }

    /**
     * Refreshes the GUI by delegating to GamePanel
     *
     * @param boardData  a DTO containing an updated copy of board information, including prices, ownership, and rent
     * @param playerData a DTO containing raw information on player name, number, cash, net worth, etc
     */
    @Override
    public void update(Map<String, TileDTO> boardData, Map<Integer, PlayerDTO> playerData) {
        getContentPane().remove(gamePanel);
        this.gamePanel.update(boardData, playerData);
        getContentPane().add(gamePanel);
        pack();
        setVisible(true);
        revalidate();
        repaint();
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals("Quit Game")){
//            // dispose of the thread
//            dispose();
//            this.gameSetUp.quitGame(this.gameSetUp.getGc());
//        }
//    }
}
