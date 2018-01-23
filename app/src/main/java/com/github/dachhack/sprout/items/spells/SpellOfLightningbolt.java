package com.github.dachhack.sprout.items.spells;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.effects.CellEmitter;
import com.github.dachhack.sprout.effects.Lightning;
import com.github.dachhack.sprout.effects.LightningLarge;
import com.github.dachhack.sprout.effects.particles.SparkParticle;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.levels.traps.LightningTrap;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by matthewporritt on 6/25/17.
 */

public class SpellOfLightningbolt extends Spell {
    {
        name = "Spell of Lightning Bolt";
        mp_cost = 1;
        image = ItemSpriteSheet.SPELL_LIT1;
        minlevel = 1;
        magicType = magicFamily.LIT;
    }

    private ArrayList<Char> affected = new ArrayList<Char>();

    private int[] points = new int[20];
    private int nPoints;

    private int dmgdiv=2;
    private float jmpchnce=0.5f;


    private void hit(Char ch, int damage) {

        if (damage < 1) {
            return;
        }

        if (ch == Dungeon.hero) {
            Camera.main.shake(2, 0.3f);
        }

        affected.add(ch);
        if (checkFam(curUser)){ damage *= (int) 4f;}
        ch.damage(Level.water[ch.pos] && !ch.flying ? damage * 2
                : damage, LightningTrap.LIGHTNING);

        ch.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
        ch.sprite.flash();

        points[nPoints++] = ch.pos;

        HashSet<Char> ns = new HashSet<Char>();
        for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
            Char n = Actor.findChar(ch.pos + Level.NEIGHBOURS8[i]);
            if (n != null && !affected.contains(n) && Random.Float()<jmpchnce) {
                ns.add(n);
            }
        }

        if (ns.size() > 0) {
            hit(Random.element(ns), Random.Int(damage / dmgdiv, damage));
        }
    }

    @Override
    protected void onZap(int cell) {

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
                GLog.n("You killed yourself with your own Spell of Lightning Bolt...");
            }
        }
    }
    @Override
    protected void fx(int cell, Callback callback) {

        nPoints = 0;
        points[nPoints++] = Dungeon.hero.pos;

        Char ch = Actor.findChar(cell);
        if (ch != null) {

            affected.clear();
            int lvl = Math.round(curUser.magicLevel/5);
            hit(ch, Random.Int(lvl / 2, lvl));

        } else {

            points[nPoints++] = cell;
            CellEmitter.center(cell).burst(SparkParticle.FACTORY, 3);

        }
        if(Random.Int(10)<5){
            curUser.sprite.parent.add(new Lightning(points, nPoints, callback));
        } else {
            curUser.sprite.parent.add(new LightningLarge(points, nPoints, callback));
        }
    }
    @Override
    public String desc() {
        return "This spell shoots a bolt of lightning at your foes.";

    }
    @Override
    public String info() {
        return "LITBOLT.";
    }
}
