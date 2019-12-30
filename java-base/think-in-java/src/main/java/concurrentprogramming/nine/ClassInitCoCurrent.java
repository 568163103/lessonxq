package concurrentprogramming.nine;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @program: java-base
 * @description: 多个线程访问类才初始化
 * @author: xq
 * @create: 2019-12-29 23:46
 **/
public class ClassInitCoCurrent {
    static {
        System.out.println("The ClassInit static code block will invoke");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IntStream.range(0,5).forEach(i->{
            new Thread(ClassInitCoCurrent::new);
        });
    }

}
