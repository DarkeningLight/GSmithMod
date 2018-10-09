package gsmith.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import gsmith.GSmithMod;
import gsmith.actions.LoseGoldAction;

/**
 * @version 0.2.2 9 Oct 2018
 *
 */
public class DebtPower extends AbstractPower {
	
	public static final String PATH = "powers/debt.png";
	public static final String POWER_ID = "Debt";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String NAME = powerStrings.NAME;
	
	public int totalGold = 0;
	
	public DebtPower(AbstractPlayer owner) {
		super();
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.img = getTexture();
		
		this.amount = calculateGoldLoss();
		updateDescription();
		
	}  
	
	@Override
	public void atStartOfTurnPostDraw() {
		super.atStartOfTurn();
		this.amount = calculateGoldLoss();
		updateDescription();
	}
	
	

	@Override
	public void atEndOfTurn(boolean isPlayer) {
		super.atEndOfTurn(isPlayer);
		AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(this.owner, Math.min(this.owner.gold, this.amount)));
		totalGold += Math.min(this.owner.gold , this.amount);
	}
	
	

	@Override
	public void atEndOfRound() {
		super.atEndOfRound();
		
		if (totalGold  != 0) {
			AbstractDungeon.getCurrRoom().addStolenGoldToRewards(totalGold);
			totalGold = 0;
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
	
	public int calculateGoldLoss() {
		int total = GSmithMod.getActNumber() * 5;
		double mul = 0.2;
		 
		try {
			java.util.Random random = new java.util.Random();
			int r = random.nextInt((int) (owner.gold * mul));
		
			return (total + r);
		} 
		catch (IllegalArgumentException e) {
			return total;
		}
	}
	
	public static Texture getTexture() { return new Texture(GSmithMod.makePath(PATH)); }
}