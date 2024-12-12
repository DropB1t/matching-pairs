package it.unipi;

import javax.swing.*;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        // Use SwingUtilities.invokeLater to safely execute Swing-related code
        SwingUtilities.invokeLater(() -> {
            String name = JOptionPane.showInputDialog(null, "Enter your username:", "Input username", JOptionPane.QUESTION_MESSAGE);
            if (name == null) {
                JOptionPane.showMessageDialog(null, "No username entered, exiting...", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }

            int N_PAIRS;
            while (true) {
                try {
                    String input = JOptionPane.showInputDialog(null, "Enter the number of pairs:", "Input number", JOptionPane.QUESTION_MESSAGE);
                    if (input == null) {
                        JOptionPane.showMessageDialog(null, "Cancel button pressed, exiting...", "Exiting", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }
                    N_PAIRS = Integer.parseInt(input);
                    break; // Exit the loop if input is valid
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid number entered, try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            System.out.println("Name entered: " + name);
            System.out.println("Pairs: " + N_PAIRS + " Cards: " + N_PAIRS * 2);

            // Instantiate the Board class
            Board board = new Board();
            // Make the window visible
            board.setVisible(true);
            // Position board JFrame at the center of monitor
            board.setLocationRelativeTo(null);

        });
    }

}