package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;

public class CountingDesk extends CustomCard {
	
	public static final String ID = "Counting Desk";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	
	private static final int DEX_GAIN = 3;
	private static final int UPGRADE_PLUS_DEX =  3;
	
	public static final String PATH = "cards/counting_desk.png";
	
	public CountingDesk() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = DEX_GAIN;
	}

	@Override
	public AbstractCard makeCopy() {
		return new CountingDesk();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_DEX);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
				new DexterityPower(player, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
				new LoseDexterityPower(player, this.magicNumber), this.magicNumber));
		
	}
}
