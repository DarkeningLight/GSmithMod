package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.actions.BiggerBagAction;
import gsmith.actions.GainGoldAction;
import gsmith.patches.AbstractCardEnum;

public class BiggerBag extends CustomCard {

	public static final String ID = "Bigger Bag";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	
	private static int increaseAmt = 1;
	public static int totalGoldGain = 1;
	public static boolean hasReset = false;
	
	public static final String PATH = "cards/bigger_bag.png";
	
	public BiggerBag() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = totalGoldGain;
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			increaseAmt += 1;
			
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new BiggerBag();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		System.out.println("Magic Number:" + this.magicNumber);
		System.out.println("Total Gold Gain:" + totalGoldGain);
		
		if (!hasReset) {
			AbstractDungeon.actionManager.addToNextCombat(new BiggerBagAction());
		}
		
		this.magicNumber = totalGoldGain;
		initializeDescription();
		System.out.println("Magic Number 2:" + this.magicNumber);
		
		AbstractDungeon.actionManager.addToBottom(new GainGoldAction(player, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(), 1));
		totalGoldGain += increaseAmt;

	}

}
