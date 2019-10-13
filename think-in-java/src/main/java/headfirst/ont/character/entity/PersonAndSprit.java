package headfirst.ont.character.entity;

import headfirst.ont.character.service.WeaponBehavior;

public class PersonAndSprit  {
    public PersonAndSprit(WeaponBehavior weaponBehavior) {
        Character character = new Queen();
        character.setWeaponBehavior(weaponBehavior);
        character.fight();
    }

}
