package concurrentprogramming.night;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class BasicThreadPool extends Thread implements ThreadPool {
    private final int initSize;

    private final int maxSize;

    private final int coreSize;

    private int activeCount;

    private final ThreadFactory threadFactory;

    private final RunnableQueue runnableQueue;

    private volatile boolean isShutdown = false;

    private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();

    private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();
    private final long keepAliveTime;
    private final TimeUnit timeUnit;

    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    public BasicThreadPool(int initSize, int maxSize, int coreSize, int queueSize) {
        this(initSize, maxSize, coreSize, DEFAULT_THREAD_FACTORY, queueSize, DEFAULT_DENY_POLICY, 10, TimeUnit.SECONDS);
    }

    public BasicThreadPool(int initSize, int maxSize, int coreSize, ThreadFactory threadFactory, int queueSize, DenyPolicy defaultDenyPolicy, long keepAliveTime, TimeUnit timeUnit) {
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize, defaultDenyPolicy, this);
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.init();
    }

    public void init() {
        start();
        for (int i = 0; i < initSize; i++) {
            newThread();
        }
    }

    public void newThread() {
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);
        ThreadTask threadTask = new ThreadTask(thread, internalTask);
        threadQueue.offer(threadTask);
        this.activeCount++;
        thread.start();

    }

    private void removeThread() {
        ThreadTask threadTask = threadQueue.remove();
        threadTask.internalTask.stop();
        this.activeCount--;
    }

    @Override
    public void run() {
        while (!isShutdown && !isInterrupted()) {
            try {
                timeUnit.sleep(keepAliveTime);
            } catch (InterruptedException e) {
                isShutdown = true;
            }

            synchronized (this) {
                if (isShutdown) {
                    break;
                }

                if (runnableQueue.size() > 0 && activeCount < coreSize) {
                    for (int i = 0; i < coreSize; i++) {
                        newThread();
                    }

                    continue;
                }

                if (runnableQueue.size() > 0 && activeCount < maxSize) {
                    newThread();
                }

                if (runnableQueue.size() == 0 && activeCount > coreSize) {
                    for (int i = coreSize; i < activeCount; i++) {

                        removeThread();
                    }
                }
            }
        }
    }

    @Override
    public void execute(Runnable runnable) {

        runnableQueue.offer(runnable);
    }


    @Override
    public void shutdown() {
        synchronized (this) {
            if (isShutdown) {
                return;
            }
            isShutdown = true;
            threadQueue.forEach(threadTask -> {
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            });
            this.interrupt();
        }

    }

    @Override
    public int initSize() {
        checkIsShutdown();
        return this.initSize;
    }

    @Override
    public int getMaxSize() {
        checkIsShutdown();
        return this.maxSize;
    }

    @Override
    public int getCoreSize() {
        checkIsShutdown();
        return this.coreSize;
    }

    @Override
    public int getQueueSize()
    {
        checkIsShutdown();
        return this.runnableQueue.size();
    }

    @Override
    public int getActiveCount() {
        synchronized (this) {
            return this.activeCount;
        }
    }

    @Override
    public boolean isShutdown() {
        return this.isShutdown;
    }

    public void checkIsShutdown(){
        if (isShutdown){
            throw new IllegalStateException("The thread pool is destroy");
        }
    }
}
