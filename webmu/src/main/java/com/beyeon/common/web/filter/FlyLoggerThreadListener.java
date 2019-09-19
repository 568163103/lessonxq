package com.beyeon.common.web.filter;

import java.util.Observable;
import java.util.Observer;
 
/**
 * FlyLoggerThreadListener
 * Created by sff on 2017/5/6.
 */
public class FlyLoggerThreadListener implements Observer {
    @Override
    public void update(Observable o, Object arg) {
//        Object b = arg;
        FlyLoggerThread thread = (FlyLoggerThread) o;
        System.out.println("RunThread死机");
        //从线程池中判断是哪个有问题
        new Thread(thread).start();
        System.out.println("RunThread重启");
    }
 
}

