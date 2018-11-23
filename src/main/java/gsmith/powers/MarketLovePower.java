package gsmith.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import gsmith.GSmithMod;

/**
 * @version 0.1.2 23 Nov 2018
 *
 */
public class MarketLovePower extends AbstractPower {
	
	public static final String PATH = "powers/market_love.png";
	public static final String POWER_ID = "Market Love";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String NAME = powerStrings.NAME;
	
	
	
	public MarketLovePower(AbstractPlayer owner) {
		super();
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = 6;
		updateDescription();
		this.img = getTexture();
	}  

	@Override
	public void atStartOfTurn() {
		super.atStartOfTurn();
		
		if (GSmithMod.isBankrupt((AbstractPlayer)owner)) {
			AbstractDungeon.actionManager.addToBottom(new HealAction(owner,owner, this.amount));
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