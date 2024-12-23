package it.unipi.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import it.unipi.utils.CardState;
import it.unipi.utils.Player;
import java.util.List;
import java.util.ArrayList;

/**
 * The ControllerLabel class represents a label that displays the current player's name and the number of pairs they have found.
 * It implements ActionListener, PropertyChangeListener, and VetoableChangeListener to update displayed text based on game events.
 * It also manages the game logic for matching pairs and switching players.
 */
public class ControllerLabel extends JLabel implements ActionListener, PropertyChangeListener, VetoableChangeListener {
    private Player[] players;
    private int currentPlayer;
    private int totalPairs;
    private int v1, v2;
    private boolean activeMatching;
    private Timer matchTimer;
    private Timer mismatchTimer;
    private List<ActionListener> actionListeners = new ArrayList<>();

    /**
     * Constructs a new ControllerLabel with the specified players and total pairs.
     *
     * @param players the array of players participating in the game
     * @param totalPairs the total number of pairs in the game
     */
    public ControllerLabel(Player[] players, int totalPairs) {
        super("Controller Label");
        this.players = players;
        this.currentPlayer = 0;
        this.totalPairs = totalPairs;
        this.setText("Current Player: " + players[currentPlayer].getName() + " | Pairs: " + players[currentPlayer].getPairs() + "/" + totalPairs);
        v1 = 0;
        v2 = 0;
        activeMatching = false;
        matchTimer = new Timer(300, this);
        mismatchTimer = new Timer(500, this);
        matchTimer.setActionCommand("match");
        mismatchTimer.setActionCommand("mismatch");
    }

    /**
     * Sets the players participating in the game.
     *
     * @param players the new array of players
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Sets the total number of pairs in the game.
     *
     * @param totalPairs the new total number of pairs
     */
    public void setTotalPairs(int totalPairs) {
        this.totalPairs = totalPairs;
    }

    /**
     * Resets the number of pairs found by each player and updates the label to display the current player's pairs.
     */
    private void resetPairs() {
        for (Player player : players) {
            player.setPairs(0);
        }
        currentPlayer = 0;
        this.setText("Current Player: " + players[currentPlayer].getName() + " | Pairs: " + players[currentPlayer].getPairs() + "/" + totalPairs);
    }

    /**
     * Handles property change events to update the label and manage game logic.
     * The following property changes trigger an update:
     * - "pairsEvent": Resets the pairs for all players.
     * - "cardFlipped": Checks if two cards have been flipped and starts the match or mismatch timer.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("pairsEvent".equals(evt.getPropertyName())) {
            this.totalPairs = (int) evt.getNewValue();
            resetPairs();
        } else if ("cardFlipped".equals(evt.getPropertyName())) {
            if (v1 == 0) {
                v1 = (int) evt.getNewValue();
            } else {
                v2 = (int) evt.getNewValue();
                activeMatching = true;
                if (v1 == v2) {
                    matchTimer.start();
                } else {
                    mismatchTimer.start();
                }
                v1 = 0;
                v2 = 0;
            }
        }
    }

    /**
     * Handles action events to update the label and manage game logic.
     * The following actions trigger an update:
     * - "match": Updates the pairs for the current player and checks if the game is over.
     * - "mismatch": Switches to the next player.
     * - "shuffleAction": Resets the pairs for all players.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("match".equals(e.getActionCommand())) {
            matchTimer.stop();
            players[currentPlayer].setPairs(players[currentPlayer].getPairs() + 1);
            this.setText("Current Player: " + players[currentPlayer].getName() + " | Pairs: " + players[currentPlayer].getPairs() + "/" + totalPairs);
            this.firePropertyChange("pairMatched", false, true);
            activeMatching = false;

            int sumPairs = 0;
            int maxPairs = 0;
            int winnerFlips = 0;
            String winner = "";
            for (Player player : players) {
                sumPairs += player.getPairs();
                if (player.getPairs() > maxPairs) {
                    winner = player.getName();
                    maxPairs = player.getPairs();
                    winnerFlips = player.getFlips();
                }
                if (player.getPairs() == maxPairs && player.getFlips() < winnerFlips) {
                    winner = player.getName();
                    winnerFlips = player.getFlips();
                }
            }

            if (sumPairs == this.totalPairs) {
                JOptionPane.showMessageDialog(null, "Game Over! " + winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                this.firePropertyChange("roundEndEvent", 0, winnerFlips);
                fireRoundEndAction();
            }

        } else if ("mismatch".equals(e.getActionCommand())) {
            mismatchTimer.stop();
            currentPlayer = (currentPlayer + 1) % players.length;
            this.setText("Current Player: " + players[currentPlayer].getName() + " | Pairs: " + players[currentPlayer].getPairs() + "/" + totalPairs);
            this.firePropertyChange("nextPlayer", -1, currentPlayer);
            this.firePropertyChange("pairMatched", true, false);
            activeMatching = false;
        } else if ("shuffleAction".equals(e.getActionCommand())) {
            resetPairs();
        }
    }

    /**
     * Fires a "roundEndAction" event to notify listeners that the round has ended.
     */
    private void fireRoundEndAction() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "roundEndAction");
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(event);
        }
        resetPairs();
    }

    /**
     * Adds an ActionListener to listen for "roundEndAction" events.
     *
     * @param listener the ActionListener to add
     */
    public void addRoundEndActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    /**
     * Removes an ActionListener from listening for "roundEndAction" events.
     *
     * @param listener the ActionListener to remove
     */
    public void removeRoundEndActionListener(ActionListener listener) {
        actionListeners.remove(listener);
    }

    /**
     * Handles vetoable change events to manage the state of the card.
     * The following conditions trigger a veto:
     * - The card is already face up or excluded.
     * - The card is being flipped while another match is being processed.
     *
     * @param evt the vetoable change event
     * @throws PropertyVetoException if the change is vetoed
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if ("stateChange".equals(evt.getPropertyName())) {
            CardState oldState = (CardState) evt.getOldValue();
            if (activeMatching && oldState == CardState.FACE_UP) return;
            if (activeMatching || oldState == CardState.FACE_UP || oldState == CardState.EXCLUDED) {
                throw new PropertyVetoException("Card already flipped", evt);
            }
        }
    }
}
