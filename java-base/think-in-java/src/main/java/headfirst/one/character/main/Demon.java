package headfirst.one.character.main;

import headfirst.one.character.entity.PersonAndSprit;
import headfirst.one.character.service.impl.KinfeBeahavior;

public class Demon {

    public static void main(String[] args) {
        //静态工厂模式
        PersonAndSprit personAndSprit = new PersonAndSprit(new KinfeBeahavior());
    }
}
