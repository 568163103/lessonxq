package hearfirst.three;

public class Test {
    public static void main(String[] args) {
        Beverage espresso = new Espresso();
        System.out.println("这个饮料为" + espresso.getDescription() + "价格为 " + espresso.cost() + "元");
        Beverage beverage2 = new HouseBlend();
        beverage2 = new MoCha(beverage2);
        beverage2 = new Whip(beverage2);
        System.out.println("这个饮料里面含有" + beverage2.getDescription()+"价格为 =" + beverage2.cost());
    }


}
