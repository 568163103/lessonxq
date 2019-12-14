package concurrentprogramming.three;

import java.util.concurrent.TimeUnit;

public class FlagThreadExit {

    static class MyTask extends Thread {
        private volatile boolean close = false;

        @Override
        public void run() {
            System.out.println("I will start work");
            while (!close && !isInterrupted()) {

            }
            System.out.println("I will be exit");
        }

        public void closed() {
            this.close = true;
            this.interrupt();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyTask myTask = new MyTask();
        myTask.start();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("System will be shutdown");
        myTask.closed();
    }


}
