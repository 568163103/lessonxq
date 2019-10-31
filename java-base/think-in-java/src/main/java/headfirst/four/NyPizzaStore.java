package headfirst.four;

import headfirst.four.constant.Constant;

public class NyPizzaStore extends PizzaStore {
    @Override
    protected Pizza createdPizza(String type) {
        if (type.equals(Constant.CHEESE_PIZZA)) {
            return new NYStyleChessePizza();
        } else if (type.equals("veggie")) {
            return new NYStyleChessePizza();
        } else {
            return null;
        }
    }
}