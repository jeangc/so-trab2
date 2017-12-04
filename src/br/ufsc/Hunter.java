package br.ufsc;

import br.ufsc.exception.WinnerException;

class Hunter {
    private String team;
    private int coins = 0;

    Hunter(String t) {
        team = t;
    }

    String getTeam() {
        return team;
    }

    synchronized void addCoins(int c) throws WinnerException {
        coins += c;

        if (coins >= Config.REQUIRED_COINS_WINNER) {
            throw new WinnerException();
        }
    }
}
