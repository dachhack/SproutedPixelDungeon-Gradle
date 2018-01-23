package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.effects.SpellSprite;
import com.github.dachhack.sprout.effects.particles.EnergyParticle;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfRecharge extends Spell {
    {
        name = "Spell of Recharge";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_RECHARGE;
        minlevel = 1;
        magicType = magicFamily.STATUS;
        selfCast = true;
    }

    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            if (ch != null) {

                int count = curUser.belongings.charge(true);
                ch.sprite.centerEmitter().burst(EnergyParticle.FACTORY, 15);

                if (count > 0) {
                    GLog.i("a surge of energy courses through your pack, recharging your wand"
                            + (count > 1 ? "s" : ""));
                    SpellSprite.show(curUser, SpellSprite.CHARGE);
                } else {
                    GLog.i("a surge of energy courses through your pack, but nothing happens");
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
        return "This spell recharges your wands.";

    }
    @Override
    public String info() {
        return "RECHARGE.";
    }
}
