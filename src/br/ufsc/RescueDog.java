package br.ufsc;

import br.ufsc.exception.PotNotEmptyException;

class RescueDog extends Thread {
    void putCoinInEmptyPots(Forest f) {
        System.out.printf("RESCUE DOG: Incrementing coins.\n");

        for(Pot p: f.getPots()) {
            try {
                p.incrementCoin();
            } catch (PotNotEmptyException e) {
                //
            }
        }
    }
}
