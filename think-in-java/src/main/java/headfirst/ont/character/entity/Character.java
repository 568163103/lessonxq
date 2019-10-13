package headfirst.ont.character.entity;

import headfirst.ont.character.service.WeaponBehavior;

public abstract class Character {
    WeaponBehavior weaponBehavior;

    abstract void fight();

    public WeaponBehavior getWeaponBehavior() {
        return weaponBehavior;
    }

    public void setWeaponBehavior(WeaponBehavior weaponBehavior) {
        this.weaponBehavior = weaponBehavior;
    }
}
