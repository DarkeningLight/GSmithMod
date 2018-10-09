package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.actions.LoseGoldAction;
import gsmith.patches.AbstractCardEnum;

public class ProtectionMoney extends CustomCard {
	
	public static final String ID = "Protection Money";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int GOLD_LOSS = 25;
	private static final int ARTIFACT_GAIN = 1;
	private static final int UPGRADE_PLUS_ARTIFACT = 1;
	
	public static final String PATH = "cards/protection_money.png";
	
	public ProtectionMoney() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		
		this.baseMagicNumber = this.magicNumber = ARTIFACT_GAIN;
		this.exhaust = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new ProtectionMoney();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_ARTIFACT);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
			AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(player, GOLD_LOSS));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
					new ArtifactPower(player, this.magicNumber), this.magicNumber));
	}

}
