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

/**
 * @version 0.1.2 26 Sep 2018
 *
 */
public class HeavyCoin extends CustomCard {
	
	public static final String ID = "Heavy Coin";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int ATTACK_DMG = 10;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int GOLD_LOSS = 5;
	
	public static final String PATH = "cards/heavy_coin.png";
	
	public HeavyCoin() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = ATTACK_DMG;
		this.isMultiDamage = true;
		this.exhaust = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new HeavyCoin();
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
		
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, this.multiDamage, 
					this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}
}
