package interfaceadapters;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerPanel extends JPanel {
    private JPanel[] playerPanelHolder;
    private Map<Integer, Color> playerToColour;
    private final int numOfPlayers;

    public PlayerPanel(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        buildPlayerStatBoxes();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlayerStats(g);
    }

    public void buildPlayerStatBoxes() {
        for (int i = 0; i <= this.numOfPlayers; i++) {
            JPanel statBox = new JPanel();
            statBox.setLayout(new BorderLayout());
            this.add(statBox);
        }
    }

    public void drawPlayerStats(Graphics g) {
//        for (int i = 0; i < this.numOfPlayers; i++) {
//            this.getContentPane().add(new JTextField("name"), "North");
//            this.playerStatBoxes.get(i).add(new JTextField("blah"), "Center");
//        }
    }

    public void updateStats() {
        revalidate();
        repaint();
    }
}
