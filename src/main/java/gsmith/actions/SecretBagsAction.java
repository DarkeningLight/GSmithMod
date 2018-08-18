package gsmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SecretBagsAction extends AbstractGameAction {

	private int energyOnUse = -1;
	private AbstractPlayer p;
	private int goldGain;
	private boolean freeToPlayOnce;
	
	public SecretBagsAction(AbstractPlayer p, int energyOnUse, int goldGain, boolean freeToPlayOnce) {
		super();
		this.energyOnUse = energyOnUse;
		this.p = p;
		this.goldGain = goldGain;
		this.freeToPlayOnce = freeToPlayOnce;
	}

	@Override
	public void update() {
		int effect = EnergyPanel.totalCount;
		if (this.energyOnUse != -1) {
			effect = this.energyOnUse;
		}
		if (this.p.hasRelic("Chemical X")) {
			effect += 2;
			this.p.getRelic("Checmical X").flash();
		}
		if (effect > 0) {
			AbstractDungeon.actionManager.addToBottom(new GainGoldAction(p, goldGain));
			
			if (!this.freeToPlayOnce) {
		        this.p.energy.use(EnergyPanel.totalCount);
		    }
		this.isDone = true;	
		}
	}

}
