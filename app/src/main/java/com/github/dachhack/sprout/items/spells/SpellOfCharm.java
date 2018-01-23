package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Charm;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfCharm extends Spell {
    {
        name = "Spell of Charm";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_CHARM;
        magicType = magicFamily.STATUS;
    }


    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            int level = Math.round(curUser.magicLevel/5);

            if (checkFam(curUser)){ level *= (int) 4f;}

            Buff.affect(ch, Charm.class, Charm.durationFactor(ch) * level).object = curUser.id();
            ch.sprite.centerEmitter().start(Speck.factory(Speck.HEART), 0.2f, 5);

        }
    }


    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.purpleLight(1,curUser.sprite.parent, curUser.pos, cell,
                callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public String desc() {
        return "The target of this spell becomes charmed.";

    }
    @Override
    public String info() {
        return "CHARM.";
    }
}
