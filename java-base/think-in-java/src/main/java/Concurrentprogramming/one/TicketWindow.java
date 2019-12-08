package Concurrentprogramming.one;

public class TicketWindow extends Thread {

    public final String name;
    public static final int MAX = 50;
    private static int index = 1;

    public TicketWindow(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (index <= MAX) {
            System.out.println("柜台:"+name+"当前号码是:"+(index++));
        }
    }


    public static void main(String[] args) {
        TicketWindow t1 = new TicketWindow("一号出号机");
        t1.start();
        TicketWindow t2 = new TicketWindow("二号出号机");
        t2.start();
        TicketWindow t3 = new TicketWindow("三号出号机");
        t3.start();
        TicketWindow t4 = new TicketWindow("四号出号机");
        t4.start();
    }
}
