package it.unipi.components;

import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class ShuffleButton extends JButton {
    public ShuffleButton() {
        super("Shuffle");
        setFocusable(false);
    }

    @Override
    protected void fireActionPerformed(ActionEvent event) {
        super.fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "shuffleAction"));
    }
}
