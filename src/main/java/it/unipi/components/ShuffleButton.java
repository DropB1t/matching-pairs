package it.unipi.components;

import javax.swing.JButton;
import java.awt.event.ActionEvent;

/**
 * The ShuffleButton class represents a button that triggers a shuffle action when pressed.
 */
public class ShuffleButton extends JButton {
    /**
     * Constructs a new ShuffleButton with the label "Shuffle".
     */
    public ShuffleButton() {
        super("Shuffle");
        setFocusable(false);
    }

    /**
     * Fires an action event with the command "shuffleAction" when the button is pressed.
     *
     * @param event the original action event
     */
    @Override
    protected void fireActionPerformed(ActionEvent event) {
        super.fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "shuffleAction"));
    }
}
