package it.unipi.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.JLabel;

/**
 * The ChallengeLabel class represents a label that displays the current challenge based on the minimum number of flips made by the winner.
 * It implements PropertyChangeListener to update its text based on game events.
 */
public class ChallengeLabel extends JLabel implements PropertyChangeListener {
    private int currentNPairs;
    private Map<Integer, Integer> bestScores;

    /**
     * Constructs a new ChallengeLabel with the specified number of pairs.
     *
     * @param currentNPairs the initial number of pairs
     */
    public ChallengeLabel(int currentNPairs) {
        super("ChallengeLabel");
        this.currentNPairs = currentNPairs;
        this.bestScores = new java.util.HashMap<>();
        this.setText("Best score for " + currentNPairs + " pairs board: N/A");
    }

    /**
     * Updates the text of the label based on property change event called roundEndEvent that transmit
     * the number of flips made by the winner in the last round.
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "roundEndEvent":
                int nFlips = (int) evt.getNewValue();
                if (bestScores.containsKey(currentNPairs)) {
                    if (nFlips < bestScores.get(currentNPairs)) {
                        bestScores.put(currentNPairs, nFlips);
                        this.setText("Best score for " + currentNPairs + " pairs board: " + nFlips + " flips");
                    }
                } else {
                    bestScores.put(currentNPairs, nFlips);
                    this.setText("Best score for " + currentNPairs + " pairs board: " + nFlips + " flips");
                }
                break;
            case "pairsEvent":
                currentNPairs = (int) evt.getNewValue();
                if(bestScores.containsKey(currentNPairs))
                    this.setText("Best score for " + currentNPairs + " pairs board: " + bestScores.get(currentNPairs) + " flips");
                else
                    this.setText("Best score for " + currentNPairs + " pairs board: N/A");
                break;
            default:
                break;
        }
    }

}
