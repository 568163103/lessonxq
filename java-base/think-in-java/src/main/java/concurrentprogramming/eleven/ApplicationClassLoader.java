package concurrentprogramming.eleven;

/**
 * @program: java-base
 * @description:
 * @author: xq
 * @create: 2019-12-30 17:37
 **/
public class ApplicationClassLoader {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(ApplicationClassLoader.class.getClassLoader());
    }
}
