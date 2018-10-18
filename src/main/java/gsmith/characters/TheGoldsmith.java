package gsmith.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake; 

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import gsmith.GSmithMod;
import gsmith.cards.BagSlam;
import gsmith.patches.TheGoldsmithEnum;

public class TheGoldsmith extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 3;
	public static final String NAME = "The Goldsmith";
	public static final String DESCRIPTION = "The latest soul to wear the hat of the goldsmiths. NL Draws power from the spire's gold.";
	
    public static final String GOLDSMITH_SHOULDER_1 = "char/goldsmith/shoulder.png";
    public static final String GOLDSMITH_SHOULDER_2 = "char/goldsmith/shoulder2.png";
    public static final String GOLDSMITH_CORPSE = "char/goldsmith/corpse.png";
    public static final String GOLDSMITH_SKELETON_ATLAS = "char/goldsmith/skeleton.atlas";
    public static final String GOLDSMITH_SKELETON_JSON = "char/goldsmith/skeleton.json";
	
	public static int turnTracker = 0;
	
	public static final String[] orbTextures = {
			"img/char/goldsmith/orb/goldsmith_layer1.png",
			"img/char/goldsmith/orb/goldsmith_layer2.png",
			"img/char/goldsmith/orb/goldsmith_layer3.png",
			"img/char/goldsmith/orb/goldsmith_layer4.png",
			"img/char/goldsmith/orb/goldsmith_layer5.png",
			"img/char/goldsmith/orb/goldsmith_layer6.png",
			"img/char/goldsmith/orb/goldsmith_layer1d.png",
			"img/char/goldsmith/orb/goldsmith_layer2d.png",
			"img/char/goldsmith/orb/goldsmith_layer3d.png",
			"img/char/goldsmith/orb/goldsmith_layer4d.png",
			"img/char/goldsmith/orb/goldsmith_layer5d.png",
	};
	
	public TheGoldsmith(String name) {
		super(name, TheGoldsmithEnum.THE_GOLDSMITH, orbTextures, "img/char/goldsmith/orb/energyGoldVFX.png", 
				new float[] {1,1,1,1,1,1,1,1,1,1,1,1}, 
				new SpriterAnimation(GSmithMod.makePath("char/goldsmith/goldsmith.scml")));
		
		initializeClass(null, GSmithMod.makePath(GOLDSMITH_SHOULDER_1),
				GSmithMod.makePath(GOLDSMITH_SHOULDER_2),
				GSmithMod.makePath(GOLDSMITH_CORPSE),
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
	}
	
	@Override
	public void applyEndOfTurnTriggers() {
		for (AbstractPower p : this.powers) {
			p.atEndOfTurn(true);
		}
		turnTracker++;
	}

	@Override
	public void onVictory() {
		if (!this.isDying) {
			for (AbstractRelic r : this.relics) {
				r.onVictory();
			}
		}
		turnTracker = 0;
		this.damagedThisCombat = 0;
	}

	@Override
	public void applyStartOfTurnPostDrawPowers() {
		for (AbstractPower p : this.powers) {
			p.atStartOfTurnPostDraw();
		}
	}
	
	@Override
	public void applyStartOfCombatLogic() {
		for (AbstractRelic r : this.relics) {
			if (r == null) continue;
			r.atBattleStart();
		}
	}
	
	public ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Strike_D");
		retVal.add("Strike_D");
		retVal.add("Strike_D");
		retVal.add("Strike_D");
		retVal.add("Bag Slam");
		retVal.add("Thief");
		retVal.add("Spare Change");
		retVal.add("Spare Change");
		retVal.add("Spare Change");
		retVal.add("Spare Change");
		retVal.add("Spare Change");
		retVal.add("Spare Change");
		return retVal;
	}
	
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Bag of Coins");
		UnlockTracker.markRelicAsSeen("Bag of Coins");
		return retVal;
	}

	@Override
	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playA("GOLD_JINGLE", MathUtils.random(-0.2F, 0.2F));
	    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
		
	}

	@Override
	public int getAscensionMaxHPLoss() {
		return 3;
	}

	@Override
	public Color getCardColor() {
		return CardHelper.getColor(210.0f, 187.0f, 26.0f);
	}

	@Override
	public Color getCardTrailColor() {
		return CardHelper.getColor(210.0f, 187.0f, 26.0f);
	}

	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "GOLD_JINGLE";
	}

	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontRed;
	}

	@Override
	public CharSelectInfo getLoadout() {
		return new CharSelectInfo(NAME, DESCRIPTION, 30, 30, 0, 149, 5, this, 
				getStartingRelics(), getStartingDeck(), false);
	}

	@Override
	public String getLocalizedCharacterName() {
		return NAME;
	}

	@Override
	public AbstractCard getStartCardForEvent() {
		return new BagSlam();
	}

	@Override
	public String getTitle(PlayerClass arg0) {
		return NAME;	
	}

	@Override
	public AbstractPlayer newInstance() {
		return new TheGoldsmith(NAME);
	}
	
	
}
