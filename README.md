# Matching Pairs Game

## Overview

The Matching Pairs is a Java application (based on JDK 17) that allows the [Concentration](https://en.wikipedia.org/wiki/Concentration_(card_game)) card game. The game is built using Java Swing components for the GUI and follows an event-driven architecture (through the use of Observer pattern design) to manage game state and interactions. This document provides an overview of the project, including its components, data structures, events, and listeners.

The application was built by extensively using and extending the Java Swing beans component system. All components communicate with each other by using only the events which are listed below.

## Classes & components

- **Main.java**: The entry point of the application. It initializes the game by setting up the players and the number of pairs, and then creates an instance of the `Board` class.
- **Board.java**: Represents the main game board. It extends `JFrame` and manages the layout and interactions of various components like cards, labels, and buttons.
- **Card.java**: Represents a card in the game. It extends `JButton` and implements `PropertyChangeListener` and `MouseListener` to handle card state changes and user interactions.

### Labels

- **ChallengeLabel.java**: An extension of `JLabel` that displays the best score for the current number of pairs.
- **CounterLabel.java**: An extension of `JLabel` that displays the number of flips made by the current player.
- **ControllerLabel.java**: An extension of `JLabel` that displays the current player's name and the number of pairs they have found. It also manages game logic for matching pairs and switching players.

### Buttons

- **ChangePairButton.java**: An extension of `JButton` that allows the user to change the number of pairs in the game. It fires a **pairsEvent** property change event when the number of pairs is changed.
- **ShuffleButton.java**: An extension of `JButton` that shuffles the cards on the board.
- **ExitButton.java**: An extension of `JButton` that exits the game.

## Data Structures & Property Fields

### GameState
Represents the state of the game, including the players and the number of pairs and cards. It provides methods to get and set these values.
- **players**: An array of `Player` objects representing the players in the game.
- **N_PAIRS**: An integer representing the number of pairs in the game.
- **N_CARDS**: An integer representing the number of cards in the game, calculated as `N_PAIRS * 2`.

### Player
Represents a player in the game. It keeps track of the player's name, the number of pairs found, and the number of flips made.
- **name**: A string representing the player's name.
- **pairs**: An integer representing the number of pairs found by the player.
- **flips**: An integer representing the number of flips made by the player.

### Card
- **value**: An integer representing the value of the card.
- **state**: An enum `CardState` representing the state of the card (FACE_UP, FACE_DOWN, EXCLUDED).

## Events and Listeners

### Events

- **pairsEvent**: Fired when the number of pairs is changed.
- **roundEndEvent**: Fired when a round ends.
- **nextPlayer**: Fired when the next player is selected.
- **cardFlipped**: Fired when a card is flipped.
- **pairMatched**: Fired when a pair is matched.
- **shuffleEvent**: Fired when the cards are shuffled.

### Listeners

- **PropertyChangeListener**: Used to listen for property change events like `pairsEvent`, `roundEndEvent`, `nextPlayer`, `cardFlipped`, and `pairMatched`.
- **ActionListener**: Used to listen for action events like button clicks and timer actions.
- **VetoableChangeListener**: Used to listen for vetoable change events like `stateChange` in the `Card` class.

## Bean Communication

### ChallengeLabel

- Listens for `roundEndEvent` to update the best score for the current dimension of the board (specified by the number of pairs).
- Listens for `pairsEvent` to update the text label when the number of pairs changes.

### CounterLabel

- Listens for `flipAction` to increment the flip count for the current player.
- Listens for `shuffleAction`, `pairsEvent`, and `roundEndAction` to reset the flip count for all players.
- Listens for `nextPlayer` to update the current player's flip count.

### ControllerLabel

- Listens for `pairsEvent` to reset the pairs for all players.
- Listens for `cardFlipped` to check if two cards have been flipped and start the match or mismatch timer.
- Listens for `match` and `mismatch` actions to update the pairs for the current player and switch players.
- Listens for `shuffleAction` to reset the pairs for all players.
- Listens for `stateChange` to manage the state of the card.
- Fires `nextPlayer` to notify when the next player is selected.
- Fires `pairMatched` to notify when a pair is matched.
- Fires `roundEndAction` to notify when the round has ended.
- Throws `PropertyVetoException` to veto invalid state changes of the card.

### ChangePairButton

- Fires `pairsEvent` when the number of pairs is changed.

### Card

- Listens for `shuffleEvent` to reset the card state and value.
- Listens for `pairMatched` to update the card state when a pair is matched.
- Fires `cardFlipped` when the card is flipped.
- Fires `stateChange` when the card state changes.

### ShuffleButton

- Fires `shuffleAction` when the button is clicked to shuffle the cards.

## How to Run

1. Package the application using Maven:
    ```sh
    mvn package
    ```
    then run the application using the packaged JAR file:
    ```sh
    java -jar target/MatchingPairs-<VERSION>.jar
    ```

3. Or run the application using directly Maven:
   ```sh
   mvn exec:java
   ```

4. Alternatively, compile all the Java files:
    ```sh
    javac -d bin src/main/java/it/unipi/**/*.java
    ```
    and run the application:
    ```sh
    java -cp bin it.unipi.Main
    ```

