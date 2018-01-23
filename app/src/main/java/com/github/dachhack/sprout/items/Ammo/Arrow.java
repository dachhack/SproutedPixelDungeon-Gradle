package com.github.dachhack.sprout.items.Ammo;

import com.github.dachhack.sprout.sprites.ItemSpriteSheet;

/**
 * Created by matthewporritt on 8/12/17.
 */

public class Arrow extends Ammo {

    {
        name = "arrow";
        image = ItemSpriteSheet.ARROW;

        MIN = 0;
        MAX = 1;

        DIE = 1;
        SIDES = 4;

        speedFactor = 1;
    }

    public imbueFamily imbue = imbueFamily.GENERAL;

    public Arrow() {
        this(1);
    }

    public Arrow(int number) {
        super();
        quantity = number;
    }

}
