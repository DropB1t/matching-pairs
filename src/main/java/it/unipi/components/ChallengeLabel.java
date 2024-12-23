package it.unipi.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.JLabel;

public class ChallengeLabel extends JLabel implements PropertyChangeListener {
    private int currentNPairs;
    private Map<Integer, Integer> bestScores;

    public ChallengeLabel(int currentNPairs) {
        super("ChallengeLabel");
        this.currentNPairs = currentNPairs;
        this.bestScores = new java.util.HashMap<>();
        this.setText("Best score for " + currentNPairs + " pairs board: N/A");
    }

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
