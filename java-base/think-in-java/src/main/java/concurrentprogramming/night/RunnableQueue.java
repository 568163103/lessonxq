package concurrentprogramming.night;


/**
 * 线程池任务队列
 *
 * @author xq
 */
public interface RunnableQueue {
    /**
     * 提交一个任务
     */
    void offer(Runnable runnable);

    /**
     * 取出一个任务
     */
    Runnable take() throws InterruptedException;

    /**
     * 获取任务的数量
     *
     * @return
     */
    int size();

}
