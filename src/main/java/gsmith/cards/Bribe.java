package gsmith.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;

/**
 * @version 0.1.2 23 Nov 2018
 *
 */
public class Bribe extends CustomCard {
	
	public static final String ID = "Bribe";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int ATTACK_DMG = 9;
	private static final int UPGRADE_PLUS_DMG =  3;
	private static final int WEAK_AMT = 2;
	private static final int UPGRADE_PLUS_WEAK = 2;
	
	public static final String PATH = "cards/bribe.png";
	
	public Bribe() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = this.magicNumber = WEAK_AMT;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Bribe();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
			this.upgradeMagicNumber(UPGRADE_PLUS_WEAK);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster m) {
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, 
				new DamageInfo(player, this.damage, this.damageTypeForTurn), 
				AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && GSmithMod.isProsperous(player))
	    {
	      flash();
	      for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
	        if ((!monster.isDead) && (!monster.isDying)) {
	          AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, 
	        		  new VulnerablePower(monster, this.magicNumber, false), this.magicNumber));
	        }
	      }
	    }
	}

}
