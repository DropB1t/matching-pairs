package it.unipi.components;

import javax.swing.*;

public class ExitButton extends javax.swing.JButton {
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
