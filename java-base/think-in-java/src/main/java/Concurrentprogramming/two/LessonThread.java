package Concurrentprogramming.two;

import java.util.stream.IntStream;

public class LessonThread {
    public static final String  PREFIX= "ALEX-";

    public static void main(String[] args) {
        IntStream.range(0,5).mapToObj(LessonThread::createThread).forEach(Thread::start);
    }
    private static Thread createThread(int initName){
        return new Thread(()-> System.out.println(Thread.currentThread().getName()),PREFIX+initName);
    }

}
