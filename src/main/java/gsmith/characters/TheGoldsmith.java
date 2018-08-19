package gsmith.characters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import gsmith.GSmithMod;
import gsmith.patches.TheGoldsmithEnum;

public class TheGoldsmith extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 3;
	
    public static final String GOLDSMITH_SHOULDER_1 = "char/goldsmith/shoulder.png";
    public static final String GOLDSMITH_SHOULDER_2 = "char/goldsmith/shoulder2.png";
    public static final String GOLDSMITH_CORPSE = "char/goldsmith/corpse.png";
    public static final String GOLDSMITH_SKELETON_ATLAS = "char/goldsmith/skeleton.atlas";
    public static final String GOLDSMITH_SKELETON_JSON = "char/goldsmith/skeleton.json";
	
	public static int turnTracker = 0;
	public static final float[] orbRotations = {
			0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
			0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
	};
	
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
	
	public TheGoldsmith(String name, PlayerClass setClass) {
		super(name, setClass, orbTextures, "img/char/goldsmith/orb/energyGoldVFX.png", 
				new float[] {1,1,1,1,1,1,1,1,1,1,1,1}, 
				new SpriterAnimation(GSmithMod.makePath("char/goldsmith/goldsmith.scml")));
		
		initializeClass(null, GSmithMod.makePath(GOLDSMITH_SHOULDER_2),
				GSmithMod.makePath(GOLDSMITH_SHOULDER_1),
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
	
	public static ArrayList<String> getStartingDeck() {
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
	
	public static ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Bag of Coins");
		UnlockTracker.markRelicAsSeen("Bag of Coins");
		return retVal;
	}
	
	public static CharSelectInfo getLoadout() {
		return new CharSelectInfo("The Goldsmith", "Blah Blah Blah. Gold. Blah Blah",
				30, 30, 0, 149, 5,
			TheGoldsmithEnum.THE_GOLDSMITH, getStartingRelics(), getStartingDeck(), false);
	}
}
