package interfaceadapters;
import datatransferobj.PlayerDTO;
import datatransferobj.TileDTO;
import usecases.GameOutputBoundary;

import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class Presenter extends JFrame implements Serializable, GameOutputBoundary { //, ActionListener {
    protected GamePanel gamePanel;
    protected static final int FRAME_WIDTH = 700;
    protected static final int FRAME_HEIGHT = 800;

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
