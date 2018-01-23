package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Poison;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfPoison extends Spell {
    {
        name = "Spell of Poison";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_POISON;
        magicType = magicFamily.STATUS;
    }


    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            int level = Math.round(curUser.magicLevel/5);
            int poisonbase = Math.round(curUser.magicLevel/5);

            if (checkFam(curUser)){ poisonbase *= (int) 4f;}

            Buff.affect(ch, Poison.class).set(
                    Poison.durationFactor(ch) * (poisonbase + level*2));

            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
                GLog.n("You killed yourself with your own Spell of Poison..");
            }
        }
    }


    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.poison(curUser.sprite.parent, curUser.pos, cell,
                callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public String desc() {
        return "This spell launches poisonous missles at you enemy.";

    }
    @Override
    public String info() {
        return "POISON.";
    }
}
