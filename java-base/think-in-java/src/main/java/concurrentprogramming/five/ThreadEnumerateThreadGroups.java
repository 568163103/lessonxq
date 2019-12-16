package concurrentprogramming.five;

import java.util.concurrent.TimeUnit;

/**
 * @author xq
 */
public class ThreadEnumerateThreadGroups {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup1 = new ThreadGroup("MyGroup1");

        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1,"MyGroup2");
        TimeUnit.SECONDS.sleep(2);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup [] list = new ThreadGroup[mainGroup.activeCount()];
        int  recurseSize = mainGroup.enumerate(list);
        System.out.println(recurseSize);
        recurseSize = mainGroup.enumerate(list,false);
        System.out.println(recurseSize);
    }
}
