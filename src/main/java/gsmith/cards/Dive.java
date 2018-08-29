package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;
import gsmith.powers.DivePower;

public class Dive extends CustomCard {

	public static final String ID = "Dive";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;
	
	private static final int BLOCK_GAIN = 3;
	private static final int TURNS_AMT = 2;
	private static final int UPGRADE_TURNS_AMT = 1;
	
	public static final String PATH = "cards/dive.png";
	
	public Dive() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.exhaust = true;
		this.baseBlock = this.block = BLOCK_GAIN;
		this.baseMagicNumber = this.magicNumber = TURNS_AMT;
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADED_COST);
			this.upgradeMagicNumber(UPGRADE_TURNS_AMT);
		}

	}

	@Override
	public AbstractCard makeCopy() {
		return new Dive();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
				new DivePower(player, this.block, this.magicNumber), this.block));

	}

}
