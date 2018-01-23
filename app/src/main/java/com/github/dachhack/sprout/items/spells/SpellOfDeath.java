package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Terror;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.effects.particles.ShadowParticle;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfDeath extends Spell {
    {
        name = "Spell of Death";
        mp_cost = 1;
        minlevel = 1;
        image = ItemSpriteSheet.SPELL_DEATH;
        magicType = magicFamily.STATUS;
    }


    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            int level = Math.round(curUser.magicLevel/5);

            if (checkFam(curUser)){ level *= (int) 4f;}

            if (Random.Int(level+200)<level){
                ch.damage(ch.HP, this);
                ch.sprite.emitter().burst(ShadowParticle.UP, 5);
            } else {
                Buff.affect(ch, Terror.class, Terror.DURATION * level).object = curUser.id();
            }
        }
    }


    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.shadow(curUser.sprite.parent, curUser.pos, cell,
                callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public String desc() {
        return "The target of this spell dies or flees in terror.";

    }
    @Override
    public String info() {
        return "DEATH.";
    }
}
