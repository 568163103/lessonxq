package three;

import three.service.QuackBehavior;

public class PersonNoQuack implements QuackBehavior {
    public void quack() {
        System.out.println("人不会鸭子叫");
    }
}
