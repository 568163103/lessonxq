package com.xq.jdk8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Test3 {

    public static void main(String[] args) {
//        MyInterFace1 myInterFace1 = ()->{};
//        MyInterFace2 myInterFace2 = ()->{};
//        System.out.println(myInterFace1.getClass().getInterfaces()[0]);
//        System.out.println(myInterFace2.getClass().getInterfaces()[0]);
//        new Thread(()-> System.out.println("hello world")).start();
        List<String> stringList = Arrays.asList("hello", "world", "fwefwqef", "produce");
//        stringList.forEach(s->{
//            System.out.println(s.toUpperCase());
//        });

//        List<String> stringList2 = new ArrayList<>();
//
//        stringList.forEach(item->stringList2.add(item.toUpperCase()));
//        stringList2.forEach(s -> System.out.println(s));
//        stringList.stream().map(item->item.toUpperCase()).forEach(s -> System.out.println(s));
//          stringList.stream().map(String::toUpperCase).forEach(s -> System.out.println(s));
        Function<String,String> function = String::toUpperCase;
        System.out.println(function.getClass().getInterfaces()[0]);
    }

    public static List<String> add(List<String> list, String value) {

        return list;
    }
}

@FunctionalInterface
interface MyInterFace1 {
    String add();
}


@FunctionalInterface
interface MyInterFace2 {
    void method2();
}
