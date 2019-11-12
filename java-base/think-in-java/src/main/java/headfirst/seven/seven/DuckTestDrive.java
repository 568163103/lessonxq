package headfirst.seven.seven;

import headfirst.seven.adapter.TurkeyAdapter;
import headfirst.seven.duck.service.Duck;
import headfirst.seven.duck.service.impl.MallardDuck;
import headfirst.seven.turke.impl.WildTurkey;

/**
 * @author xq
 */
public class DuckTestDrive {
    public static void main(String[] args) {
        MallardDuck mallardDuck = new MallardDuck();

        WildTurkey wildTurkey  = new WildTurkey();

        Duck turkeyAdapter = new TurkeyAdapter(wildTurkey);
        System.out.println("the  mallardDuck say s");
        mallardDuck.quack();
        mallardDuck.fly();

        System.out.println("the  wildTurkey say s");

        wildTurkey.gobble();
        wildTurkey.fly();

        System.out.println("the  turkeyAdapter say s");
        turkeyAdapter.quack();
        turkeyAdapter.fly();

    }
}
