package gsmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * @version 0.1.0 2 Oct 2018
 *
 */
public class DiscountAction extends AbstractGameAction {
	
	
	public DiscountAction() {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
	}
	
	
	@Override
	public void update() {
		AbstractPlayer p = AbstractDungeon.player;
		
		if (this.duration == Settings.ACTION_DUR_FAST) { 
			if (p.hand.isEmpty()) { //Empty Hand
				this.isDone = true;
				return;
			}
			
			if (p.hand.size() == 1) { //1 Card in Hand
				AbstractCard c = p.hand.getTopCard();
				c.modifyCostForTurn(-99);
				p.hand.refreshHandLayout();
				this.isDone = true;
				return;
			}
			
			AbstractDungeon.handCardSelectScreen.open("Discount", 1, false, true); //Other
			tickDuration();
			return;
		}
		
		if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
				c.modifyCostForTurn(-99);
				AbstractDungeon.player.hand.addToHand(c);
			}
			
			AbstractDungeon.player.hand.refreshHandLayout();
			AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
			AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
			this.isDone = true;
		}
		tickDuration();
	}
}
