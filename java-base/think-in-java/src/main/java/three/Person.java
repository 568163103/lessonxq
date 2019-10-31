package three;

import three.service.QuackBehavior;

public class Person {
    QuackBehavior quackBehavior;

    public void useDuckCall(){
        quackBehavior.quack();
    }

    public QuackBehavior getQuackBehavior() {
        return quackBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}
