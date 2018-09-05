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

public class SpinningBag extends CustomCard {
	
	public static final String ID = "Spinning Bag";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	
	private static final int ATTACK_DMG = 7;
	private static final int NUM_TIMES = 3;
	private static final int UPGRADE_PLUS_TIMES = 1;
	
	public static final String PATH = "cards/spinning_bag.png";
	
	public SpinningBag() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = this.damage =  ATTACK_DMG;
		this.isMultiDamage = true;
		this.baseMagicNumber = this.magicNumber = NUM_TIMES;
	}

	@Override
	public AbstractCard makeCopy() {
		return new SpinningBag();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_TIMES);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		if (player.gold >= 500) {
			for (int i = 1 ; i <= this.magicNumber ; i++) {
				AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
						new DamageInfo(player, this.damage, this.damageTypeForTurn), 
						AbstractGameAction.AttackEffect.BLUNT_HEAVY));
			}
		}
	}
}
