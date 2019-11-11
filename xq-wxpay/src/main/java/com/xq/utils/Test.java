package com.xq.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Map<String,String> map = new HashMap<>();


        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("key","1");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("key","2");
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("key","3");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("key","4");
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("key","5");
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("key","6");
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put("key","7");
            }
        }).start();



        new Thread(new Runnable() {
            @Override
            public void run() {
               for (int i =0 ; i<99 ;i++) {
                   map.put("key", map.get("key") + "1");
               }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("threa1=="+map.get("key").length());

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =0 ; i<99 ;i++) {
                    map.put("key", map.get("key") + "1");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(map.get("key").length());
                System.out.println(new Date());

            }
        }).start();

    }

}
