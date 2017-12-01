package br.ufsc;

import br.ufsc.exception.PotNotEmptyException;

class RescueDog extends Thread {
    void putCoinInEmptyPots(Forest f) {
        for(Pot p: f.getPots()) {
            try {
                p.incrementCoin();
                System.out.printf("Incrementing pot %s.\n", p.getName());
            } catch (PotNotEmptyException e) {
                System.out.printf("Not incremented pot %s. It was not empty.\n", p.getName());
            }
        }
    }
}
