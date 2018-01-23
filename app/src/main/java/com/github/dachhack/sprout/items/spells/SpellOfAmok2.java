package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Amok;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Vertigo;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfAmok2 extends Spell {
    {
        name = "Spell of Greater Amok";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_AMOK2;
        minlevel = 1;
        magicType = magicFamily.STATUS;
    }

    @Override
    protected void onZap(int cell) {

        boolean chAffected = false;

        for (int n : Level.NEIGHBOURS9) {
            int c = cell + n;
            if (c >= 0 && c < Level.getLength()) {

                Char ch = Actor.findChar(c);
                if (ch != null) {

                    chAffected = true;

                    // those not at the center of the blast take damage less
                    // consistently.
                    int minChance = c == cell ? curUser.magicLevel:Math.round(curUser.magicLevel/4);
                    int maxChance = curUser.magicLevel;

                    int chance = Random.NormalIntRange(minChance, maxChance);

                    if (checkFam(curUser)){
                        chance = maxChance*2;
                    }

                    if (ch == Dungeon.hero) {
                        Buff.affect(ch, Vertigo.class, Vertigo.duration(ch));
                    } else {
                        Buff.affect(ch, Amok.class, 3f + chance);
                    }

                    if (ch == Dungeon.hero && !ch.isAlive())
                        // constant is used here in the rare instance a player
                        // is killed by a double bomb.
                        Dungeon.fail(Utils.format(ResultDescriptions.ITEM,"spell of greater amok"));
                }
            }
        }

        if(!chAffected) {

            GLog.i("nothing happened");

        }
    }

    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.purpleLight(4, curUser.sprite.parent, curUser.pos, cell,
                callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public String desc() {
        return "The purple light from this spell will make the target run amok "
                + "attacking random creatures in its vicinity.";

    }
    @Override
    public String info() {
        return "GREATER AMOK.";
    }
}
