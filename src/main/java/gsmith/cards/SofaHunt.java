package gsmith.cards;

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

public class SofaHunt extends CustomCard {
	
	public static final String ID = "Sofa Hunt";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int BLOCK_AMT = 25;
	private static final int UPGRADE_PLUS_BLOCK = 10;
	
	public static final String PATH = "cards/sofa_hunt.png";
	
	public SofaHunt() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.baseBlock = this.block = BLOCK_AMT;
		this.exhaust = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new SofaHunt();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		if (player.gold <= GSmithMod.BANKRUPT ) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
		}
	}

}
