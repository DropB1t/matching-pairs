package it.unipi.utils;

import javax.swing.*;
import java.awt.*;

public class Utils {
    public static final java.awt.Insets DEFAULT_INSETS = new java.awt.Insets(10, 10, 10, 10);

    public static GridBagConstraints createDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = DEFAULT_INSETS; // Use the constant for layout insets
        return gbc;
    }

    // Generic helper to update GridBagConstraints with new values
    public static GridBagConstraints updateGbc(GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight,
                                         int fill, double weightx, double weighty) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        return gbc;
    }

    public static GridBagConstraints updateGbc(GridBagConstraints gbc, int gridx, int gridy,
                                         int gridwidth, int gridheight, int fill,
                                         double weightx, double weighty, int anchor) {
        updateGbc(gbc, gridx, gridy, gridwidth, gridheight, fill, weightx, weighty);
        gbc.anchor = anchor;
        return gbc;
    }

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
