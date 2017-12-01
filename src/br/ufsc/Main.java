package br.ufsc;

import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        for (int i=0; i<2; i++) {
            executorService.execute(new Thread() {
                public void run() {
                    int j = 0;
                    try {
                        while(true) {
                            System.out.printf("Asynchronous task %s\n", this.getName());
                            sleep(1000);

                            if (++j == 3) {
                                executorService.submit(this);
                                break;
                            }
                        }
                    } catch (InterruptedException e) {
                        //
                    }
                }
            });
        }
    }
}
