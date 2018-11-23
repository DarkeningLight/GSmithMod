package gsmith.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import gsmith.GSmithMod;

public class GoldFuPower extends AbstractPower {
	
	public static final String PATH = "powers/gold_fu.png";
	public static final String POWER_ID = "Gold Fu";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String NAME = powerStrings.NAME;
	
	public GoldFuPower(AbstractPlayer owner, int cardDraw) {
		super();
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = cardDraw;
		updateDescription();
		this.img = getTexture();
	}  
	
	
	
	@Override
	public void atStartOfTurn() {
		super.atStartOfTurn();
		
		if (GSmithMod.isProsperous((AbstractPlayer)owner)) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
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