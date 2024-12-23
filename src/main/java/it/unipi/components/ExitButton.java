package it.unipi.components;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * The ExitButton class represents a button that exits the application when pressed.
 * It displays a confirmation dialog before exiting.
 */
public class ExitButton extends JButton {
    /**
     * Constructs a new ExitButton with the label "Exit".
     */
    public ExitButton() {
        super("Exit");
        setFocusable(false);
        addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}
