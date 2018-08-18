package gsmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class LoseGoldAction extends AbstractGameAction {

	private AbstractCreature player;
	
	private static int GOLD_LOSS;
	
	public LoseGoldAction(AbstractCreature player, int goldLoss) {
		super();
		
		this.player = player;
		GOLD_LOSS = goldLoss;
	}
	
	@Override
	public void update() {
		player.loseGold(GOLD_LOSS);
		this.isDone = true;

	}

}
