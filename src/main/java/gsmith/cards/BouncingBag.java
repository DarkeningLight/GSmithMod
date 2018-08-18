package gsmith.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.actions.LoseGoldAction;
import gsmith.patches.AbstractCardEnum;

public class BouncingBag extends CustomCard {
	
	public static final String ID = "Bouncing Bag";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int ATTACK_DMG = 3;
	private static final int NUM_TIMES = 3;
	private static final int UPGRADE_PLUS_TIMES = 1;
	private static final int GOLD_LOSS = 10;
	
	public static final String PATH = "cards/bouncing_bag.png";
	
	public BouncingBag() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = ATTACK_DMG;
		this.isMultiDamage = true;
		this.baseMagicNumber = this.magicNumber = NUM_TIMES;
	}

	@Override
	public AbstractCard makeCopy() {
		return new BouncingBag();
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
		
		for (int i = 1 ; i <= this.magicNumber ; i++) {
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, this.multiDamage, 
					this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
			}
		AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(player, GOLD_LOSS));
	}
	
	@Override
	public void triggerOnEndOfPlayerTurn() {
		if (!upgraded) {
			AbstractDungeon.player.hand.moveToExhaustPile(this);
		}
	}
}
