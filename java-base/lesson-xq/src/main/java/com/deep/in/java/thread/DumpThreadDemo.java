package com.deep.in.java.thread;

/**
 * @ClassName
 * @Description
 * @Auror
 * @Date
 * @Version
 **/
public class DumpThreadDemo {
    public static void main(String[] args) {
        new Throwable().printStackTrace(System.out);
        Thread.dumpStack();
    }
}
