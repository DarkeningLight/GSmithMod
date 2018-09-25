package gsmith.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.actions.LoseGoldAction;
import gsmith.patches.AbstractCardEnum;

public class LuckyFind extends CustomCard {

	public static final String ID = "Lucky Find";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 3;
	
	private static final int GOLD_LOSS = 100;
	
	public static final String PATH = "cards/lucky_find.png";
	
	public LuckyFind() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		
		this.exhaust = true;
	}

	@Override
	public boolean canUpgrade() {
		return false;
	}

	@Override
	public void upgrade() {
		// Can't upgrade. 
	}

	@Override
	public AbstractCard makeCopy() {
		return new LuckyFind();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		if (player.gold >= GSmithMod.PROSPEROUS) {
			AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(player, GOLD_LOSS));
			AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
				
				@Override
				public void update() {
				
					AbstractRelic r = AbstractDungeon.returnRandomRelic(AbstractDungeon.returnRandomRelicTier());
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(r));
					this.isDone = true;
				}
			});
			
		}
		
	}
}
