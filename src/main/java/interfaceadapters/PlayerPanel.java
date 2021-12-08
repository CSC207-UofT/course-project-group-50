package interfaceadapters;

import datatransfer.PlayerData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerPanel extends JPanel {
    protected static final int STATS_WIDTH = 700;
    protected static final int STATS_HEIGHT = 100;

    protected final int USERNAME = 0;
    protected final int CASH = 1;
    protected final int NET_WORTH = 2;

    private final Map<Integer, List<JComponent>> playerToStats;
    private JPanel[] playerPanelHolder;


    public PlayerPanel(){
        setLayout(new FlowLayout());
        setBackground(Color.white);
        setPreferredSize(new Dimension(STATS_WIDTH, STATS_HEIGHT));

        this.playerToStats = new HashMap<>();

        buildTemplate();
    }

    private void buildTemplate(){
        this.playerPanelHolder = new JPanel[4];

        for (int i = 0; i < 4; i++) {
            JPanel statBox = new JPanel();
            statBox.setLayout(new BorderLayout());
            statBox.setBackground(Color.WHITE);

            List<JComponent> statsComponents = new ArrayList<>();

            String[] textPosition = new String[]{"North", "Center", "South"};
            for(int j = 0; j < 3; j++){
                JTextField text = new JTextField("");
                text.setEditable(false);
                text.setBorder(javax.swing.BorderFactory.createEmptyBorder());

                statBox.add(text, textPosition[j]);
                statsComponents.add(text);
            }

            this.playerToStats.put(i, statsComponents);

            this.playerPanelHolder[i] = statBox;
            add(statBox);
        }
    }

    public void update(int numOfPlayers, Map<Integer, PlayerData> playerData, Map<Integer, Color> playerToColour) {
        for (int i = 1; i <= numOfPlayers; i++) {
            PlayerData value = playerData.get(i);

            List<JComponent> statsComponents = this.playerToStats.get(i - 1);

            JTextField username = (JTextField) statsComponents.get(USERNAME);
            username.setText(value.username);
            username.setForeground(playerToColour.get(i));

            JTextField cash = (JTextField) statsComponents.get(CASH);
            cash.setText("Cash: $" + value.cash);

            JTextField netWorth = (JTextField) statsComponents.get(NET_WORTH);
            netWorth.setText("Net Worth: $" + value.netWorth);
        }
    }
}
