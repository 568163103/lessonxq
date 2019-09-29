package hearfirst.four;

/**
 * @author xq
 */
public abstract class PizzaStore {

    public Pizza orderPizza(String type){
        Pizza pizza;
        pizza = createdPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    /**
     * 生产一个Pizza对象
     * @param type  pizza类型
     * @return
     */
    protected abstract Pizza createdPizza(String type);
}