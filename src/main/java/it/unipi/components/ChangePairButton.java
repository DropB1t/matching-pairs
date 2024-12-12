package it.unipi.components;

import it.unipi.utils.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChangePairButton extends javax.swing.JButton {
    private final PropertyChangeSupport nPairsChangeSupport;
    int nPairs;

    public ChangePairButton() {
        super("Change Pairs");
        this.nPairs = 0;
        this.addActionListener(new buttonActionListener());
        nPairsChangeSupport = new PropertyChangeSupport(this);
    }

    private int getPairs() {
        return nPairs;
    }

    private void setPairs(int nPairs) {
        int oldNPairs = this.nPairs;
        this.nPairs = nPairs;
        nPairsChangeSupport.firePropertyChange("nPairs", nPairs, oldNPairs);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        nPairsChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        nPairsChangeSupport.removePropertyChangeListener(listener);
    }

    private class buttonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int newPairs = Utils.getIntInput("Enter the new number of pairs:");
            setPairs(newPairs);
        }
    }
}
