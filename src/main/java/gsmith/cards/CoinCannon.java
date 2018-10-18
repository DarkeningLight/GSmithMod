package gsmith.cards;

import com.megacrit.cardcrawl.actions.unique.SkewerAction;
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
 * @version 0.1.0 29 Sep 2018 
 *
 */
public class CoinCannon extends CustomCard {
	
	public static final String ID = "Coin Cannon";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -1;
	
	private static final int GOLD_LOSS = 25;
	private static final int DMG_AMT = 8;
	private static final int UPGRADE_PLUS_DMG = 4;
	
	public static final String PATH = "cards/coin_cannon.png";
	
	public CoinCannon() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = this.damage = DMG_AMT;
	}

	@Override
	public AbstractCard makeCopy() {
		return new CoinCannon();
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
		AbstractDungeon.actionManager.addToBottom(new SkewerAction(player, monster, this.damage, 
				this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
	}
}
