package com.deep.in.java.thread;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                new Runnable() {
                    public void run() {
                        System.out.printf("线程[ID : %s]:Hello,World\n", Thread.currentThread().getId());
                    }
                }
        );
        thread.start();

        thread.join();//线程等待 等待线程的加入
        System.out.println("state" + thread.getState());
        System.out.println("Hello Next Thread");
    }
}
