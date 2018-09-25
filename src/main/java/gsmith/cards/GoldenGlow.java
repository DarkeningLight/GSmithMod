package gsmith.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;

public class GoldenGlow extends CustomCard {

	public static final String ID = "Golden Glow";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int STR_LOSE = 7;
	private static final int UPGRADE_PLUS_STR = 3;
	private static final int STR_GAIN = 3;
	
	public static final String PATH = "cards/golden_glow.png";
	
	public GoldenGlow() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.SKILL, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL);
		
		this.baseMagicNumber = this.magicNumber = STR_LOSE;
		this.exhaust = true;
	}

	@Override
	public boolean canUpgrade() {
		return false;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_STR);
			
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new GoldenGlow();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {

		for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
			if ((!m.isDead) && (!m.isDying)) {

				if (m.hasPower("Artifact")) {
					System.out.println("HAS ARTIFACT");
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, player, 
							new StrengthPower(m, -this.magicNumber), -this.magicNumber));
				}
				else {

					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, player, 
							new StrengthPower(m, -this.magicNumber), -this.magicNumber));

					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, player, 
							new GainStrengthPower(m, this.magicNumber), this.magicNumber));
				}
			}
		}

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
				new StrengthPower(player, STR_GAIN), STR_GAIN));

	}
}
