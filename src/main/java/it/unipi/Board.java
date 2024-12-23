package it.unipi;

import it.unipi.components.*;
import it.unipi.utils.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Board class represents the main game board for the Matching Pairs game.
 * It extends JFrame and implements PropertyChangeListener and ActionListener to handle game events and actions.
 */
public class Board extends JFrame implements PropertyChangeListener, ActionListener {
    private final GameState gameState;

    private JPanel labelsPanel;
    private JPanel cardsPanel;
    private JPanel buttonPanel;

    private ControllerLabel controllerLabel;
    private CounterLabel counterLabel;

    private ShuffleButton shuffleButton;
    private ChallengeLabel challengeLabel;
    private ExitButton exitButton;
    private ChangePairButton changePairsButton;

    private List<Card> cards;
    private List<Integer> cardValues;

    /**
     * Constructs a new Board with the specified game state.
     *
     * @param gameState the current game state
     */
    public Board(GameState gameState) {
        this.gameState = gameState;

        setVisible(true);
        setResizable(true);
        setSize(1360, 800);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setTitle("Matching Pairs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        initBeans();
        setupButtonListeners();
        setupUI();

        newGame();
    }

    /**
     * Initializes the components (beans) used in the game.
     */
    private void initBeans() {
        changePairsButton = new ChangePairButton(gameState.getN_PAIRS());
        challengeLabel = new ChallengeLabel(gameState.getN_PAIRS());
        shuffleButton = new ShuffleButton();
        exitButton = new ExitButton();

        controllerLabel = new ControllerLabel(gameState.getPlayers(), gameState.getN_PAIRS());
        counterLabel = new CounterLabel(gameState.getPlayers());
    }

    /**
     * Sets up listeners for the buttons and other components.
     */
    private void setupButtonListeners() {
        changePairsButton.addPropertyChangeListener("pairsEvent", this);
        changePairsButton.addPropertyChangeListener("pairsEvent", controllerLabel);
        changePairsButton.addPropertyChangeListener("pairsEvent", counterLabel);
        changePairsButton.addPropertyChangeListener("pairsEvent", challengeLabel);
        controllerLabel.addPropertyChangeListener("nextPlayer", counterLabel);
        controllerLabel.addPropertyChangeListener("roundEndEvent", challengeLabel);
        shuffleButton.addActionListener(this);
        shuffleButton.addActionListener(counterLabel);
        shuffleButton.addActionListener(controllerLabel);
        controllerLabel.addRoundEndActionListener(this);
        controllerLabel.addRoundEndActionListener(counterLabel);
    }

    /**
     * Handles property change events to update the game state and UI.
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("pairsEvent".equals(evt.getPropertyName())) {
            int newPairs = (int) evt.getNewValue();
            gameState.setN_PAIRS(newPairs);
            newGame();
        }
    }

    /**
     * Handles action events to shuffle cards or start a new round.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("shuffleAction".equals(e.getActionCommand()) || "roundEndAction".equals(e.getActionCommand())) {
            shuffleCards();
            return;
        }
    }

    /**
     * Starts a new game by initializing the cards and updating the UI.
     */
    private void newGame() {
        if (cards != null) {
            for (Card card : cards) {
                cardsPanel.remove(card);
                this.removePropertyChangeListener("shuffleEvent", card);
                controllerLabel.removePropertyChangeListener("pairMatched", card);
                card.removeActionListener(counterLabel);
                card.removePropertyChangeListener(controllerLabel);
            }
        }
        cards = Stream.generate(Card::new).limit(gameState.getN_CARDS()).collect(Collectors.toList());
        cardValues = Stream.generate(() -> 0).limit(gameState.getN_CARDS()).collect(Collectors.toList());
        controllerLabel.setTotalPairs(gameState.getN_PAIRS());

        int n_cards = gameState.getN_CARDS();
        GridLayout layout = (GridLayout) cardsPanel.getLayout();
        layout.setRows((int) Math.sqrt(n_cards));
        layout.setColumns((int) Math.ceil((double) n_cards / (int) Math.sqrt(n_cards)));
        layout.setHgap(10);
        layout.setVgap(10);

        for (Card card : cards) {
            cardsPanel.add(card);
            this.addPropertyChangeListener("shuffleEvent", card);
            controllerLabel.addPropertyChangeListener("pairMatched", card);
            card.addPropertyChangeListener(controllerLabel); 
            card.addVetoableChangeListener(controllerLabel);
            card.addActionListener(counterLabel);
        }
        shuffleCards();

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    /**
     * Shuffles the cards and updates their values.
     */
    private void shuffleCards() {
        List<Integer> newCardValues = new Random().ints(1, gameState.getN_CARDS() * 2)
                            .distinct()
                            .limit(gameState.getN_PAIRS())
                            .flatMap(i -> Arrays.stream(new int[]{i, i}))
                            .boxed()
                            .collect(Collectors.toList());
        Collections.shuffle(newCardValues, new Random());

        PropertyChangeListener[] shuffleListeners = this.getPropertyChangeListeners("shuffleEvent");
        if (shuffleListeners.length != 0) {
            for (int i = 0; i < gameState.getN_CARDS(); i++) {
                shuffleListeners[i].propertyChange(new PropertyChangeEvent(this, "shuffleEvent", cardValues.get(i), newCardValues.get(i)));
            }
        }

        cardValues = newCardValues;
    }

    /**
     * Sets up the UI components and layout.
     */
    private void setupUI() {
        add(createLabelsPanel(), BorderLayout.NORTH);
        add(createCardsPanel(), BorderLayout.CENTER);
        add(createControlButtons(), BorderLayout.SOUTH);
    }

    /**
     * Creates the panel for displaying labels.
     *
     * @return the labels panel
     */
    private JPanel createLabelsPanel() {
        labelsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        labelsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        labelsPanel.add(controllerLabel);
        labelsPanel.add(Box.createHorizontalStrut(20));
        labelsPanel.add(counterLabel);
        labelsPanel.add(Box.createHorizontalStrut(20));
        labelsPanel.add(challengeLabel);

        return labelsPanel;
    }

    /**
     * Creates the panel for displaying cards.
     *
     * @return the cards panel
     */
    private JPanel createCardsPanel() {
        cardsPanel = new JPanel(new GridLayout());
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return cardsPanel;
    }

    /**
     * Creates the panel for control buttons.
     *
     * @return the control buttons panel
     */
    private JPanel createControlButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(changePairsButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(shuffleButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(exitButton);
        return buttonPanel;
    }
}



