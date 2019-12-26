package concurrentprogramming.night;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue {
    //任务队列的最大容量
    private final int limit;
    //若任务队列容量已满，执行拒绝侧率
    private final DenyPolicy denyPolicy;
    //存放任务的队列
    private final LinkedList<Runnable> runnableLinkedList = new LinkedList<>();

    private ThreadPool threadPool;


    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (runnableLinkedList) {
            if (runnableLinkedList.size() >= limit) {
                denyPolicy.reject(runnable, threadPool);
            } else {
                runnableLinkedList.addLast(runnable);
                runnableLinkedList.notifyAll();
            }
        }
    }

    @Override
    public Runnable take() throws InterruptedException {
        synchronized (runnableLinkedList) {
            while (runnableLinkedList.isEmpty()) {
                try {
                    runnableLinkedList.wait();
                } catch (InterruptedException e) {
                    throw e;
                }

            }
            return runnableLinkedList.removeFirst();
        }


    }

    @Override
    public int size() {
        return runnableLinkedList.size();
    }
}
