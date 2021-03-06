package gsmith.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.actions.GainGoldAction;
import gsmith.patches.AbstractCardEnum;

public class TreasureMap extends CustomCard {

	public static final String ID = "Treasure Map";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	
	private static final int GOLD_GAIN = 10;
	private static final int UPGRADE_PLUS_GOLD = 5;
	
	public static final String PATH = "cards/treasure_map.png";
	
	public TreasureMap() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		
		this.baseMagicNumber = this.magicNumber = GOLD_GAIN;
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_GOLD);
		}

	}

	@Override
	public AbstractCard makeCopy() {
		return new TreasureMap();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new GainGoldAction(player, this.magicNumber));
	}

}
