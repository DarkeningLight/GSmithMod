package gsmith.powers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DivePower extends AbstractPower {
	
	public static final String POWER_ID = "Dive Power";
	  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Next Turn Block");
	  public static final String NAME = powerStrings.NAME;
	  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	  
	  public int length;
	  public static ArrayList<Integer> TURNS = new ArrayList<>(3);
	
	public DivePower(AbstractCreature owner, int armGain, int length) {
		super();
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = armGain;
		this.length = length;
		updateDescription();
		loadRegion("defenseNext");
		
		if (TURNS.isEmpty()) {
			TURNS.add(0);
			TURNS.add(0);
			TURNS.add(0);
		}
		
		for (int i = 0 ; i < length ; i++) {
			TURNS.set(i, (TURNS.get(i) + this.amount));
		}
	} 
	
	@Override
	public void atStartOfTurn() {
		
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, TURNS.get(0)));
		TURNS.remove(0);
		TURNS.add(0);
		
		
		if (TURNS.get(0) == 0) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
		}
		else {
			this.amount = TURNS.get(0);
			updateDescription();
		}
		
	}

	@Override
	public void updateDescription() {
		this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	}
}