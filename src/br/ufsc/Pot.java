package br.ufsc;

import br.ufsc.exception.EmptyPotException;

class Pot {
    private int coins = 0;
    private Dog[] dogs;

    synchronized void transferCoinsToDog(Dog d) throws EmptyPotException {
        if(coins == 0) {
            throw new EmptyPotException();
        }

        d.addCoins(coins);
        coins = 0;
    }
}
