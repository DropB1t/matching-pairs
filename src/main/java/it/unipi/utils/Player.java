package it.unipi.utils;

/**
 * The Player class represents a player in the matching pairs game.
 * It keeps track of the player's name, number of pairs found, and number of flips made.
 */
public class Player {
    private final String name;
    private volatile int pairs;
    private volatile int flips;

    /**
     * Constructs a new Player with the specified name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.pairs = 0;
        this.flips = 0;
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of pairs found by the player.
     *
     * @return the number of pairs found by the player
     */
    public int getPairs() {
        return pairs;
    }

    /**
     * Sets the number of pairs found by the player.
     *
     * @param pairs the new number of pairs found by the player
     */
    public void setPairs(int pairs) {
        this.pairs = pairs;
    }

    /**
     * Returns the number of flips made by the player.
     *
     * @return the number of flips made by the player
     */
    public int getFlips() {
        return flips;
    }

    /**
     * Sets the number of flips made by the player.
     *
     * @param flips the new number of flips made by the player
     */
    public void setFlips(int flips) {
        this.flips = flips;
    }
}
