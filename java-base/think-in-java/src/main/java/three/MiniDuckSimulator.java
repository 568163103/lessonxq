package three;

public class MiniDuckSimulator {
    public static void main(String[] args) {
        MallardDuck mallardDuck = new MallardDuck();
        mallardDuck.performFly();
        mallardDuck.performQuack();

        Duck modelDuck = new ModelDuck();
        modelDuck.performFly();
        modelDuck.setFlyBehavior(new FlyRocketPowered());
        modelDuck.performFly();
        Person person = new PersonUseDuckCall();
        person.useDuckCall();
        person.setQuackBehavior(new DuckCall());
        person.useDuckCall();
    }
}
