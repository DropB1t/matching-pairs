package it.unipi.utils;

public class GameState {
    Player[] players;
    int N_PAIRS;

    public GameState(Player[] players, int N_PAIRS) {
        this.players = players;
        this.N_PAIRS = N_PAIRS;
    }

    public Player getPlayer(int index) {
        return players[index];
    }

    public int getN_PAIRS() {
        return N_PAIRS;
    }

    public void setN_PAIRS(int n_pairs) {
        this.N_PAIRS = n_pairs;
    }
}
