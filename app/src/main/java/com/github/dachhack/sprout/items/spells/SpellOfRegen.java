package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.BerryRegeneration;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfRegen extends Spell {
    {
        name = "Spell of Regen";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_REGEN;
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
                    Buff.affect(ch, BerryRegeneration.class).level(ch.HT);
                } else {
                    Buff.affect(ch, BerryRegeneration.class).level(ch.HT / 2);
                }
            }


        }
    }


    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.whiteLight(curUser.sprite.parent, curUser.pos, cell,
                callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public String desc() {
        return "This spell regenerates your hit points.";

    }
    @Override
    public String info() {
        return "REGEN.";
    }
}
