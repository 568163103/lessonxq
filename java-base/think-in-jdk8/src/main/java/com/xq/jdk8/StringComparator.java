package com.xq.jdk8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class StringComparator {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("zhangsan","lisi","wangwu","zhaoliu");

        Collections.sort(list,(o1,o2)->o2.compareTo(o1));
        System.out.println(list);  
    }
}
