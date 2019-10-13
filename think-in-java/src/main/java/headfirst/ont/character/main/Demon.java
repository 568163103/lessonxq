package headfirst.ont.character.main;

import headfirst.ont.character.entity.PersonAndSprit;
import headfirst.ont.character.service.impl.KinfeBeahavior;

public class Demon {

    public static void main(String[] args) {
        //静态工厂模式
        PersonAndSprit personAndSprit = new PersonAndSprit(new KinfeBeahavior());
    }
}
