package gsmith.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import gsmith.GSmithMod;

/**
 * @version 0.1.0 23 Nov 2018
 *
 */
public  class FoolsGold extends CustomRelic {
	public static final String ID = "Fools Gold";
	
	public static final String PATH = "relics/fools_gold.png";
	
	public FoolsGold() {
		super(ID, getTexture(), AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.HEAVY);
	}
	
	public String getUpdatedDescription() {
		return this.DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new FoolsGold();
	}
	
	public static Texture getTexture() { 
		return new Texture(GSmithMod.makePath(PATH)); 
		}
}
