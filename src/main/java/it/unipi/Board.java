package it.unipi;

import it.unipi.components.*;
import it.unipi.utils.GameState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board extends JFrame {
    private final GameState gameState;

    private JPanel labelsPanel;
    private JPanel cardsPanel;
    private JPanel buttonPanel;

    private ControllerLabel controllerLabel;
    private CounterLabel counterLabel;

    private ShuffleButton shuffleButton;
    private LeaderboardButton leaderboardButton;
    private ExitButton exitButton;
    private ChangePairButton changePairsButton;

    private List<Card> cards;

    public Board(GameState gameState) {
        this.gameState = gameState;

        cards = Stream.generate(Card::new).limit(gameState.getN_CARDS()).collect(Collectors.toList());

        setVisible(true);
        setResizable(true);
        setSize(1360, 800);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setTitle("Matching Pairs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initBeans();
        setupListeners();
        setupUI();


        shuffleCards();
    }

    private void initBeans() {
        changePairsButton = new ChangePairButton(gameState.getN_PAIRS());
        leaderboardButton = new LeaderboardButton();
        shuffleButton = new ShuffleButton();
        exitButton = new ExitButton();

        controllerLabel = new ControllerLabel();
        counterLabel = new CounterLabel();
    }

    private void setupListeners() {
        changePairsButton.addPropertyChangeListener(new nPairChangeListener());
    }

    private class nPairChangeListener implements PropertyChangeListener, Serializable {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("nPairs".equals(evt.getPropertyName())) {
                int newPairs = (int) evt.getNewValue();
                gameState.setN_PAIRS(newPairs);
                System.out.println("New pairs: " + newPairs);
            }
        }
    }

    private void shuffleCards() {
        int N = gameState.getN_PAIRS();
        List<Integer> card_values = new Random().ints(1, 50)
                                                .distinct()
                                                .limit(N)
                                                .flatMap(i -> Arrays.stream(new int[]{i, i}))
                                                .boxed()
                                                .collect(Collectors.toList());
        Collections.shuffle(card_values, new Random());
        card_values.forEach(System.out::println);
    }

    private void setupUI() {
        add(createLabelsPanel(), BorderLayout.NORTH);
        add(createCardsPanel(), BorderLayout.CENTER);
        add(createControlButtons(), BorderLayout.SOUTH); // Add panel to the Board JFrame with BorderLayout.SOUTH
    }

    private JPanel createLabelsPanel() {
        labelsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        labelsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        labelsPanel.add(controllerLabel);
        labelsPanel.add(Box.createHorizontalStrut(20));
        labelsPanel.add(counterLabel);

        return labelsPanel;
    }

    private JPanel createCardsPanel() {
        int n_cards = gameState.getN_CARDS();
        cardsPanel = new JPanel(new GridLayout((int) Math.sqrt(n_cards),
                                               (int) Math.ceil((double) n_cards / (int) Math.sqrt(n_cards)),
                                          10,
                                          10
        ));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        for(Card card : cards) { cardsPanel.add(card); }
        return cardsPanel;
    }

    private JPanel createControlButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(changePairsButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(shuffleButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(Box.createHorizontalGlue()); // Add flexible space to push the next component to the right
        buttonPanel.add(exitButton);
        return buttonPanel;
    }
}



