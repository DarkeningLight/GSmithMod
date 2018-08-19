package gsmith.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import gsmith.GSmithMod;
import gsmith.actions.GainGoldAction;

public class SleightOfHandPower extends AbstractPower {
	
	public static final String PATH = "powers/sleight_of_hand.png";
	public static final String POWER_ID = "Sleight of Hand";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String NAME = powerStrings.NAME;
	
	public SleightOfHandPower(AbstractCreature owner, int goldGain) {
		super();
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = goldGain;
		updateDescription();
		this.img = getTexture();
		
		System.out.println("<Init> Amount: " + this.amount);
	}  

	@Override
	public void onUseCard(AbstractCard card, UseCardAction action) {
		System.out.println("Amount: " + this.amount);
		if (card.type == AbstractCard.CardType.SKILL) {
			AbstractDungeon.actionManager.addToBottom(new GainGoldAction(this.owner, this.amount));
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