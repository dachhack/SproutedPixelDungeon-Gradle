package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Invisibility;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.mechanics.Ballistica;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Callback;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfBlink extends Spell {
    {
        name = "Spell of Blink";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_BLINK;
        usesTargeting = false;
        magicType = magicFamily.GENERAL;
    }

    protected void onZap(int cell) {
        final String TXT_PREVENTING = "Something scrambles the blink magic! ";
        if (Dungeon.sokobanLevel(Dungeon.depth)){
            GLog.w(TXT_PREVENTING);
            Invisibility.dispel();
            return;
        }

        int level = Math.round(curUser.magicLevel/5);

        if (Ballistica.distance > level + 4) {
            cell = Ballistica.trace[level + 3];
        } else if (Actor.findChar(cell) != null && Ballistica.distance > 1) {
            cell = Ballistica.trace[Ballistica.distance - 2];
        }

        curUser.sprite.visible = true;
        appear(Dungeon.hero, cell);
        Dungeon.observe();
    }


    @Override
    protected void fx(int cell, Callback callback) {
        MagicMissile.whiteLight(curUser.sprite.parent, curUser.pos, cell,
                callback);
        Sample.INSTANCE.play(Assets.SND_ZAP);
        if (!Dungeon.sokobanLevel(Dungeon.depth)){
            curUser.sprite.visible = false;
        }
    }

    public static void appear(Char ch, int pos) {

        ch.sprite.interruptMotion();

        ch.move(pos);
        ch.sprite.place(pos);

        if (ch.invisible == 0) {
            ch.sprite.alpha(0);
            ch.sprite.parent.add(new AlphaTweener(ch.sprite, 1, 0.4f));
        }

        ch.sprite.emitter().start(Speck.factory(Speck.LIGHT), 0.2f, 3);
        Sample.INSTANCE.play(Assets.SND_TELEPORT);
    }


    @Override
    public String desc() {
        return "This spell will allow you to teleport in the chosen direction. "
                + "Creatures and inanimate obstructions will block the teleportation.";

    }
    @Override
    public String info() {
        return "BLINK.";
    }
}
