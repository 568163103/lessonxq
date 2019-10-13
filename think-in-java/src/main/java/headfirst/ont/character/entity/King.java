package headfirst.ont.character.entity;

public class King extends Character {
    @Override
    public void fight() {
        System.out.println("国王");
        weaponBehavior.useWeapon();
    }
}
