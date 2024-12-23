package it.unipi.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;

import it.unipi.utils.Player;

public class CounterLabel extends JLabel implements ActionListener, PropertyChangeListener {
    private Player[] players;
    private int currentPlayer;

    public CounterLabel(Player[] players) {
        super("Counter Label");
        this.players = players;
        this.setText("Flips: " + players[currentPlayer].getFlips());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "flipAction":
                players[currentPlayer].setFlips(players[currentPlayer].getFlips() + 1);
                this.setText("Flips: " + players[currentPlayer].getFlips());
                break;
            case "shuffleAction":
            case "roundEndAction":
                resetCounter();
                break;
            default:
                break;
        }
        return;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "pairsEvent":
                resetCounter();
                break;
            case "nextPlayer":
                currentPlayer = (currentPlayer + 1) % players.length;
                this.setText("Flips: " + players[currentPlayer].getFlips());
                break;
            default:
                break;
        }
    }

    private void resetCounter() {
        for (Player player : players) {
            player.setFlips(0);
        }
        this.setText("Flips: " + players[currentPlayer].getFlips());
    }

}
