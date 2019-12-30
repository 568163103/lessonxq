package concurrentprogramming.nine;

public class Singleton {
    private static Singleton instance = new Singleton();
    public static int x =0;
    public static int y;

    private Singleton(){
        x++;
        y++;
    }

    public static Singleton getInstance(){
        return instance;
    }

//    public static void main(String[] args) {
//        Singleton singleton = Singleton.getInstance();
//        System.out.println(Singleton.x);
//        System.out.println(Singleton.y);
//    }


}
