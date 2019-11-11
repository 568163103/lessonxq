package headfirst.seven;

public class DuckTestDrive {
    public static void main(String[] args) {
        MallardDuck duck = new MallardDuck();

        WildTurkey wildTurkey = new WildTurkey();

        Duck turkeyAdapter = new TurkeyAdapter(wildTurkey);
        System.out.println("The duck says");
        wildTurkey.gobble();
        wildTurkey.fly();
        System.out.println("The Duck says ...");
        testDuck(duck);
        System.out.println("The turkeyAdapter  says .....");
        testDuck(turkeyAdapter);
    }

    public static void testDuck(Duck duck){
        duck.quack();
        duck.fly();
    }
}
