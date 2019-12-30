package concurrentprogramming.eleven;

/**
 * @program: java-base
 * @description: 类加载器的命名空间
 * @author: xq
 * @create: 2019-12-30 23:39
 **/
public class NameSpace {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader classLoader = NameSpace.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass("concurrentprogramming.eleven.HelloWorld");
        Class<?> bClass = classLoader.loadClass("concurrentprogramming.eleven.HelloWorld");
        System.out.println(aClass.hashCode());
        System.out.println(bClass.hashCode());
        System.out.println(aClass == bClass);
    }
}
