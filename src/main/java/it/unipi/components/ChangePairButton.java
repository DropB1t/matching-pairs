package it.unipi.components;

import it.unipi.utils.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePairButton extends javax.swing.JButton {
    int nPairs;

    public ChangePairButton(int nPairs) {
        super("Change Pairs");
        setFocusable(false);

        this.nPairs = nPairs;
        this.addActionListener(new buttonActionListener());
    }

    private int getPairs() {
        return nPairs;
    }

    private void setPairs(int nPairs) {
        int oldNPairs = this.nPairs;
        this.nPairs = nPairs;
        this.firePropertyChange("nPairs", oldNPairs, nPairs);
    }

    private class buttonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int newPairs = Utils.getIntInput("Enter the new number of pairs:");
            setPairs(newPairs);
        }
    }
}
