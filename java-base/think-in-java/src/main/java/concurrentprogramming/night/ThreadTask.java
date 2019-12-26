package concurrentprogramming.night;

public class ThreadTask {

    public ThreadTask(Thread thread, InternalTask internalTask) {
        this.thread = thread;
        this.internalTask = internalTask;
    }

    Thread thread;
    InternalTask internalTask;
}
