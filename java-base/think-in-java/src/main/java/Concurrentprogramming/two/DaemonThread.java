package Concurrentprogramming.two;

public class DaemonThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() ->
        {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        );
        t1.setDaemon(true);
        t1.start();
        Thread.sleep(2_000L);
        System.out.println("Main thread finished lifecycle");

    }
}
