package hearfirst.four;

import hearfirst.four.constant.Constant;

public class Test {

    public static void main(String[] args) {
        PizzaStore nyPizzaStore = new NyPizzaStore();
        PizzaStore chicagoPizzaStore = new ChicagoStylePizzaStore();
        Pizza pizza =  nyPizzaStore.orderPizza(Constant.CHEESE_PIZZA);
        System.out.println("pizza === "+pizza.getName());
        Pizza chicagoPizza =  chicagoPizzaStore.createdPizza(Constant.CHEESE_PIZZA);
        System.out.println("chicagoPizza =="+ chicagoPizza.getName());

    }
}
