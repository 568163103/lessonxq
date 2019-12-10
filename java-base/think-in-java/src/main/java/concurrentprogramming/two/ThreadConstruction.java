package concurrentprogramming.two;

public class ThreadConstruction {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1");

        ThreadGroup threadGroup = new ThreadGroup("testGroup");

        Thread t2 = new Thread(threadGroup,"t2");

        ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
        System.out.println("mainThreadGroup Name:"+ mainThreadGroup.getName());

        System.out.println("t1 threadGroup == mainThreadGroup :"+(t1.getThreadGroup() == mainThreadGroup) );
        System.out.println("t1 threadGroup == mainThreadGroup :"+(t2.getThreadGroup() == mainThreadGroup) );

        System.out.println("t2 threadGroup == mainThreadGroup :"+(t2.getThreadGroup() == threadGroup) );

    }
}
