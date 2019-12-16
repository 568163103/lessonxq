package concurrentprogramming.six;

/**
 * @author xq
 */
public class ThreadGroupCreator {

    public static void main(String[] args) {
        ThreadGroup threadGroup  = Thread.currentThread().getThreadGroup();

        ThreadGroup threadGroup1 = new ThreadGroup("T1");

        System.out.println(threadGroup == threadGroup1.getParent());

        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1,"T2");
        System.out.println(threadGroup2.getParent() == threadGroup1);
    }

}
