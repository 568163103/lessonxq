package concurrentprogramming.night;

/**
 * 线程池接口
 *
 * @author xq
 */
public interface ThreadPool {
    /**
     * 提交一个线程任务
     *
     * @param runnable
     */
    void execute(Runnable runnable);

    /**
     * 关闭线程池
     */
    void shutdown();

    /**
     * 获取线程池的大小
     *
     * @return
     */
    int initSize();

    /**
     * 获取线程池最大的线程数
     *
     * @return
     */
    int getMaxSize();

    /**
     * 获取核心线程数量
     *
     * @return
     */
    int getCoreSize();

    /**
     * 获取线程池中用于缓存任务队列的大小
     *
     * @return
     */
    int getQueueSize();

    /**
     * 获取线程池中活跃线程的数量
     *
     * @return
     */
    int getActiveCount();


    /**
     * 查看线程池是否已经被shutdown
     *
     * @return
     */
    boolean isShutdown();


}
