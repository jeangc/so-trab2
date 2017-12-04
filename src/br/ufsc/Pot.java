package br.ufsc;

import br.ufsc.exception.EmptyPotException;
import br.ufsc.exception.PotNotEmptyException;
import br.ufsc.exception.PotQueueViolationException;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

class Pot {
    private String name;
    private int coins = 0;

    private ArrayList<Pot> relatedPots;
    private LinkedBlockingQueue<Dog> sleepingDogs;

    Pot(String s) {
        name = s;

        relatedPots = new ArrayList<>();
        sleepingDogs = new LinkedBlockingQueue<>();
    }

    synchronized void transferCoinsToDog(Dog d) throws EmptyPotException, PotQueueViolationException {
        if (!sleepingDogs.contains(d)) {
            sleepingDogs.add(d);
        }

        if(!sleepingDogs.isEmpty() && !sleepingDogs.peek().equals(d)) {
            throw new PotQueueViolationException();
        }

        if(coins == 0) {
            throw new EmptyPotException();
        }

        int transferredCoins = Integer.min(d.remainingCoinsCapacity(), coins);
        d.addCoins(transferredCoins);
        coins -= transferredCoins;
        sleepingDogs.remove();

        System.out.printf("%s - Adding %d coins to the dog, %d remaining.\n", d.getOwner().getTeam(), transferredCoins, coins);
    }

    synchronized void incrementCoin() throws PotNotEmptyException {
        if (coins > 0) {
            throw new PotNotEmptyException();
        }

        coins++;
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
}
