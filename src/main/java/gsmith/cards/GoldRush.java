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

/**
 * @version 0.1.2 26 Sep 2018
 *
 */
public class GoldRush extends CustomCard {
	
	public static final String ID = "Gold Rush";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	
	private static final int ATTACK_DMG = 20;
	private static final int UPGRADE_PLUS_DMG =  6;
	private static final int GOLD_GAIN = 20;
	private static final int UPGRADE_PLUS_GOLD = 10;
	
	public static final String PATH = "cards/gold_rush.png";
	
	public GoldRush() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = ATTACK_DMG;
		this.exhaust = true;
		this.baseMagicNumber = this.magicNumber = GOLD_GAIN;
	}

	@Override
	public AbstractCard makeCopy() {
		return new GoldRush();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
			this.upgradeMagicNumber(UPGRADE_PLUS_GOLD);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		AbstractDungeon.actionManager.addToBottom(new GainGoldAction(player, this.magicNumber));
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
				new DamageInfo(player, this.damage, this.damageTypeForTurn), 
				AbstractGameAction.AttackEffect.BLUNT_HEAVY));
	}

}
