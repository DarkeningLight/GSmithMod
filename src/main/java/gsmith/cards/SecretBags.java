package gsmith.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.actions.SecretBagsAction;
import gsmith.patches.AbstractCardEnum;

public class SecretBags extends CustomCard {
	
	public static final String ID = "Secret Bags";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -1;
	
	private static final int GOLD_GAIN = 9;
	private static final int UPGRADE_PLUS_GOLD = 3 ;
	
	public static final String PATH = "cards/secret_bags.png";
	
	public SecretBags() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = GOLD_GAIN;
	}

	@Override
	public AbstractCard makeCopy() {
		return new SecretBags();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_GOLD);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		if(this.energyOnUse < EnergyPanel.totalCount) {
			this.energyOnUse = EnergyPanel.totalCount;
		}
		
		AbstractDungeon.actionManager.addToBottom(
				new SecretBagsAction(player, this.energyOnUse, GOLD_GAIN, this.freeToPlayOnce));
	}
	
	@Override
	public void triggerOnEndOfPlayerTurn() {
		AbstractDungeon.player.hand.moveToExhaustPile(this);
	}
}
