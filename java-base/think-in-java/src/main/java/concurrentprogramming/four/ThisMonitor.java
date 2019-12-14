package concurrentprogramming.four;

import java.util.concurrent.TimeUnit;

public class ThisMonitor {

    public static synchronized void method1() {
        System.out.println(Thread.currentThread().getName() + "enter to method1");
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void method2() {
        System.out.println(Thread.currentThread().getName() + "enter to method2");
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        ThisMonitor thisMonitor = new ThisMonitor();
        new Thread(ThisMonitor::method1, "T1").start();
        new Thread(ThisMonitor::method2, "T2").start();

    }
}
