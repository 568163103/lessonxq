package concurrentprogramming.four;

import java.util.concurrent.TimeUnit;

public class Mutex {
    public static class Task implements Runnable {
        private final Object MUTEX = new Object();

        @Override
        public void run() {
            synchronized (MUTEX) {
                System.out.println(Thread.currentThread());
            }
        }
    }

    public static final Object MUTEX = new Object();

    public void accessResource() {
        synchronized (MUTEX) {
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        final Mutex mutex = new Mutex();
//        for (int i = 0; i < 5; i++) {
//            new Thread(mutex::accessResource).start();
//        }

        for (int i = 0; i < 5; i++) {
            new Thread(Task::new).start();
        }
    }
}
