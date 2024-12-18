package it.unipi.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Card extends JButton {

    public enum CardState {
        FACE_DOWN,
        FACE_UP,
        EXCLUDED
    }

    private int value;
    private CardState state;

    public Card(int value) {
        super("Cards");
        setPreferredSize(new java.awt.Dimension(100, 120)); // Set the size of a card
        setMaximumSize(new java.awt.Dimension(100, 120));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(new Color(240, 255, 255));
        setFont(new Font("Serif", Font.BOLD, 40));
        putClientProperty("html.disable", Boolean.TRUE);
        setFocusable(false);

        this.value = value;
        this.state = CardState.FACE_DOWN;
        this.setText("");
        registerMouseHandler();
    }

    public Card() {
        this(0);
    }

    public CardState getState(){
        return this.state;
    }

    private void setState(CardState cardState) {
        this.state = cardState;
        if(this.state == CardState.FACE_UP){
            this.setText(String.valueOf(this.value));
        }else{
            this.setText("");
        }
    }

    // Instantiate a mouse click listener that trigger the show value method
    private void registerMouseHandler(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                Card.this.setState(CardState.FACE_UP);
            }
            @Override
            public void mouseEntered(MouseEvent evt) {
                if(Card.this.state == CardState.FACE_DOWN){
                    setBackground(new Color(173, 216, 230));
                }
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                if(Card.this.state == CardState.FACE_DOWN){
                    setBackground(new Color(240, 255, 255));
                }
            }
        });
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
