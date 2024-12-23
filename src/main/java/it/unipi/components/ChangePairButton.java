package it.unipi.components;

import it.unipi.utils.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ChangePairButton class represents a button that allows the user to change the number of pairs in the game.
 * It fires a "pairsEvent" property change event when the number of pairs is changed.
 */
public class ChangePairButton extends javax.swing.JButton {
    private int nPairs;

    /**
     * Constructs a new ChangePairButton with the specified initial number of pairs.
     *
     * @param nPairs the initial number of pairs
     */
    public ChangePairButton(int nPairs) {
        super("Change Pairs");
        setFocusable(false);

        this.nPairs = nPairs;
        this.addActionListener(new buttonActionListener());
    }

    /**
     * Sets the number of pairs and fires a "pairsEvent" property change event.
     *
     * @param nPairs the new number of pairs
     */
    private void setPairs(int nPairs) {
        int oldNPairs = this.nPairs;
        this.nPairs = nPairs;
        this.firePropertyChange("pairsEvent", oldNPairs, nPairs);
    }

    /**
     * The buttonActionListener class handles the button click event to prompt the user for a new number of pairs.
     */
    private class buttonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int newPairs = Utils.getIntInput("Enter the new number of pairs:");
            if (newPairs == -1) return;
            setPairs(newPairs);
        }
    }
}
