package gsmith.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;

/**
 * @version 0.3.0 9 Oct 2018
 *
 */
public class Money extends CustomCard {
	
	public static final String ID = "Money";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 4;
	
	private static final int ATTACK_DMG = 3;
	private static final int UPGRADE_PLUS_DMG =  1;
	private static final int NUM_TIMES = 5;
	
	public static final String PATH = "cards/money_money_money.png";
	
	public Money() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = this.damage = ATTACK_DMG;
		this.baseMagicNumber = this.magicNumber = NUM_TIMES;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Money();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
		}

	}
	
	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();
		setCost();
	}

	@Override
	public void triggerWhenCopied() {
		super.triggerWhenCopied();
		setCost();
	}

	@Override
	public void triggerOnOtherCardPlayed(AbstractCard c) {
		super.triggerOnOtherCardPlayed(c);
		if (c.type == CardType.SKILL) {
			modifyCostForTurn(-1);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		for (int i = 0 ; i < this.magicNumber ; i++) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
					new DamageInfo(player, this.damage, this.damageTypeForTurn), 
					AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		}
	}
	
	private void setCost() {
		int skillCount = 0;
		
		for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
			if (c.type == CardType.SKILL) {
				skillCount++;
			}
		}
		
		this.modifyCostForTurn(-skillCount);
	}

}
