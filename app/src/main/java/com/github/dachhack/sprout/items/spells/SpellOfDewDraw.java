package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Dewcharge;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfDewDraw extends Spell {
    {
        name = "Spell of Dew Draw";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_DEW;
        minlevel = 1;
        magicType = magicFamily.STATUS;
        selfCast = true;
    }

    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            int level = Math.round(curUser.magicLevel/5);

            if (checkFam(curUser)){ level *= (int) 4f;}

            if (ch != null) {
                Buff.prolong(ch, Dewcharge.class, Dewcharge.DURATION*level);
            }


        }
    }


    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.blueLight(curUser.sprite.parent, curUser.pos, cell,
                callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public String desc() {
        return "This spell charges the dew in the dungeon.";

    }
    @Override
    public String info() {
        return "DEWDRAW.";
    }
}
