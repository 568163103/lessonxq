package concurrentprogramming.eleven;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: java-base
 * @description:
 * @author: xq
 * @create: 2019-12-30 20:14
 **/
public class MyClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> myClass = myClassLoader.loadClass("concurrentprogramming.eleven.HelloWorld");
        System.out.println(myClass.getClassLoader());

        Object helloWorld = myClass.newInstance();
        System.out.println(helloWorld);
        Method method = myClass.getMethod("welcome");
        String result = (String) method.invoke(helloWorld);
        System.out.println(result);
    }
}
