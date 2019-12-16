package concurrentprogramming.six;

import java.util.concurrent.TimeUnit;

public class ThreadGroupDaemon {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group1 = new ThreadGroup("group1");
        new Thread(group1, () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread1").start();


        ThreadGroup group2 = new ThreadGroup("group2");
        new Thread(group2, () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread2").start();

        group2.setDaemon(true);
        TimeUnit.SECONDS.sleep(3);
        System.out.println("group1" + group1.isDestroyed());
        System.out.println("group2" + group2.isDestroyed());
    }


}
