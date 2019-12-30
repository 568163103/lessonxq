package concurrentprogramming.nine;

public class RefectTest {

    public static void main(String[] args) throws ClassNotFoundException {
         Singleton singleton = Singleton.getInstance();
        System.out.println(singleton.x);
        System.out.println(singleton.y);
    }
}
