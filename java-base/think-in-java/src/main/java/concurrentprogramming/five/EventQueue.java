package concurrentprogramming.five;

import java.util.LinkedList;

/**
 * @author xq
 * @description 队列处理事件
 */
public class EventQueue {
    private final int max;

    static class Event {

    }

    private final LinkedList<Event> eventQueue = new LinkedList<>();
    public static final int DEFAULT_MAX = 10;

    public EventQueue() {
        this(DEFAULT_MAX);
    }

    public EventQueue(int max) {
        this.max = max;
    }

    public void offer(Event event) {
        synchronized (eventQueue) {
            if (eventQueue.size() >= max) {
                console("the queue is full");
                try {
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            console("the new event is submitted");
            eventQueue.addLast(event);
            eventQueue.notify();

        }
    }

    public Event take() {
        synchronized (eventQueue) {
            if (eventQueue.isEmpty()) {
                console("the queue is empty");
                try {
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Event event = eventQueue.removeFirst();
            this.eventQueue.notify();
            console("the event " + event + "is handled");
            return event;
        }

    }

    private void console(String message) {
        System.out.printf("%s:%s\n", Thread.currentThread().getName(), message);
    }


}
