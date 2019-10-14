package headfirst.one.character.entity;

public class Knight extends Character {
    @Override
    public void fight() {
        System.out.println("骑士");
        weaponBehavior.useWeapon();
    }
}
