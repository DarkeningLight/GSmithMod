package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;

/**
 * @version 0.1.2 23 Nov 2018
 *
 */
public class CoinCaddy extends CustomCard {
	
	public static final String ID = "Coin Caddy";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	
	public static final String PATH = "cards/coin_caddy.png";
	
	public CoinCaddy() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.exhaust = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new CoinCaddy();
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
		
		int strGain = 1;
		
		if (upgraded && GSmithMod.isProsperous(player)) {
			strGain += 2;
		} 
		else if (GSmithMod.isProsperous(player)) {
			strGain += 1;
		}
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
				new StrengthPower(player, strGain), strGain));
	}
}
