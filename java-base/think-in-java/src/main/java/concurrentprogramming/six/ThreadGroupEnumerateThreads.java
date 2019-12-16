package concurrentprogramming.six;

import java.util.concurrent.TimeUnit;

public class ThreadGroupEnumerateThreads {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup myThreadGroup = new ThreadGroup("myThreadGroup");
        Thread thread = new Thread(myThreadGroup, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(2);

        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        Thread [] list = new Thread[mainGroup.activeCount()];

        int recurseSize = mainGroup.enumerate(list);
        System.out.println(recurseSize);
        recurseSize = mainGroup.enumerate(list,false);
        System.out.println(recurseSize);

    }
}
