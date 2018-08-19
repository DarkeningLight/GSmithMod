package gsmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import gsmith.cards.BiggerBag;

public class BiggerBagAction extends AbstractGameAction {
	
	public BiggerBagAction() {
		super();
	}

	@Override
	public void update() {
		BiggerBag.totalGoldGain = 1;
		BiggerBag.hasReset = false;
		
		this.isDone = true;
	}
}
