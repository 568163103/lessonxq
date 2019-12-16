package concurrentprogramming.five;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author xq
 */
public class BooleanLockTest {
    private final Lock lock = new BooleanLock();

    public void synchronizedMethod()  {
        try {
            lock.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int random = ThreadLocalRandom.current().nextInt(10);
        System.out.println(Thread.currentThread() + "get the lock");
        try {
            TimeUnit.SECONDS.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {

        BooleanLockTest booleanLockTest  = new BooleanLockTest();
        IntStream.range(0,10).mapToObj(i-> new Thread(booleanLockTest::synchronizedMethod)).forEach(Thread::start);
    }

}
