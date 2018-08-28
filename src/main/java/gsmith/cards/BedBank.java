package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
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

public class BedBank extends CustomCard {
	
	public static final String ID = "Bed Bank";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	
	private static final int GOLD_GAIN = 8;
	private static final int UPGRADE_PLUS_GOLD = 2;
	
	public static final String PATH = "cards/bed_bank.png";
	
	public BedBank() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = GOLD_GAIN;
		this.isEthereal = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new BedBank();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_GOLD);
			this.upgradeBaseCost(UPGRADED_COST);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int count = (player.hand.size() - 1);
		AbstractDungeon.actionManager.addToBottom(new ExhaustAction(player, player, player.hand.size(), false));
		AbstractDungeon.actionManager.addToBottom(new GainGoldAction(player, (this.magicNumber * count)));
	}

}
