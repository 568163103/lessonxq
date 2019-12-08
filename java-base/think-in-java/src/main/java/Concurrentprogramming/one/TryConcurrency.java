package Concurrentprogramming.one;

import java.util.concurrent.TimeUnit;

public class TryConcurrency {

    public static void main(String[] args) {

//        new Thread(new Runnable() {
//            public void run() {
//                browseNews();
//            }
//        }).start();
        //jdk8
        new Thread(TryConcurrency::browseNews).start();
        enjoyMusic();
    }

    private static void browseNews() {
        for (; ; ) {
            System.out.println("Uh-huh , the good news .");
            sleep(1);
        }
    }

    private static void enjoyMusic() {
        for (; ; ) {
            System.out.println("Uh-huh , the nice music .");
            sleep(1);
        }
    }


    private static void sleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


