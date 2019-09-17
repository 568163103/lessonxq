package hearfirst.ont.character.main;

import hearfirst.ont.character.entity.PersonAndSprit;
import hearfirst.ont.character.service.impl.KinfeBeahavior;

public class Demon {

    public static void main(String[] args) {
        //静态工厂模式
        PersonAndSprit personAndSprit = new PersonAndSprit(new KinfeBeahavior());
    }
}
