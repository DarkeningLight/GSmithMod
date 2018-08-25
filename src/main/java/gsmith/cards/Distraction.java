package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;

public class Distraction extends CustomCard {

	public static final String ID = "Distraction_D";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int BLOCK_GAIN = 3;
	private static final int UPGRADE_PLUS_BLOCK = 2;
	private static final int WEAK_AMT = 3;
	private static final int UPGRADE_PLUS_WEAK = 2;
	
	public static final String PATH = "cards/distraction.png";
	
	public Distraction() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
		
		this.baseMagicNumber = this.magicNumber = WEAK_AMT;
		this.baseBlock = this.block = BLOCK_GAIN;
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_WEAK);
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new Distraction();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, 
				new WeakPower(monster, this.magicNumber, false), this.magicNumber));
	}
}
