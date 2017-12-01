package br.ufsc;

import java.util.concurrent.*;


public class Main {
    private static final String[] TEAMS = {"Amarelo", "Verde", "Azul"};
    private static final int DOGS_PER_TEAM = 2;
    private static final int MAXIMUM_PARALLEL_DOGS = 1;

    public static void main(String[] args) {
        for(String t: TEAMS){
            ExecutorService executorService = Executors.newFixedThreadPool(MAXIMUM_PARALLEL_DOGS);

            for (int i = 0; i < DOGS_PER_TEAM; i++) {
                executorService.execute(new Dog() {
                    public void run() {
                        int j = 0;
                        while (true) {
                            System.out.printf("Cachorro %s do time %s\n", this.getName(), t);

                            this.waitForCoins();

                            if (++j == 3) {
                                executorService.submit(this);
                                break;
                            }
                        }
                    }
                });
            }
        }
    }
}
