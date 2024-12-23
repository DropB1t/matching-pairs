package it.unipi.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;

import it.unipi.utils.Player;

/**
 * The CounterLabel class represents a label that displays the number of flips performed by the current player.
 * It implements ActionListener and PropertyChangeListener to update its text based on game events.
 */
public class CounterLabel extends JLabel implements ActionListener, PropertyChangeListener {
    private Player[] players;
    private int currentPlayer;

    /**
     * Constructs a new CounterLabel with the specified players.
     *
     * @param players the array of players participating in the game
     */
    public CounterLabel(Player[] players) {
        super("Counter Label");
        this.players = players;
        this.setText("Flips: " + players[currentPlayer].getFlips());
    }

    /**
     * Handles action events to update the flip count.
     * The following actions trigger an update:
     * - "flipAction": Increments the flip count for the current player.
     * - "shuffleAction": Resets the flip count for all players.
     * - "roundEndAction": Resets the flip count for all players.
     */
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

    /**
     * Handles property change events to update the flip count.
     * The following property changes trigger an update:
     * - "pairsEvent": Resets the flip count for all players.
     * - "nextPlayer": Updates the current player and displays their flip count.
     */
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

    /**
     * Resets the flip count for all players and updates the label to display the current player's flip count.
     */
    private void resetCounter() {
        for (Player player : players) {
            player.setFlips(0);
        }
        this.setText("Flips: " + players[currentPlayer].getFlips());
    }

}
