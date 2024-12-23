package it.unipi.utils;

import javax.swing.JOptionPane;

public class Utils {
    /**
     * Prompts the user to input a number representing the number of pairs through a dialog box.
     * Repeats the input prompt until a valid integer is provided, or the user cancels the input.
     * If the user cancels, it returns -1.
     *
     * @return the number of pairs entered by the user, or -1 if the user cancels the input
     */
    public static int getIntInput(String message) {
        int numberOfPairs;
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(null, message, "Number of pairs", JOptionPane.QUESTION_MESSAGE);
                if (input == null) {
                    //JOptionPane.showMessageDialog(null, "Cancel button pressed, exiting...", "Exiting", JOptionPane.INFORMATION_MESSAGE);
                    return -1;
                }
                numberOfPairs = Integer.parseInt(input);
                if (numberOfPairs <= 0) {
                    JOptionPane.showMessageDialog(null, "Invalid number entered, try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                break; // Exit the loop if input is valid
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number entered, try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return numberOfPairs;
    }
}
