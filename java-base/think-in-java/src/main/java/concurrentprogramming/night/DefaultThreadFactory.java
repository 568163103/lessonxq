package concurrentprogramming.night;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group = new ThreadGroup("MyThreadPool-" + poolNumber.getAndDecrement());
    private final AtomicInteger threadNumber = new AtomicInteger(1);



    @Override
    public Thread createThread(Runnable runnable) {

        return new Thread(group, runnable, "thread-pool-" + threadNumber.getAndDecrement());

    }
}
