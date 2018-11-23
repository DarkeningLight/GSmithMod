package gsmith.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import gsmith.GSmithMod;

/**
 * @version 0.1.2 23 Nov 2018
 *
 */
public class GoldBarPower extends AbstractPower {
	
	public static final String PATH = "powers/gold_bar.png";
	public static final String POWER_ID = "Gold Bar";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String NAME = powerStrings.NAME;
	
	
	public GoldBarPower(AbstractPlayer owner, int powerGain) {
		super();
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.img = getTexture();
		
		this.amount = powerGain;
		updateDescription();
		
	}  
	@Override
	public void atStartOfTurn() {
		super.atStartOfTurn();
		
		if (GSmithMod.isBankrupt((AbstractPlayer)owner)) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, 
					new StrengthPower(owner, this.amount), this.amount));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, 
					new DexterityPower(owner, this.amount), this.amount));
		}
	}

	@Override
	public void updateDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append(DESCRIPTIONS[0]);
		sb.append("#b" + amount);
		sb.append(DESCRIPTIONS[1]);
		sb.append("#b" + amount);
		sb.append(DESCRIPTIONS[2]);
		
		this.description = sb.toString();
	}
	
	public static Texture getTexture() { return new Texture(GSmithMod.makePath(PATH)); }
}