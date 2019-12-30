package concurrentprogramming.ten;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: java-base
 * @description:
 * @author: xq
 * @create: 2019-12-31 00:02
 **/
public class SimpleClass {
    private static byte[] buffer = new byte[8];
    private static String str = "";
    private static List<String> list = new ArrayList<>();

    static {
        buffer[0] = (byte) 1;
        str="Simple";
        list.add("element");
    }
}
