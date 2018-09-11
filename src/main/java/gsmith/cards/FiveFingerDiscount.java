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
import gsmith.actions.GainGoldAction;
import gsmith.patches.AbstractCardEnum;

public class FiveFingerDiscount extends CustomCard {
	
	public static final String ID = "Five Finger Discount";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int ATTACK_DMG = 1;
	private static final int NUM_TIME = 5;
	private static final int GOLD_GAIN = 10;
	private static final int UPGRADE_PLUS_GOLD = 10;
	
	public static final String PATH = "cards/five_finger_discount.png";
	
	public FiveFingerDiscount() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = this.damage = ATTACK_DMG;
		this.baseMagicNumber = this.magicNumber = GOLD_GAIN;
	}

	@Override
	public AbstractCard makeCopy() {
		return new FiveFingerDiscount();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_GOLD);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		for (int i = 1 ; i <= NUM_TIME ; i++) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
					new DamageInfo(player, this.damage, this.damageTypeForTurn), 
					AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		}
		
		if (monster.isDead) {
			AbstractDungeon.actionManager.addToBottom(new GainGoldAction(player, this.magicNumber));
		}
			
	}

}
