package hearfirst.four;

import hearfirst.four.constant.Constant;

public class ChicagoStylePizzaStore extends PizzaStore {
    @Override
    protected Pizza createdPizza(String type) {
        if (type.equals(Constant.CHEESE_PIZZA)) {
            return new ChicagoStyleCheesePizza();
        } else if (type.equals("veggie")) {
            return new NYStyleChessePizza();
        } else {
            return null;
        }
    }
}
