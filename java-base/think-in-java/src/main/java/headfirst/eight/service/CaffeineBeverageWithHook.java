package headfirst.eight.service;

/**
 * @author xq
 * @Date 2019/11/20 23:41:30
 */
public abstract class CaffeineBeverageWithHook {
    public  void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()){
            addCondiments();
        }

    }

    public abstract void brew();
    public abstract void addCondiments();

    public void boilWater(){
        System.out.println("Boiling water");
    }

    public void pourInCup(){
        System.out.println("Pouring into cup");
    }
    public boolean customerWantsCondiments(){
        return true;
    }
}
