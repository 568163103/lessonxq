package concurrentprogramming.five;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

/**
 * @author xq
 */
public class BooleanLock implements Lock {
    private Thread currentThread;
    private boolean locked = false;
    private final List<Thread> blockThreadList = new ArrayList<>();


    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {
            while (locked) {
               final Thread tempThread = Thread.currentThread();
                try {
                    if (blockThreadList.contains(Thread.currentThread())){
                         blockThreadList.add(tempThread);
                    }
                    this.wait();
                } catch (InterruptedException e) {
                    blockThreadList.remove(Thread.currentThread());
                    throw e;
                }
            }
            blockThreadList.remove(Thread.currentThread());
            this.locked = true;
            this.currentThread = Thread.currentThread();
        }
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized (this) {
            if (mills <= 0) {
                this.lock();
            } else {
                long remainingMills = mills;
                long endMills = System.currentTimeMillis() + remainingMills;
                while (locked) {
                    if (remainingMills <= 0) {
                        throw new TimeoutException("can not get the locking " + mills);
                    }
                    final Thread tempThread = Thread.currentThread();
                    try {
                        if (!blockThreadList.contains(tempThread)) {
                            blockThreadList.add(tempThread);
                        }

                        this.wait(remainingMills);
                        remainingMills = endMills - System.currentTimeMillis();
                    }catch (InterruptedException e){
                        blockThreadList.remove(tempThread);
                    }

                    blockThreadList.remove(Thread.currentThread());
                    this.locked = true;
                    this.currentThread = Thread.currentThread();

                }
            }
        }
    }

    @Override
    public void unlock() {
        synchronized (this) {
            if (this.currentThread == Thread.currentThread()) {
                this.locked = false;
                Optional.of(Thread.currentThread().getName() + "release the lock")
                        .ifPresent(System.out::print);
                this.notifyAll();
            }
        }
    }

    @Override
    public List<Thread> getBlockedThreads() {
        //获取一个只读List
        return Collections.unmodifiableList(blockThreadList);
    }
}
