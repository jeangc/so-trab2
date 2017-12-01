package br.ufsc;

import br.ufsc.exception.EmptyPotException;

class Pot {
    private int coins = 0;

    private Dog[] dogs;
    private Pot[] relatedPots;

    public Pot(Pot[] p) {
        relatedPots = p;
    }

    synchronized void transferCoinsToDog(Dog d) throws EmptyPotException {
        if(coins == 0) {
            throw new EmptyPotException();
        }

        d.addCoins(coins);
        coins = 0;
    }

    Pot[] getRelatedPots() {
        return relatedPots;
    }
}
