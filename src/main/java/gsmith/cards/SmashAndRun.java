package gsmith.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;

public class SmashAndRun extends CustomCard {
	
	public static final String ID = "Smash & Run";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 4;
	
	private static final int ATTACK_DMG = 20;
	private static final int UPGRADE_PLUS_DMG =  10;
	private static final int MAGIC_AMT = 2;
	private static final int UPGRADE_PLUS_MAGIC = 2;
	private static final int ENERGY_GAIN = 4;
	
	public static final String PATH = "cards/smash_and_run.png";
	
	public SmashAndRun() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = this.magicNumber = MAGIC_AMT;
	}

	@Override
	public AbstractCard makeCopy() {
		return new SmashAndRun();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
			this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
				new DamageInfo(player, this.damage, this.damageTypeForTurn), 
				AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, 
				new WeakPower(monster, this.magicNumber, false), this.magicNumber));
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, 
				new VulnerablePower(monster, this.magicNumber, false), this.magicNumber));
		
		if (player.gold >= GSmithMod.PROSPEROUS) {
			AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN));
		}
			
	}

}
