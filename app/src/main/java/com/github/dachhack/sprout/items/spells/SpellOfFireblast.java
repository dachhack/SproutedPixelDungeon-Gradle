package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.blobs.Blob;
import com.github.dachhack.sprout.actors.blobs.Fire;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Burning;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.effects.particles.FlameParticle;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.mechanics.Ballistica;
import com.github.dachhack.sprout.scenes.GameScene;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfFireblast extends Spell {
    {
        name = "Spell of Fireblast";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_FIRE2;
        minlevel = 1;
        magicType = magicFamily.FIRE;
    }

    @Override
    protected void onZap(int cell) {

        int level = Math.round(curUser.magicLevel/3);

        for (int i = 1; i < Ballistica.distance - 1; i++) {
            int c = Ballistica.trace[i];
            if (Level.flamable[c]) {
                GameScene.add(Blob.seed(c, 1, Fire.class));
            }
        }

        GameScene.add(Blob.seed(cell, 1, Fire.class));

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            int damage= Random.Int(level*2, level * level+3);
            if (checkFam(curUser)){ damage *= (int) 4f;}
            ch.damage(damage, this);

            Buff.affect(ch, Burning.class).reignite(ch);

            ch.sprite.emitter().burst(FlameParticle.FACTORY, 5);

            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
                GLog.n("You killed yourself with your own Spell of Firebolt...");
            }
        }
    }
    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.fire(3, curUser.sprite.parent, curUser.pos, cell, callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }
    @Override
    public String desc() {
        return "This spell unleashes bursts of magical fire. It will ignite "
                + "flammable terrain, and will damage and burn a creature it hits.";

    }
    @Override
    public String info() {
        return "FIREBLAST.";
    }
}
