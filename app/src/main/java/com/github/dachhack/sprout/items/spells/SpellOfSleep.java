package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.DrowsySpell;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfSleep extends Spell {
    {
        name = "Spell of Sleep";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_SLEEP;
        minlevel = 1;
        magicType = magicFamily.STATUS;
    }


    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            Sample.INSTANCE.play(Assets.SND_LULLABY);

            Buff.affect(ch, DrowsySpell.class);
            ch.sprite.centerEmitter().start(Speck.factory(Speck.NOTE),0.3f, 5);

            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
                GLog.n("You killed yourself with your own Spell of Sleep..");
            }
        }
    }


    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.wool(1,curUser.sprite.parent, curUser.pos, cell, callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public String desc() {
        return "This spell attempts to put an enemy to sleep.";

    }
    @Override
    public String info() {
        return "POISON.";
    }
}
