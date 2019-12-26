package concurrentprogramming.night;

/**
 * @author xq
 * 创建一个线程工厂
 */
@FunctionalInterface
public interface ThreadFactory {
    /**
     * 创建一个线程 获得一个任务
     *
     * @param runnable
     * @return
     */
    Thread createThread(Runnable runnable);
}
