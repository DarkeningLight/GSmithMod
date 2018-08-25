package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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

public class CashToBurn extends CustomCard {

	public static final String ID = "Cash To Burn";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	
	private static final int DRAW_GAIN = 2;
	private static final int UPGRADE_PLUS_DRAW = 2;
	private static final int GOLD_LOSS = 10;
	
	public static final String PATH = "cards/cash_to_burn.png";
	
	public CashToBurn() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		
		this.baseMagicNumber = this.magicNumber = DRAW_GAIN;
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_DRAW);
		}

	}

	@Override
	public AbstractCard makeCopy() {
		return new CashToBurn();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(player, GOLD_LOSS));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, this.magicNumber));
	}
}
