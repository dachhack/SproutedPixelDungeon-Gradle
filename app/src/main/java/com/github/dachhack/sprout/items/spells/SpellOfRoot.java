package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.effects.CellEmitter;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.effects.particles.EarthParticle;
import com.github.dachhack.sprout.plants.Earthroot;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfRoot extends Spell {
    {
        name = "Spell of Rooting";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_ROOT;
        minlevel = 1;
        magicType = magicFamily.STATUS;
        selfCast = true;
    }

    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            int level = Math.round(curUser.magicLevel/5);

            if (checkFam(curUser)){ level *= (int) 4f;}

            if (ch != null) {
                Buff.affect(ch, Earthroot.Armor.class).level = level;
            }

            if (Dungeon.visible[cell]) {
                CellEmitter.bottom(cell).start(EarthParticle.FACTORY, 0.05f, 8);
                Camera.main.shake(1, 0.4f);
            }
            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
                GLog.n("You killed yourself with your own Spell of Rooting..");
            }
        }
    }


    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.earth(1, curUser.sprite.parent, curUser.pos, cell,
                callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }

    @Override
    public String desc() {
        return "This spell creates immobile natural armor around its target.";

    }
    @Override
    public String info() {
        return "ROOTING.";
    }
}
