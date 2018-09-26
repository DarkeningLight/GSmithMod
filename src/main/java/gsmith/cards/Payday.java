package gsmith.cards;

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

/**
 * @version 0.1.2 26 Sep 2018
 *
 */
public class Payday extends CustomCard {

	public static final String ID = "Payday";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 3;
	
	private static final int GOLD_GAIN = 70;
	private static final int UPGRADE_PLUS_GOLD = 30;
	
	public static final String PATH = "cards/payday.png";
	
	public Payday() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		
		this.baseMagicNumber = this.magicNumber = GOLD_GAIN;
		this.isEthereal = true;
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_GOLD);
		}

	}

	@Override
	public AbstractCard makeCopy() {
		return new Payday();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new GainGoldAction(player, this.magicNumber));
	}
}
