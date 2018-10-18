package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;
import gsmith.powers.SleepingCashPower;

/**
 * @version 0.1.2 18 Oct 2018
 *
 */
public class SleepingCash extends CustomCard {
	
	public static final String ID = "Sleeping Cash";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	
	private static final int DMG_AMT = 1;
	private static final int UPGRADE_PLUS_DMG = 1;
	
	public static final String PATH = "cards/sleeping_cash.png";
	
	public SleepingCash() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.POWER, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = DMG_AMT;
		this.isMultiDamage = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new SleepingCash();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_DMG);
			
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
				new SleepingCashPower(player, this.magicNumber, this) , this.magicNumber));
	}
}
