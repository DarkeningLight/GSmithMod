package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.actions.GainGoldAction;
import gsmith.patches.AbstractCardEnum;

public class GreedIsGood extends CustomCard {
	
	public static final String ID = "Greed is Good";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	
	private static final int GOLD_GAIN = 5;
	private static final int CARD_DRAW = 1;
	
	public static final String PATH = "cards/greed_is_good.png";
	
	public GreedIsGood() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = GOLD_GAIN;
	}

	@Override
	public AbstractCard makeCopy() {
		return new GreedIsGood();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADED_COST);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new GainGoldAction(player, 
				(getAliveMonsters() * this.magicNumber)));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, CARD_DRAW));
	}
	
	public int getAliveMonsters() {
		int alive = 0;
		
		for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters ) {
			if (!m.isDead) {
				alive++;
			}
		}
		
		return alive;
	}
}
