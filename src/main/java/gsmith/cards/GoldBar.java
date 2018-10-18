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
import gsmith.powers.GoldBarPower;

/**
 * @version 0.2.0 18 Oct 2018
 *
 */
public class GoldBar extends CustomCard {
	
	public static final String ID = "Gold Bar";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 2;
	
	private static final int POWER_GAIN = 1;
	
	public static final String PATH = "cards/gold_bar.png";
	
	public GoldBar() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.POWER, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = POWER_GAIN;
		this.isEthereal = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new GoldBar();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			
			this.isEthereal = false;
			this.isInnate = true;
			
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
			
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
				new GoldBarPower(player, this.magicNumber), this.magicNumber));
	}
}
