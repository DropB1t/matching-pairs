package it.unipi;

public class Player {
    private final String name;
    private volatile int pairs;
    private volatile int flips;


    public Player(String name) {
        this.name = name;
        this.pairs = 0;
        this.flips = 0;
    }

    public String getName() {
        return name;
    }

    public int getPairs() {
        return pairs;
    }

    public void setPairs(int pairs) {
        this.pairs = pairs;
    }

    public int getFlips() {
        return flips;
    }

    public void setFlips(int flips) {
        this.flips = flips;
    }
}
