package interfaceadapters;
import datatransfer.PlayerData;
import datatransfer.TileData;
import usecases.GameOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.awt.geom.Point2D;
import java.util.Map;

public class Presenter extends JFrame implements Serializable, GameOutputBoundary { //, ActionListener {
    protected DisplayPanel displayPanel;
    protected static final int FRAME_WIDTH = 700;
    protected static final int FRAME_HEIGHT = 800;

    public Presenter() throws IOException {
        setTitle("Simplified Monopoly");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.displayPanel = new DisplayPanel();
        getContentPane().add(displayPanel);

        setVisible(true);
        setResizable(false);

//        JButton quit = new JButton("Quit Game");
//        getContentPane().add(quit);
//        quit.addActionListener(this);
    }

    @Override
    public void update(Map<String, TileData> boardData, Map<Integer, PlayerData> playerData) {
        getContentPane().remove(displayPanel);
        this.displayPanel.update(boardData, playerData);
        getContentPane().add(displayPanel);
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
