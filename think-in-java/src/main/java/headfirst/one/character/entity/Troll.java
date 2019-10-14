package headfirst.one.character.entity;

public class Troll extends Character {
    @Override
    public void fight() {
        System.out.println("怪物");
        weaponBehavior.useWeapon();
    }
}
