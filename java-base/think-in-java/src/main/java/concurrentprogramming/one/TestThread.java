package concurrentprogramming.one;

import java.util.concurrent.TimeUnit;

public class TestThread extends Thread{

    public static void main(String[] args) throws InterruptedException {
        TestThread testThread = new TestThread();
        testThread.start();
        TimeUnit.SECONDS.sleep(2);
        testThread.start();
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
