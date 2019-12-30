package concurrentprogramming.nine;

import java.util.Random;

/**
 * @author xq
 */
public class GlobalConstants {
    static {
        System.out.println("The GlobalConstants will be initialized");
    }


    public static final   int MAX = 100;

    public static final int Random = new Random().nextInt();

}
