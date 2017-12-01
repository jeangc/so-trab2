package br.ufsc;

import br.ufsc.exception.EmptyPotException;
import br.ufsc.exception.PotQueueViolationException;

import java.util.ArrayList;
import java.util.Random;

class Dog extends Thread {
    private int coins = 0;
    private Hunter owner;
    private Pot currentPot;

    Dog(Hunter h) {
        super();
        owner = h;
    }

    void goSearchForCoins(Forest f) {
        System.out.printf("%s - Dog entering the forest.\n", owner.getTeam());
        currentPot = f.getFirstPot();
        takePotCoins();
    }

    int remainingCoinsCapacity() {
        return Config.MAXIMUM_DOG_COINS - coins;
    }

    void addCoins(int c) {
        coins += c;
    }

    Hunter getOwner() {
        return owner;
    }

    private void takePotCoins() {
        try {
            System.out.printf("%s - Dog trying to take coins from the pot %s.\n", owner.getTeam(), currentPot.getName());

            currentPot.transferCoinsToDog(this);
            takeSomeTime(1);

            if (isFullOfCoins()) {
                deliverCoinsToOwner();
                return;
            }

            goToNextPot();
        } catch (PotQueueViolationException | EmptyPotException e) {
            waitForCoins();
        }

        takePotCoins();
    }

    private boolean isFullOfCoins() {
        return this.coins >= Config.MAXIMUM_DOG_COINS;
    }

    private void deliverCoinsToOwner() {
        System.out.printf("%s - I'm full, going deliver the coins to my owner.\n", owner.getTeam());

        owner.addCoins(coins);
        coins = 0;
    }

    private void goToNextPot() {
        System.out.printf("%s - Going to the next pot.\n", owner.getTeam());

        takeSomeTime(1);

        Random r = new Random();
        ArrayList<Pot> relatedPots = currentPot.getRelatedPots();
        currentPot = relatedPots.get(r.nextInt(relatedPots.size()));
    }

    private void waitForCoins() {
        System.out.printf("%s - Pot without coins, going sleep.\n", owner.getTeam());

        takeSomeTime(60);
    }

    private static void takeSomeTime(int i) {
        try {
            sleep(i * Config.TIME_UNIT_MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("Who dares waking me??");
        }
    }
}
