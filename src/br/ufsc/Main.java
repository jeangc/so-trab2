package br.ufsc;

import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) {
        for(String t: Config.TEAMS){
            ExecutorService executorService = Executors.newFixedThreadPool(Config.MAXIMUM_PARALLEL_DOGS);

            for (int i = 0; i < Config.DOGS_PER_TEAM; i++) {
                executorService.execute(new Dog() {
                    public void run() {
                        int j = 0;
                        while (true) {
                            System.out.printf("Cachorro %s do time %s\n", this.getName(), t);

//                            this.takePotCoins();

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
