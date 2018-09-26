package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;

/**
 * @version 0.1.2 26 Sep 2018
 *
 */
public class CoinTrick extends CustomCard {
	
	public static final String ID = "Coin Trick";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;
	
	private static final int CARD_DRAW = 3;
	
	public static final String PATH = "cards/coin_trick.png";
	
	public CoinTrick() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = CARD_DRAW;
		this.isEthereal = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new CoinTrick();
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
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(player, player, 1, false));
	}
}
