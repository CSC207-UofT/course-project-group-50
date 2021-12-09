package interfaceadapters;

import datatransferobj.PlayerDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds the player stats section of the GUI from the information stored in a PlayerDTO object.
 */
public class PlayerPanel extends JPanel {
    protected static final int STATS_WIDTH = 600;
    protected static final int STATS_HEIGHT = 100;

    protected final int USERNAME = 0;
    protected final int CASH = 1;
    protected final int NET_WORTH = 2;

    private final Map<Integer, List<JComponent>> playerToStats;


    public PlayerPanel() {
        setLayout(new FlowLayout());
        setBackground(Color.white);
        setPreferredSize(new Dimension(STATS_WIDTH, STATS_HEIGHT));

        this.playerToStats = new HashMap<>();

        buildTemplate();
    }

    /**
     * Sets up an empty FlowLayout for the board, and creates references to empty JPanel elements within the layout that
     * can be used to update the stats panel later.
     */
    private void buildTemplate() {
        JPanel[] playerPanelHolder = new JPanel[4];

        for (int i = 0; i < 4; i++) {
            JPanel statBox = new JPanel();
            statBox.setLayout(new BorderLayout());
            statBox.setBackground(Color.WHITE);

            List<JComponent> statsComponents = new ArrayList<>();

            String[] textPosition = new String[]{"North", "Center", "South"};
            for (int j = 0; j < 3; j++) {
                JTextField text = new JTextField("");
                text.setEditable(false);
                text.setBorder(javax.swing.BorderFactory.createEmptyBorder());

                statBox.add(text, textPosition[j]);
                statsComponents.add(text);
            }

            this.playerToStats.put(i, statsComponents);

            playerPanelHolder[i] = statBox;
            add(statBox);
        }
    }

    /**
     * Refreshes the stats panel of the GUI using information from the PlayerDTO.
     *
     * @param numOfPlayers   the number of players in this game
     * @param playerData     a DTO containing raw information on player name, number, cash, net worth, etc
     * @param playerToColour maps each player's number to its associated Colour
     */
    public void update(int numOfPlayers, Map<Integer, PlayerDTO> playerData, Map<Integer, Color> playerToColour) {
        for (int i = 1; i <= numOfPlayers; i++) {
            PlayerDTO value = playerData.get(i);

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
