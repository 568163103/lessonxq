package headfirst.one.character.entity;

import headfirst.one.character.service.WeaponBehavior;

public class PersonAndSprit  {
    public PersonAndSprit(WeaponBehavior weaponBehavior) {
        Character character = new Queen();
        character.setWeaponBehavior(weaponBehavior);
        character.fight();
    }

}
