package headfirst.three;

public class MoCha extends CondimentDecorator {
    Beverage beverage;

    public MoCha(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        description = beverage.getDescription()+"Mo Cha";
        return description;
    }

    public double cost() {
        return 2 + beverage.cost();
    }
}
