package gsmith.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.actions.LoseGoldAction;
import gsmith.patches.AbstractCardEnum;

/**
 * @version 0.1.3 26 Sep 2018
 *
 */
public class HardCrash extends CustomCard {
	
	public static final String ID = "Hard Crash";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int ATTACK_DMG = 8;
	private static final int UPGRADE_PLUS_DMG =  3;
	private static final int STR_AMT = 1;
	private static final int UPGRADE_PLUS_STR = 1;
	private static final int GOLD_LOSS = 10;
	
	public static final String PATH = "cards/hard_crash.png";
	
	public HardCrash() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = this.magicNumber = STR_AMT;
	}

	@Override
	public AbstractCard makeCopy() {
		return new HardCrash();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
			this.upgradeMagicNumber(UPGRADE_PLUS_STR);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(player, GOLD_LOSS));
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
				new DamageInfo(player, this.damage, this.damageTypeForTurn), 
				AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		
		if(player.gold <= GSmithMod.BANKRUPT) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, 
	        		  new StrengthPower(player, this.magicNumber), this.magicNumber));
		}
	}

}
