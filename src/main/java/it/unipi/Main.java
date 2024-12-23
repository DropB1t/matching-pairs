package it.unipi;

import it.unipi.utils.Player;
import it.unipi.utils.Utils;
import it.unipi.utils.GameState;

import javax.swing.*;
import java.util.Locale;

public class Main {
    private static int N_PAIRS = 4;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        // SwingUtilities.invokeLater for a safely execution of Swing-related code
        SwingUtilities.invokeLater(() -> {
            
            int N_PLAYERS = Utils.getIntInput("Enter the number of players:");
            if (N_PLAYERS == -1) {
                JOptionPane.showMessageDialog(null, "Cancel button pressed, exiting...", "Exiting", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            Player[] players = new Player[N_PLAYERS];
            for (int i = 0; i < N_PLAYERS; i++) {
                String name;
                while (true) {
                    name = JOptionPane.showInputDialog(null, "Enter player " + (i + 1) + " username:", "Input username", JOptionPane.QUESTION_MESSAGE);
                    if (name != null && !name.isBlank()) {
                        break;
                    }
                    JOptionPane.showMessageDialog(null, "Please enter a valid username...", "Error", JOptionPane.ERROR_MESSAGE);
                }
                Player player = new Player(name);
                players[i] = player;
            }

            N_PAIRS = Utils.getIntInput("Enter the number of pairs:");
            if (N_PAIRS == -1) {
                JOptionPane.showMessageDialog(null, "Cancel button pressed, exiting...", "Exiting", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            
            // Test with 2 players
            //Player[] players = {new Player("Yuriy"), new Player("John")};
            
            // Instantiate the Board class
            GameState gameState = new GameState(players, N_PAIRS);
            new Board(gameState);
        });
    }

}