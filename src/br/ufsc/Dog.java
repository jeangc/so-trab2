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

    /**
     *
     */
    void enterTheForest(Forest f) {
        System.out.printf("%s - Dog entering the forest.\n", owner.getTeam());

        currentPot = f.getFirstPot();
        takePotCoins();
    }

    /**
     *
     * @return int
     */
    int remainingCoinsCapacity() {
        return Config.MAXIMUM_DOG_COINS - coins;
    }

    /**
     *
     * @param c -
     */
    void addCoins(int c) {
        coins += c;

        System.out.printf("%s - received %d coins. Total %d.\n", owner.getTeam(), c, coins);
    }

    /**
     *
     * @return Hunter
     */
    Hunter getOwner() {
        return owner;
    }

    /**
     *
     */
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

    /**
     *
     * @return boolean
     */
    private boolean isFullOfCoins() {
        return this.coins >= Config.MAXIMUM_DOG_COINS;
    }

    /**
     *
     */
    private void deliverCoinsToOwner() {
        System.out.printf("%s - I'm full, going deliver the coins to my owner.\n", owner.getTeam());

        owner.addCoins(coins);
        coins = 0;
    }

    /**
     *
     */
    private void goToNextPot() {
        takeSomeTime(1);

        Random r = new Random();
        ArrayList<Pot> relatedPots = currentPot.getRelatedPots();
        currentPot = relatedPots.get(r.nextInt(relatedPots.size()));

        System.out.printf("%s - Going to the pot %s in 1 unit.\n", owner.getTeam(), currentPot.getName());
    }

    /**
     *
     */
    private void waitForCoins() {
        System.out.printf("%s - Pot without coins, going sleep for 60 units.\n", owner.getTeam());

        takeSomeTime(60);
    }

    /**
     *
     * @param i - time units for sleep
     */
    private void takeSomeTime(int i) {
        synchronized (currentPot) {
            try {
                currentPot.wait(i * Config.TIME_UNIT_MILLISECONDS);
            } catch (InterruptedException e) {
                System.out.printf("Who dares waking the %s dog??\n", owner.getTeam());
            }
        }
    }
}
