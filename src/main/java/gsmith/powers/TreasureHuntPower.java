package gsmith.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import gsmith.GSmithMod;

public class TreasureHuntPower extends AbstractPower {
	
	public static final String PATH = "powers/treasure_hunt.png";
	public static final String POWER_ID = "Treasure Hunt";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String NAME = powerStrings.NAME;
	
	
	public TreasureHuntPower(AbstractPlayer owner, int strLose) {
		super();
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.img = getTexture();
		
		this.amount = -strLose;
		updateDescription();
		
	}  
	@Override
	public void atStartOfTurn() {
		super.atStartOfTurn();
		
		if (owner.gold <= GSmithMod.BANKRUPT) {
			for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
				if (!m.isDeadOrEscaped()) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, owner, 
							new StrengthPower(m, this.amount), this.amount));
				}
			}
		}
	}

	@Override
	public void updateDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append(DESCRIPTIONS[0]);
		sb.append("#b" + -amount);
		sb.append(DESCRIPTIONS[1]);
		
		this.description = sb.toString();
	}
	
	public static Texture getTexture() { return new Texture(GSmithMod.makePath(PATH)); }
}