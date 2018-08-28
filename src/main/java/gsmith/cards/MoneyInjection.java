package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.actions.LoseGoldAction;
import gsmith.patches.AbstractCardEnum;

public class MoneyInjection extends CustomCard {

	public static final String ID = "Money Injection";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	
	private static final int GOLD_LOSS = 20;
	private static final int ENERGY_GAIN = 1;
	private static final int UPGRADE_PLUS_ENERGY = 1;
	
	public static final String PATH = "cards/money_injection.png";
	
	public MoneyInjection() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		
		this.baseMagicNumber = this.magicNumber = ENERGY_GAIN;
		this.exhaust = true;
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_ENERGY);
			
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}

	}

	@Override
	public AbstractCard makeCopy() {
		return new MoneyInjection();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(player, GOLD_LOSS));
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
	}

}
