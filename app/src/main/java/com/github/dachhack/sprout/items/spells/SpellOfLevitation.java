package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Levitation;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfLevitation extends Spell {
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

                if (Dungeon.depth<51) {
                    Buff.affect(ch, Levitation.class, Levitation.DURATION * level);
                    GLog.i("You float into the air!");
                }
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
        return "This spell summons magical energy to float you in midair.";

    }
    @Override
    public String info() {
        return "LEVITATION.";
    }
}
