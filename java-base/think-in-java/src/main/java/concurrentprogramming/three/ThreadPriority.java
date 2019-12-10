package concurrentprogramming.three;

public class ThreadPriority {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("t1");
            }
        });
        t1.setPriority(3);
        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println("t2");
            }
        });

        t2.setPriority(10);
        t1.start();
        t2.start();
    }
}
