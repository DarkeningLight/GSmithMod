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

/**
 * @version 0.1.2 26 Sep 2018
 *
 */
public class FistFulOfCoin extends CustomCard {
	
	public static final String ID = "Fistful of Coin";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int ATTACK_DMG = 11;
	private static final int UPGRADE_PLUS_DMG =  4;
	private static final int GOLD_LOSS = 5;
	
	public static final String PATH = "cards/fist_full_of_coin.png";
	
	public FistFulOfCoin() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = ATTACK_DMG;
	}

	@Override
	public AbstractCard makeCopy() {
		return new FistFulOfCoin();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(player, GOLD_LOSS));
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
				new DamageInfo(player, this.damage, this.damageTypeForTurn), 
				AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}

}
