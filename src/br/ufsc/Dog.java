package br.ufsc;

import br.ufsc.exception.EmptyPotException;
import br.ufsc.exception.PotQueueViolationException;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

class Dog implements Callable<Integer> {
    private int coins = 0;

    private Hunter owner;
    private Pot currentPot;
    private Forest forest;

    boolean isSleeping = false;
    Future<Integer> future;

    Dog(Hunter h, Forest f) {
        super();
        owner = h;
        forest = f;
    }

    public Integer call() {
        enterTheForest();
        return 0;
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
     * @return Dog
     */
    Dog cleanDog() {
        return new Dog(owner, forest);
    }

    /**
     *
     */
    void enterForestQueue()
    {
        System.out.println("Method must be overwritten.");
    }

    /**
     *
     */
    private void enterTheForest() {
        System.out.printf("%s - Dog entering the forest.\n", owner.getTeam());

        currentPot = forest.getFirstPot();
        takePotCoins();
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

        enterForestQueue();
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
        isSleeping = true;

        try {
            Thread.sleep(i * Config.TIME_UNIT_MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.printf("Who dares waking the %s dog??\n", owner.getTeam());
        }

        isSleeping = false;
    }
}
