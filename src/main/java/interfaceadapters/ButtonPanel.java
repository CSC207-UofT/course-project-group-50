package interfaceadapters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ButtonPanel extends JPanel {
    // This class will handle all buttons to which will communicate with a presenter

    // TODO Add more buttons if needed
    private JButton buyButton;
    private JButton rollDice;
    private JButton endTurn;

    private GameController gameController;

    public ButtonPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        setOpaque(false);
        buyButton = new JButton("Buy");
        rollDice = new JButton("Roll Dice");
        endTurn = new JButton("End Turn");

        // Adding buttons to the panel
        panel.add(buyButton);
        panel.add(endTurn);
        panel.add(rollDice);

        this.setMaximumSize(panel.getMaximumSize());

        this.add(panel);

        // TODO Add presenter/controller to communicate with the users
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("buy");
                buyButton.setEnabled(false);
            }
        });

        rollDice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("rolldice");
                rollDice.setEnabled(false);
            }
        });

        endTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("player/endturn");
                endTurn.setEnabled(false);
            }
        });
    }
}
