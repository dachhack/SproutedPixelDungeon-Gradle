/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.github.dachhack.sprout.actors.buffs;

import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.items.spells.Spell;
import com.github.dachhack.sprout.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class MagicSurge extends FlavourBuff {

	public static final float DURATION = 25f;

	@Override
	public int icon() {
		return BuffIndicator.MAGICSURGE;
	}

	public static String flavor;

	private static final String FLAVOR = "flavor";

	public void setFlavor(String flv){
		this.flavor=flv;
	}

	public Spell.magicFamily buffFlavor () {

		for (Spell.magicFamily c : Spell.magicFamily.values()) {
			if (c.name().equals(flavor)) {
				return c;
			}
		}
		return Spell.magicFamily.GENERAL;
	}

	public static <T extends MagicSurge> T affect(Char target,
	    Class<T> buffClass, float duration, String flv) {
		T buff = affect(target, buffClass);
		buff.setFlavor(flv);
		buff.spend(duration);
		return buff;
	}


	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(FLAVOR, flavor);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		flavor = bundle.getString(FLAVOR);
	}

}
