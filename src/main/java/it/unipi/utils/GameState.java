package it.unipi.utils;

public class GameState {
    Player[] players;
    int N_PAIRS;
    int N_CARDS;

    public GameState(Player[] players, int N_PAIRS) {
        this.players = players;
        this.N_PAIRS = N_PAIRS;
        this.N_CARDS = N_PAIRS * 2;
    }

    public Player getPlayer(int index) {
        return players[index];
    }

    public int getN_PAIRS() {
        return N_PAIRS;
    }

    public void setN_PAIRS(int n_pairs) {
        this.N_PAIRS = n_pairs;
        this.N_CARDS = n_pairs * 2;
    }

    public int getN_CARDS() {
        return N_CARDS;
    }
}
