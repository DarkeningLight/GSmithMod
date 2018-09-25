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

public class HalfPenny extends CustomCard {
	
	public static final String ID = "Half Penny";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	
	private static final int WEAK_AMT = 3;
	private static final int UPGRADE_PLUS_WEAK = 3;
	private static final int BLOCK_AMT = 3;
	
	public static final String PATH = "cards/half_penny.png";
	
	public HalfPenny() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseMagicNumber = this.magicNumber = WEAK_AMT;
	}

	@Override
	public AbstractCard makeCopy() {
		return new HalfPenny();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_WEAK);
			this.upgradeBlock(BLOCK_AMT);
			
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster m) {
		
		if (upgraded) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
		}
		
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && player.gold <= GSmithMod.BANKRUPT)
	    {
	      flash();
	      for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
	        if ((!monster.isDead) && (!monster.isDying)) {
	          AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, 
	        		  new WeakPower(monster, this.magicNumber, false), this.magicNumber));
	        }
	      }
	    }
	}

}
