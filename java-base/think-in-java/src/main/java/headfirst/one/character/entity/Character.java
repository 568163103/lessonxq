package headfirst.one.character.entity;

import headfirst.one.character.service.WeaponBehavior;

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
