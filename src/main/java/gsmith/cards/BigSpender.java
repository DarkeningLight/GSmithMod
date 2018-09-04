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
import gsmith.actions.LoseGoldAction;
import gsmith.patches.AbstractCardEnum;

public class BigSpender extends CustomCard {
	
	public static final String ID = "Big Spender";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 3;
	
	
	public static final String PATH = "cards/big_spender.png";
	
	public BigSpender() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = this.damage = 0;
	}

	@Override
	public AbstractCard makeCopy() {
		return new BigSpender();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.isInnate = true;
			
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();
		this.baseDamage = this.damage = calculateDamage(AbstractDungeon.player);
	}

	@Override
	public void triggerWhenCopied() {
		super.triggerWhenCopied();
		this.baseDamage = this.damage = calculateDamage(AbstractDungeon.player);
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		this.baseDamage = this.damage = calculateDamage(player);
		
		AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(player, player.gold));
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
				new DamageInfo(player, this.damage, this.damageTypeForTurn), 
				AbstractGameAction.AttackEffect.SMASH));
		
		this.baseDamage = this.damage = calculateDamage(player);
	}
	
	public int calculateDamage(AbstractPlayer player) {
		return player.gold;
	}

}
