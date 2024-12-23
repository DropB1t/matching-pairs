package it.unipi.components;

import javax.swing.*;

import it.unipi.utils.CardState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;

/**
 * The Card class represents a card in the matching pairs game.
 * It extends JButton and implements PropertyChangeListener and MouseListener.
 */
public class Card extends JButton implements PropertyChangeListener, MouseListener {

    private int value;
    private CardState state;

    /**
     * Constructs a new Card with the specified value.
     *
     * @param value the value of the card
     */
    public Card(int value) {
        super("Cards");
        setPreferredSize(new Dimension(100, 120)); // Set the size of a card
        setMaximumSize(new Dimension(100, 120));
        setBorder(BorderFactory.createLineBorder(Color.black));
        putClientProperty("html.disable", Boolean.TRUE);
        setFocusable(false);
        setFont(new Font("Monospaced", Font.BOLD, 24));
        setBackground(new Color(200, 255, 200));
        this.state = CardState.FACE_DOWN;
        this.value = value;
        this.setText("");
        this.addMouseListener(this);
    }

    /**
     * Constructs a new Card with a default value of 0.
     */
    public Card() {
        this(0);
    }

    /**
     * Returns the current state of the card.
     *
     * @return the current state of the card
     */
    public CardState getState(){
        return this.state;
    }

    /**
     * Sets the state of the card and updates its appearance accordingly.
     * This method fires a vetoable change event before updating the state.
     * If the change is vetoed, the state will not be updated.
     * The state is a bound and constrained property.
     *
     * @param cardState the new state of the card
     */
    public void setState(CardState cardState) {
        try {
            this.fireVetoableChange("stateChange", this.state, cardState);
            this.state = cardState;
            switch (cardState) {
                case FACE_UP:
                    setFaceUp();
                    break;
                case FACE_DOWN:
                    setFaceDown();
                    break;
                case EXCLUDED:
                    setExcluded();
                    break;
                default:
                    break;
            }
        } catch (PropertyVetoException e) {
            return;
        }
    }

    private void setFaceUp() {
        this.setText(String.valueOf(this.value));
        this.fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "flipAction"));
        this.firePropertyChange("cardFlipped", -1, this.value); // Notify card flip
    }

    private void setFaceDown() {
        this.setText("");
        setBackground(new Color(200, 255, 200));
    }

    private void setExcluded() {
        this.setText("");
        setBackground(new Color(255, 102, 102));
    }

    /**
     * Returns the value of the card.
     *
     * @return the value of the card
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the card.
     *
     * @param value the new value of the card
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("shuffleEvent".equals(evt.getPropertyName())) {
            int newValue = (int) evt.getNewValue();
            this.state = CardState.FACE_DOWN;
            setFaceDown();
            this.setValue(newValue);
            return;
        } else if ("pairMatched".equals(evt.getPropertyName())) {
            if (this.state == CardState.FACE_UP) {
                boolean pairMatched = (boolean) evt.getNewValue();
                if (pairMatched) {
                    this.setState(CardState.EXCLUDED);
                } else {
                    this.setState(CardState.FACE_DOWN);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.setState(CardState.FACE_UP);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(this.state == CardState.FACE_DOWN){
            setBackground(new Color(144, 238, 144));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(this.state == CardState.FACE_DOWN){
            setBackground(new Color(200, 255, 200));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        return;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        return;
    }
}
