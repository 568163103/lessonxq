package concurrentprogramming.four;

public  class Task implements Runnable {

    private final Object MUTEX = new Object();

    @Override
    public void run() {
        synchronized (MUTEX) {
            System.out.println(Thread.currentThread());
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
          new Thread(Task::new).start();
        }
    }
}
