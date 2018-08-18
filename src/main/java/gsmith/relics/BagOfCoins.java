package gsmith.relics;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import gsmith.GSmithMod;

public  class BagOfCoins extends CustomRelic {
	public static final String ID = "Bag of Coins";
	
	public BagOfCoins() {
		super(ID, GSmithMod.getBagOfCoinsTexture(), AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.SOLID);
	}
	
	public String getUpdatedDescription() {
		return this.DESCRIPTIONS[0];
	}
	
	public int onAttacked(DamageInfo info, int damageAmount) {
		if (AbstractDungeon.player.gold >= damageAmount) {
			AbstractDungeon.player.loseGold(damageAmount);
			return 0;
		} 
		else {
			int damage = damageAmount - AbstractDungeon.player.gold;
			AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
			return damage;
		}
	}
	public AbstractRelic makeCopy() {
		return new BagOfCoins();
	}
}
