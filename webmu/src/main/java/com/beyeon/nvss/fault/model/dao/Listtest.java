package com.beyeon.nvss.fault.model.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Listtest {
    public static void main(String args[]){
        List<Long> lists = new ArrayList<Long>();

        for(Long i=0l;i<1000000l;i++){
            lists.add(i);
        }  
        
        Long oneOk = oneMethod(lists);
        Long twoOk = twoMethod(lists);
        Long threeOk = threeMethod(lists);
        Long fourOk = fourMethod(lists);
        
        System.out.println("One:" + oneOk);
        System.out.println("Two:" + twoOk);
        System.out.println("Three:" + threeOk);
        System.out.println("four:" + fourOk);
        
    }
    
    public static Long oneMethod(List<Long> lists){
        
        Long timeStart = System.currentTimeMillis();
        for(int i=0;i<lists.size();i++)    {
            System.out.println(lists.get(i));
        }
        Long timeStop = System.currentTimeMillis();

        return timeStop -timeStart ;
    }
    
    public static Long twoMethod(List<Long> lists){
        
        Long timeStart = System.currentTimeMillis();
        for(Long string : lists)    {
            System.out.println(string);
        }
        Long timeStop = System.currentTimeMillis();

        return timeStop -timeStart ;
    }
    
    public static Long threeMethod(List<Long> lists){
        
        Long timeStart = System.currentTimeMillis();
        Iterator<Long> it = lists.iterator();
        while (it.hasNext())
        {
                System.out.println(it.next());
        }
        Long timeStop = System.currentTimeMillis();

        return timeStop -timeStart ;
    }    
    
        
    
    public static Long fourMethod(List<Long> lists){
        
        Long timeStart = System.currentTimeMillis();
        for(Iterator<Long> i = lists.iterator(); i.hasNext();)    {
            System.out.println(i.next());
        }
        Long timeStop = System.currentTimeMillis();

        return timeStop -timeStart ;
    }    
}
