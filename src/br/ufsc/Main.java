package br.ufsc;

import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Pot p2 = new Pot("Pote 2");
        Pot p1 = new Pot("Pote 1");
        p1.addRelatedPot(p2);
        p2.addRelatedPot(p1);

        Forest f = new Forest(new Pot[]{p1, p2});
        ArrayList<Dog> dogs = new ArrayList<>();

        for (String t : Config.TEAMS) {
            Hunter h = new Hunter(t);
            ExecutorService executorService = Executors.newFixedThreadPool(Config.MAXIMUM_PARALLEL_DOGS);

            for (int i = 0; i < Config.DOGS_PER_TEAM; i++) {
                Dog d = new Dog(h, f) {
                    @Override
                    public void enterForestQueue() {
                        Dog d = this.cleanDog();
                        d.future = executorService.submit(d);
                        dogs.add(d);

                        System.out.println("Re-enqueuing the dog.");
                    }
                };

                d.future = executorService.submit(d);
                dogs.add(d);
            }
        }

        RescueDog redDog = new RescueDog() {
            public void run() {
                System.out.printf("%d cachorros na lista e %d threads rodando.\n", dogs.size(), Thread.activeCount());

                putCoinInEmptyPots(f);

                for(Dog d: dogs) {
                    System.out.printf("O cachorro %s está %s\n", d.getOwner().getTeam(), (d.isSleeping ? "dormindo" : "acordado"));

                    if (d.isSleeping) {
                        d.future.cancel(true);
                        System.out.println("Waking-up a thread.");
                    }

                    if (d.future.isDone() || d.future.isCancelled()) {
                        String s = (d.future.isDone() ? "concluida" : "") + " " + (d.future.isCancelled() ? "cancelada" : "");
                        System.out.printf("Removendo future da lista e ela está %s.\n", s);
                        //futures.remove(f);
                    }
                }
            }
        };

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(redDog, 0, 2 * Config.TIME_UNIT_MILLISECONDS, TimeUnit.MILLISECONDS);

        // TODO acordar os cachorros quando botar moeda
        // TODO mapear todos os potes da floresta
        // TODO parar quando alguém ganha
    }
}
