package br.ufsc;

import br.ufsc.exception.EmptyPotException;

class Dog extends Thread {
    private int coins = 0;
    private Hunter owner;
    private Pot currentPot;


    void goToNextPot()
    {

    }

    void deliverCoinsToOwner()
    {
        owner.addCoins(coins);
        coins = 0;
    }

    void takePotCoins()
    {
        try {
            currentPot.letDogTakeCoins(this);
        } catch (EmptyPotException e) {
            waitForCoins();
            takePotCoins();
        }
    }

    void addCoins(int c) {
        coins += c;
    }

    private boolean isFullOfCoins()
    {
        return this.coins >= Config.MAXIMUM_DOG_COINS;
    }

    private void waitForCoins() {
        try {
            sleep(60 * Config.TIME_UNIT_MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("The dog died asleep. Sad.");
        }
    }

}
