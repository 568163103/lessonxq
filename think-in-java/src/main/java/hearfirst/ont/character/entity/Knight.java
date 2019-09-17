package hearfirst.ont.character.entity;

import hearfirst.ont.character.service.WeaponBehavior;

public class Knight extends Character {
    @Override
    public void fight() {
        System.out.println("骑士");
        weaponBehavior.useWeapon();
    }
}
