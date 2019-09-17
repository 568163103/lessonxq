package hearfirst.ont.character.entity;

import hearfirst.ont.character.service.WeaponBehavior;

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
