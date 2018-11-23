package gsmith.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import gsmith.GSmithMod;

/**
 * @version 0.1.0 23 Nov 2018
 *
 */
public  class Credit extends CustomRelic {
	public static final String ID = "Credit";
	
	public static final String PATH = "relics/credit.png";
	
	public Credit() {
		super(ID, getTexture(), AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return this.DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new Credit();
	}
	
	public static Texture getTexture() { 
		return new Texture(GSmithMod.makePath(PATH)); 
		}
}
