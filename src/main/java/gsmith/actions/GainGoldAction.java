package gsmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class GainGoldAction extends AbstractGameAction {

	private AbstractCreature player;
	
	private static int GOLD_GAIN;
	
	public GainGoldAction(AbstractCreature player, int goldGain) {
		super();
		
		this.player = player;
		GOLD_GAIN = goldGain;
	}
	
	@Override
	public void update() {
		player.gainGold(GOLD_GAIN);

	}

}
