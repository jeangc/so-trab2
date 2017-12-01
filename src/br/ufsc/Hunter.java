package br.ufsc;

class Hunter {
    private int coins = 0;

    synchronized void addCoins(int c) {
        coins += c;
    }
}
