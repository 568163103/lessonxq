package concurrentprogramming.three;

import java.util.concurrent.TimeUnit;

public class InterruptThreadExit {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    System.out.println("hhh");
                }
                System.out.println("I will be exiting");
            }
        };
        thread.start();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("System will be shutdown");
        thread.interrupt();

    }

    public static void  demo2() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (; ; ) {

                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                System.out.println("I will be exiting");
            }
        };
        thread.setDaemon(true);
        thread.start();
        TimeUnit.MINUTES.sleep(10);
        System.out.println("System will be shutdown");
        thread.interrupt();

    }

}





