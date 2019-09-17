package hearfirst.ont.character.entity;

import hearfirst.ont.character.service.WeaponBehavior;

public class PersonAndSprit  {
    public PersonAndSprit(WeaponBehavior weaponBehavior) {
        Character character = new Queen();
        character.setWeaponBehavior(weaponBehavior);
        character.fight();
    }

}
