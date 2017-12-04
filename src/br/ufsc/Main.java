package br.ufsc;

import java.util.ArrayList;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        Forest f = new Forest();
        ArrayList<ExecutorService> executors = new ArrayList<>();

        for (String t : Config.TEAMS) {
            Hunter h = new Hunter(t);
            ExecutorService executorService = Executors.newFixedThreadPool(Config.MAXIMUM_PARALLEL_DOGS);

            executors.add(executorService);

            for (int i = 0; i < Config.DOGS_PER_TEAM; i++) {
                executorService.submit(new Dog(h) {
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
                putCoinInEmptyPots(f);

                for (Pot p : f.getPots()) {
                    synchronized (p) {
                        p.notifyAll();
                    }
                }

                if (Config.winner != null) {
                    interrupt();
                }
            }
        };

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(0);
        ScheduledFuture<?> scheduleFuture = scheduledExecutorService.scheduleAtFixedRate(redDog, 0, 2 * Config.TIME_UNIT_MILLISECONDS, TimeUnit.MILLISECONDS);

        while (Config.winner == null) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        scheduleFuture.cancel(true);
        for(ExecutorService e: executors) {
            e.shutdownNow();
        }

        System.out.printf("\n\nFinalizado com o vencedor %s\n\n", Config.winner);
    }
}
