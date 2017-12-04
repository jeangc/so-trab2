package br.ufsc;

import br.ufsc.exception.PotNotEmptyException;

class RescueDog extends Thread {
    void putCoinInEmptyPots(Forest f) {
        for(Pot p: f.getPots()) {
            try {
                System.out.printf("RESCUE DOG: Incrementing pot %s.\n", p.getName());

                p.incrementCoin();
            } catch (PotNotEmptyException e) {
                System.out.printf("RESCUE DOG: Not incremented pot %s. It was not empty.\n", p.getName());
            }
        }
    }
}
