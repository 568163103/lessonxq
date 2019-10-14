package three;

import three.service.QuackBehavior;

public class DuckCall implements QuackBehavior {
    public void quack() {
        System.out.println("鸭鸣器叫声");
    }
}
