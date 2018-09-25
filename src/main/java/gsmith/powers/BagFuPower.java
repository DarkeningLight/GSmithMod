package gsmith.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import gsmith.GSmithMod;

public class BagFuPower extends AbstractPower {
	
	public static final String PATH = "powers/bag_fu.png";
	public static final String POWER_ID = "Bag Fu";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String NAME = powerStrings.NAME;
	
	public BagFuPower(AbstractCreature owner, int thornsGain) {
		super();
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = thornsGain;
		updateDescription();
		this.img = getTexture();
	}  
	
	
	
	@Override
	public void atStartOfTurn() {
		super.atStartOfTurn();
		
		if (this.owner.gold >= GSmithMod.PROSPEROUS) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, 
					new ThornsPower(this.owner, this.amount), this.amount));
		}
	}



	@Override
	public void updateDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append(DESCRIPTIONS[0]);
		sb.append("#b" + amount);
		sb.append(DESCRIPTIONS[1]);
		
		this.description = sb.toString();
	}

	public static Texture getTexture() { return new Texture(GSmithMod.makePath(PATH)); }
}