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
import gsmith.actions.GainGoldAction;
import gsmith.patches.AbstractCardEnum;

/**
 * @version 0.2.1 9 Oct 2018
 *
 */
public class OldSurprise extends CustomCard {
	
	public static final String ID = "Old Surprise";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	
	private static final int BLOCK_AMT =  3;
	
	public static final String PATH = "cards/old_surprise.png";
	
	public OldSurprise() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		
		this.isInnate = true;
		this.exhaust = true;
		this.baseMagicNumber = this.magicNumber = GSmithMod.BANKRUPT;
		this.baseBlock = this.block = BLOCK_AMT;
	}

	@Override
	public AbstractCard makeCopy() {
		return new OldSurprise();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		if (player.gold <= GSmithMod.BANKRUPT ) {
			AbstractDungeon.actionManager.addToBottom(new GainGoldAction(player, this.magicNumber));
			
			if (upgraded) {
				AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
			}
		}
	}

}
