package br.ufsc;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        Pot p2 = new Pot("Pote 2");
        Pot p1 = new Pot("Pote 1");
        p1.addRelatedPot(p2);
        p2.addRelatedPot(p1);

        Forest f = new Forest(new Pot[]{p1, p2});


        for (String t : Config.TEAMS) {
            Hunter h = new Hunter(t);
            ExecutorService executorService = Executors.newFixedThreadPool(Config.MAXIMUM_PARALLEL_DOGS);

            for (int i = 0; i < Config.DOGS_PER_TEAM; i++) {
                executorService.submit(new Dog(h) {
                    @Override
                    public void run() {
                        enterTheForest(f);

                        if (Config.winner == null) {
                            executorService.submit(this);
                        }
                    }
                });
            }
        }

        RescueDog redDog = new RescueDog() {
            public void run() {
                if (Config.winner != null) {
                    return;
                }

                putCoinInEmptyPots(f);

                for (Pot p : f.getPots()) {
                    synchronized (p) {
                        p.notifyAll();
                    }
                }
            }
        };

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(0);
        scheduledExecutorService.scheduleAtFixedRate(redDog, 0, 2 * Config.TIME_UNIT_MILLISECONDS, TimeUnit.MILLISECONDS);
        // TODO mapear todos os potes da floresta
    }
}
