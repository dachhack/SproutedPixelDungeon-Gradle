package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Amok;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Vertigo;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfAmok extends Spell {
    {
        name = "Spell of Amok";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_AMOK;
        magicType = magicFamily.STATUS;
    }

    @Override
    protected void onZap(int cell) {
        Char ch = Actor.findChar(cell);
        if (ch != null) {

            if (ch == Dungeon.hero) {
                Buff.affect(ch, Vertigo.class, Vertigo.duration(ch));
            } else {

                if(checkFam(curUser)){
                    Buff.affect(ch, Amok.class, 3f + curUser.magicLevel*2);
                } else {
                    Buff.affect(ch, Amok.class, 3f + curUser.magicLevel);
                }
            }

        } else {

            GLog.i("nothing happened");

        }
    }

    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.purpleLight(2, curUser.sprite.parent, curUser.pos, cell,
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
        return "AMOK.";
    }
}
