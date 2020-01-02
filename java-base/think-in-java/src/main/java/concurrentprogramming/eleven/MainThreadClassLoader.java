package concurrentprogramming.eleven;

/**
 * @program: java-base
 * @description: 线程类的上下文
 * @author: xq
 * @create: 2020-01-01 23:07
 **/
public class MainThreadClassLoader {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
    }
}
