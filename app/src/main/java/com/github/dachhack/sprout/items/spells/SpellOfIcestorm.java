package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.blobs.Fire;
import com.github.dachhack.sprout.actors.blobs.Freezing;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.mechanics.Ballistica;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfIcestorm extends Spell {
    {
        name = "Spell of Icestorm";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_ICE3;
        minlevel = 1;
        magicType = magicFamily.COLD;
    }

    @Override
    protected void onZap(int cell) {

        int level = Math.round(curUser.magicLevel/5);

        Fire fire = (Fire) Dungeon.level.blobs.get(Fire.class);

        for (int i = 1; i < Ballistica.distance - 1; i++) {
            int c = Ballistica.trace[i];
            Freezing.affect(i, fire);
        }

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            int damage= Random.Int(level*level, level * level * 10);
            if (checkFam(curUser)){ damage *= (int) 4f;}
            ch.damage(damage, this);

            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
                GLog.n("You killed yourself with your own Spell of Icestorm...");
            }
        }
    }
    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.coldLight(6, curUser.sprite.parent, curUser.pos, cell, callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
    }
    @Override
    public String desc() {
        return "This spell rips apart your foes with a powerful fury of ice.";

    }
    @Override
    public String info() {
        return "ICESTORM.";
    }
}
