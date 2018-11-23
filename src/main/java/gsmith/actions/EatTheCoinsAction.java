package gsmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import gsmith.GSmithMod;

/**
 * @version 0.1.2 23 Nov 2018
 *
 */
public class EatTheCoinsAction extends AbstractGameAction {
	private final int GOLD_LOSS;
	private final int HP_GAIN;
	
	public EatTheCoinsAction (AbstractPlayer player, int goldLoss, int hpGain) {
		this.source = player;
		this.GOLD_LOSS = goldLoss;
		this.duration = Settings.ACTION_DUR_XFAST;
		this.HP_GAIN = hpGain;
	}
	
	@Override
	public void update() {
		if (this.duration == Settings.ACTION_DUR_XFAST) {
			this.source.loseGold(GOLD_LOSS);
			tickDuration();
			return;
		}
		
		if (GSmithMod.isBankrupt((AbstractPlayer)this.source)) {
			this.source.increaseMaxHp(this.HP_GAIN, true);
		}	
		
		this.isDone = true;
	}

}
