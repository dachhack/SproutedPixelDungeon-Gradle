package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Invisibility;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfInvisibility extends Spell {
    {
        name = "Spell of Invisibility";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_INVISIBLE;
        minlevel = 1;
        magicType = magicFamily.STATUS;
        selfCast = true;
    }

    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            int level = Math.round(curUser.magicLevel/10);

            if (checkFam(curUser)){ level *= (int) 2f;}

            if (ch != null) {
                Buff.affect(ch, Invisibility.class, Invisibility.DURATION*level);
                GLog.i("You see your hands turn invisible!");
                Sample.INSTANCE.play(Assets.SND_MELD);
            }

        }
    }

    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.whiteLight(1, curUser.sprite.parent, curUser.pos, cell,
                callback);
    }

    @Override
    public String desc() {
        return "This spell hides you from your enemy.";

    }
    @Override
    public String info() {
        return "INVISIBILITY.";
    }
}
