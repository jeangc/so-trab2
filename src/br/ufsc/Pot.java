package br.ufsc;

import br.ufsc.exception.EmptyPotException;

import java.util.ArrayList;

class Pot {
    private int coins = 0;

    private ArrayList<Dog> dogs;
    private ArrayList<Pot> relatedPots;

    Pot(int c) {
        coins = c;
        relatedPots = new ArrayList<>();
    }

    synchronized void transferCoinsToDog(Dog d) throws EmptyPotException {
        if(coins == 0) {
            throw new EmptyPotException();
        }

        System.out.printf("%s - Adding %d coins to the dog.\n", d.getOwner().getTeam(), coins);
        d.addCoins(coins);
        coins = 0;
    }

    void addRelatedPot(Pot p) {
        this.relatedPots.add(p);
    }

    ArrayList<Pot> getRelatedPots() {
        return relatedPots;
    }
}
