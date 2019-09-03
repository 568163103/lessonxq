package com.deep.in.java.thread;

/**
 * @ClassName
 * @Description
 * @Auror
 * @Date
 * @Version
 **/
public class HowToStopThreadDemo {
    public static void main(String[] args) {
        StoppableAction action = new StoppableAction();
        Thread thread = new Thread(action);
        thread.start();

        action.stop();
    }

    private static class StoppableAction implements Runnable {
        private boolean stoppable;

        public void run() {
            if (stoppable) {
                System.out.println("线程退出");
                return;
            }
            System.out.println("线程执行");

        }

        public void stop() {
            stoppable = true;
        }
    }
}
