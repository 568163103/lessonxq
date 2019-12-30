package concurrentprogramming.ten;

/**
 * @program: java-base
 * @description:
 * @author: xq
 * @create: 2019-12-30 15:38
 **/
public class BootStrapClassLoader {
    public static void main(String[] args) {
        System.out.println("Bootstrap:"+String.class.getClassLoader());
        System.out.println(System.getProperty("sun.boot.class.path"));
    }
}
