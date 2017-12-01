package br.ufsc;

class Hunter {
    private String team;
    private int coins = 0;

    Hunter(String t) {
        team = t;
    }

    String getTeam() {
        return team;
    }

    int getCoins() {
        return coins;
    }

    synchronized void addCoins(int c) {
        coins += c;
    }
}
