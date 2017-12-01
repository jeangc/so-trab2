package br.ufsc;

class Dog extends Thread {
    private int coins;
    private Hunter owner;
    private Pot currentPot;

    void waitForCoins() {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            //
        }
    }

    void goToNextPot()
    {

    }

    void takeCoinsToOwner()
    {

    }

    void deliverCoinsToOwner()
    {

    }

    private void isFullOfCoins()
    {

    }
}
