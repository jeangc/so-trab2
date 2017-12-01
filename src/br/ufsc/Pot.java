package br.ufsc;

import br.ufsc.exception.EmptyPotException;

import java.util.ArrayList;
import java.util.Queue;

class Pot {
    private int coins = 0;

    private Queue<Dog> sleepingDogs;
    private ArrayList<Pot> relatedPots;

    Pot(int c) {
        coins = c;
        relatedPots = new ArrayList<>();
    }

    synchronized void transferCoinsToDog(Dog d) throws EmptyPotException {
        if(coins == 0) {
            throw new EmptyPotException();
        }

        int transferredCoins = Integer.min(d.remainingCoinsCapacity(), coins);
        d.addCoins(transferredCoins);
        coins -= transferredCoins;

        System.out.printf("%s - Adding %d coins to the dog, %d remaining.\n", d.getOwner().getTeam(), transferredCoins, coins);
    }

    void addRelatedPot(Pot p) {
        this.relatedPots.add(p);
    }

    ArrayList<Pot> getRelatedPots() {
        return relatedPots;
    }
}
