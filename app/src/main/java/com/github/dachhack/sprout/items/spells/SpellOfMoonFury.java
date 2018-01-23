package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.FullMoonStrength;
import com.github.dachhack.sprout.actors.buffs.Strength;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfMoonFury extends Spell {
    {
        name = "Spell of Moon Fury";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_MOONFURY;
        minlevel = 1;
        magicType = magicFamily.STATUS;
        selfCast = true;
    }

    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            if (ch != null) {
                if(checkFam(curUser)) {
                    Buff.affect(ch, FullMoonStrength.class);
                } else {
                    Buff.affect(ch, Strength.class);
                }
            }
        }
    }


    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.whiteLight(1, curUser.sprite.parent, curUser.pos, cell,
                callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public String desc() {
        return "This spell fills you with rage for the coming battle.";

    }
    @Override
    public String info() {
        return "MOONFURY.";
    }
}
