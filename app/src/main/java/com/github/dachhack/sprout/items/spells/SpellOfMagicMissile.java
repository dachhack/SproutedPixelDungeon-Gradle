package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.utils.Random;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfMagicMissile extends Spell {
    {
        name = "Spell of Magic Missile";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_MAGICMISSLE;
        magicType = magicFamily.ENERGY;
    }


    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            int level = Math.round(curUser.magicLevel/5);

            int damage= Random.NormalBtwn(level+2, 6 + level * 2);
            if (checkFam(curUser)){ damage *= (int) 4f;}
            ch.damage(damage, this);

            ch.sprite.burst(0xFF99CCFF, level / 2 + 2);

            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
                GLog.n("You killed yourself with your own Spell of Magic Missile...");
            }
        }
    }

    @Override
    public String desc() {
        return "This spell launches missiles of pure magical energy, dealing moderate damage to a target creature.";

    }
    @Override
    public String info() {
        return "MAGIC MISSILE.";
    }
}
