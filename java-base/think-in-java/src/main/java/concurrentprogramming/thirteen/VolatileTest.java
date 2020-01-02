package concurrentprogramming.thirteen;

import java.util.concurrent.CountDownLatch;

/**
 * @program: java-base
 * @description:
 * @author: xq
 * @create: 2020-01-03 02:44
 **/
public class VolatileTest {

    private static volatile int i = 0;

    private static final CountDownLatch latch = new CountDownLatch(10);

    private static void inc() {
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int x = 0; x < 1000; x++) {
                    inc();
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println(i);
    }
}
