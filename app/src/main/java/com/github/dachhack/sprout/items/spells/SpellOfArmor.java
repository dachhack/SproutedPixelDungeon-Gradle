package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Barkskin;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfArmor extends Spell {
    {
        name = "Spell of Armor";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_ARMOR;
        minlevel = 1;
        magicType = magicFamily.STATUS;
        selfCast = true;
    }

    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            int level = Math.round(curUser.magicLevel/2);

            if (checkFam(curUser)){ level *= (int) 2f;}

            if (ch != null) {
                Buff.affect(ch, Barkskin.class).level(level);
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
        return "This spell creates a strong temporary armor around its target.";

    }
    @Override
    public String info() {
        return "ARMOR.";
    }
}
