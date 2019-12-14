package concurrentprogramming.four;

import java.util.concurrent.TimeUnit;

public class TicketWindowRunnable implements Runnable {
    private int index = 1;
    private static final int MAX = 1000;
    private final Object object = new Object();

    @Override
    public void run() {

        synchronized (object) {
        while (index <= MAX) {
            System.out.println(Thread.currentThread() + " 的号码是" + (index++));
            }
        }

    }

    public static void main(String[] args) {
        final   TicketWindowRunnable task = new TicketWindowRunnable();
        Thread thread1 = new Thread(task, "一号窗口");
        Thread thread2 = new Thread(task, "二号窗口");
        Thread thread3 = new Thread(task, "三号窗口");
        Thread thread4 = new Thread(task, "四号窗口");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
