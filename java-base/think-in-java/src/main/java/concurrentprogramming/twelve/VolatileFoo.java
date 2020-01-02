package concurrentprogramming.twelve;

import java.util.concurrent.TimeUnit;

/**
 * @program: java-base
 * @description:
 * @author: xq
 * @create: 2020-01-01 23:52
 **/
public class VolatileFoo {
    final static int Max = 5;
    static volatile int init_value = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = init_value;
            while (init_value < Max) {
                if (init_value != localValue) {
                    System.out.printf("The init_value is update to[%d]\n", init_value);
                    localValue = init_value;
                }

            }
        },"Reader").start();
        new Thread(()->{
            int localValue = init_value;
            while (init_value<Max){
                System.out.printf("The init_value will be changed to[%d]\n",++localValue);
                init_value = localValue;
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Update").start();
    }

}
