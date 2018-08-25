package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.actions.LoseGoldAction;
import gsmith.patches.AbstractCardEnum;

public class PayToWin extends CustomCard {
	
	public static final String ID = "Pay To Win";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int UPGRADE_COST = 0;
	
	private static final int GOLD_LOSS = 30;
	private static final int STR_AMT = 1;
	private static final int DEX_AMT = 1;
	
	public static final String PATH = "cards/pay_to_win.png";
	
	public PayToWin() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
	}

	@Override
	public AbstractCard makeCopy() {
		return new PayToWin();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADE_COST);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
			AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(player, GOLD_LOSS));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
					new StrengthPower(player, STR_AMT), STR_AMT));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
					new DexterityPower(player, DEX_AMT), DEX_AMT));
	}

}
