package concurrentprogramming.five;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author xq
 */
public interface Lock {
    /**
     * 锁对象
     * @throws InterruptedException
     */
    void lock() throws InterruptedException;

    /**
     * 锁对象时间
     * @param mills  毫秒
     * @throws InterruptedException
     * @throws TimeoutException
     */
    void lock(long mills) throws InterruptedException, TimeoutException;

    /**
     * 释放锁
     */
    void unlock();

    List<Thread> getBlockedThreads();
}
