package gsmith.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
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

public class FiveFingerDiscount extends CustomCard {
	
	public static final String ID = "Five Finger Discount";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	
	private static final int ATTACK_DMG = 5;
	private static final int DISCOUNT_NUM = 2;
	
	public static final String PATH = "cards/five_finger_discount.png";
	
	public FiveFingerDiscount() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = this.damage = ATTACK_DMG;
		this.exhaust = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new FiveFingerDiscount();
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
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
				new DamageInfo(player, this.damage, this.damageTypeForTurn), 
				AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		if (this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
					new DamageInfo(player, this.damage, this.damageTypeForTurn), 
					AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		}
		
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Discount(), DISCOUNT_NUM));
	}

}
