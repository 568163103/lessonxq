package three;

import three.service.FlyBehavior;
import three.service.QuackBehavior;

public abstract class Duck {
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public void performQuack(){
     quackBehavior.quack();
    }

    public void swim(){
        System.out.println("All ducks float,even decoys");
    }

    public void display(){

    }

    public void performFly(){
    flyBehavior.fly();
    }

    public FlyBehavior getFlyBehavior() {
        return flyBehavior;
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public QuackBehavior getQuackBehavior() {
        return quackBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}
