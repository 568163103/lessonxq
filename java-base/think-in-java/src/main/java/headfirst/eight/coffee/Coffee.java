package headfirst.eight.coffee;

import headfirst.eight.service.CaffeineBeverage;

public class Coffee  extends CaffeineBeverage {
    @Override
    public void brew() {
        System.out.println("Dripping coffee through filter");
    }
    @Override
    public void addCondiments() {
        System.out.println("Adding Sugar and Milk");
    }

}
