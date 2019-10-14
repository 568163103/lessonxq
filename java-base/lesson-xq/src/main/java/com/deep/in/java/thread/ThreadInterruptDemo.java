package com.deep.in.java.thread;

public class ThreadInterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(ThreadInterruptDemo::sayHelloWorld);
        thread.start();
        thread.interrupt();
        thread.join();//线程等待 等待线程的加入
        System.out.println("state" + thread.getState());
        System.out.println("Hello Next Thread");
    }

    public static void sayHelloWorld() {
        if (Thread.currentThread().isInterrupted()) {
            System.out.printf("线程终止 [ID : %s]:Hello,World\n", Thread.currentThread().getId());
            return;
        }
        System.out.printf("线程[ID : %s]:Hello,World\n", Thread.currentThread().getId());
    }
}
