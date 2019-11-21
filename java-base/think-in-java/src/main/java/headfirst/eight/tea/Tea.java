package headfirst.eight.tea;

import headfirst.eight.service.CaffeineBeverage;

public class Tea extends CaffeineBeverage {


    @Override
    public void brew() {
        System.out.println("Steeping the tea");
    }

    @Override
    public void addCondiments() {
        System.out.println("addLemon");
    }


}
