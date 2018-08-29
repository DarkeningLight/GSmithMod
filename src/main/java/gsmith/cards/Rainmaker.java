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
import gsmith.powers.RainmakerPower;
import gsmith.powers.ThiefPower;

public class Rainmaker extends CustomCard {
	
	public static final String ID = "Rainmaker";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	
	private static final int ENERGY_GAIN = 1;
	private static final int UPGRADE_INCREASE_ENERGY = 1;
	
	public static final String PATH = "cards/rainmaker.png";
	
	public Rainmaker() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.POWER, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = ENERGY_GAIN;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Rainmaker();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_INCREASE_ENERGY);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
				new RainmakerPower(player, this.magicNumber), this.magicNumber));
	}
}
