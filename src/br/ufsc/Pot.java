package br.ufsc;

import br.ufsc.exception.EmptyPotException;
import br.ufsc.exception.PotNotEmptyException;

import java.util.ArrayList;
import java.util.Queue;

class Pot {
    private String name;
    private int coins = 0;

    private Queue<Dog> sleepingDogs;
    private ArrayList<Pot> relatedPots;

    Pot(String s) {
        name = s;
        relatedPots = new ArrayList<>();
    }

    synchronized void incrementCoin() throws PotNotEmptyException {
        if (coins > 0) {
            throw new PotNotEmptyException();
        }

        coins++;
        wakeUpSleepingDogs();
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

    String getName() {
        return name;
    }

    private void wakeUpSleepingDogs() {

    }
}
