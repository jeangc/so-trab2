package br.ufsc;

import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) {
        Pot p2 = new Pot("Pote 2", 30);
        Pot p1 = new Pot("Pote 1", 3);
        p1.addRelatedPot(p2);
        p2.addRelatedPot(p1);

        Forest f = new Forest(new Pot[] {p1, p2});

        for(String t: Config.TEAMS){
            Hunter h = new Hunter(t);
            ExecutorService executorService = Executors.newFixedThreadPool(Config.MAXIMUM_PARALLEL_DOGS);

            for (int i = 0; i < Config.DOGS_PER_TEAM; i++) {
                executorService.execute(new Dog(h) {
                    public void run() {
                        goSearchForCoins(f);
                        executorService.submit(this);
                    }
                });
            }
        }

        // TODO cachorro vermelho
        // TODO fila de cachorros dormindo no pote
        // TODO cachorro só pode pegar 20 moedas do pote
    }
}
