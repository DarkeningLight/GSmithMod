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
import gsmith.powers.SleightOfHandPower;

public class SleightOfHand extends CustomCard {
	
	public static final String ID = "Sleight of Hand";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int GOLD_GAIN = 1;
	private static final int UPGRADE_INCREASE_GOLD = 1;
	
	public static final String PATH = "cards/sleight_of_hand.png";
	
	public SleightOfHand() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.POWER, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = GOLD_GAIN;
	}

	@Override
	public AbstractCard makeCopy() {
		return new SleightOfHand();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_INCREASE_GOLD);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
				new SleightOfHandPower(player, this.magicNumber), this.magicNumber));
	}

	@Override
	public void triggerOnEndOfPlayerTurn() {
		AbstractDungeon.player.hand.moveToExhaustPile(this);
	}
	
	

}
