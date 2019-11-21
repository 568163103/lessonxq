package headfirst.seven.seven.duck.service.impl;


import headfirst.seven.seven.duck.service.Duck;

/**
 * @author xq
 */
public class MallardDuck implements Duck {
    public void quack() {
        System.out.println("Quack");
    }

    public void fly() {
        System.out.println("I'm flying");
    }
}
