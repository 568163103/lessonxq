package concurrentprogramming.six;

import java.util.concurrent.TimeUnit;

public class ThreadGroupInterrupt {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("TestGroup");

        new Thread(threadGroup, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println("I will T1 exit");
        }, "thread1").start();

        new Thread(threadGroup, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println("I will T2 exit");
        }, "thread2").start();

        TimeUnit.SECONDS.sleep(2);
        threadGroup.interrupt();

    }
}
