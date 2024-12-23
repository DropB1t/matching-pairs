package it.unipi.utils;

/**
 * The GameState class represents the state of the game, including the players and the number of pairs and cards.
 */
public class GameState {
    private Player[] players;
    private int N_PAIRS;
    private int N_CARDS;

    /**
     * Constructs a new GameState with the specified players and number of pairs.
     *
     * @param players the array of players participating in the game
     * @param N_PAIRS the number of pairs in the game
     */
    public GameState(Player[] players, int N_PAIRS) {
        this.players = players;
        this.N_PAIRS = N_PAIRS;
        this.N_CARDS = N_PAIRS * 2;
    }

    /**
     * Returns the array of players participating in the game.
     *
     * @return the array of players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Returns the player at the specified index.
     *
     * @param index the index of the player to return
     * @return the player at the specified index
     */
    public Player getPlayer(int index) {
        return players[index];
    }

    /**
     * Returns the number of pairs in the game.
     *
     * @return the number of pairs
     */
    public int getN_PAIRS() {
        return N_PAIRS;
    }

    /**
     * Returns the number of cards in the game.
     *
     * @return the number of cards
     */
    public int getN_CARDS() {
        return N_CARDS;
    }

    /**
     * Sets the number of pairs in the game and updates the number of cards accordingly.
     *
     * @param n_pairs the new number of pairs
     */
    public void setN_PAIRS(int n_pairs) {
        this.N_PAIRS = n_pairs;
        this.N_CARDS = n_pairs * 2;
    }

}
