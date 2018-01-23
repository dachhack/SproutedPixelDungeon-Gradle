package com.github.dachhack.sprout.items.weapon.ranged;

import com.github.dachhack.sprout.sprites.ItemSpriteSheet;

/**
 * Created by matthewporritt on 11/14/17.
 */

public class Bow extends RangedWeapon {

    {
        name = "bow";
        image = ItemSpriteSheet.BOW;
    }

    public Bow() {
        super(2, 20);
    }

    @Override
    public String desc() {
        return "Shooty shoot.";
    }
}
