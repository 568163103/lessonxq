package headfirst.one.character.entity;

public class Queen extends Character{
    @Override
    public void fight() {
        System.out.println("女王");
         weaponBehavior.useWeapon();
    }
}
