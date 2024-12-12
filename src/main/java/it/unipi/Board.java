package it.unipi;

import it.unipi.components.ChangePairButton;
import it.unipi.components.ExitButton;
import it.unipi.utils.GameState;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

public class Board extends JFrame {
    private final GameState gameState;

    private JPanel contentPane;
    private JButton shuffleButton;
    private JButton leaderboardButton;
    private ExitButton exitButton;
    private ChangePairButton changePairsButton;

    private JPanel cardsPanel;

    private JPanel labelsPanel;
    private JLabel controllerLabel;
    private JLabel counterLabel;

    public Board(GameState gameState) {
        this.gameState = gameState;

        setVisible(true);
        setResizable(true);
        setSize(1360, 800);
        setLocationRelativeTo(null);
        setTitle("Matching Pairs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set contentPane as the main panel of the JFrame
        setContentPane(contentPane);
        setupBeans();
    }

    private void setupBeans() {
        changePairsButton = new ChangePairButton();
        changePairsButton.addPropertyChangeListener(new nPairChangeListener());
    }

    private class nPairChangeListener implements PropertyChangeListener, Serializable {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("N_PAIRS".equals(evt.getPropertyName())) {
                int newPairs = (int) evt.getNewValue();
                gameState.setN_PAIRS(newPairs);
            }
        }
    }
}



