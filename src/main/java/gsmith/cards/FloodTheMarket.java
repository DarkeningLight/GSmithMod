package gsmith.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gsmith.GSmithMod;
import gsmith.patches.AbstractCardEnum;

public class FloodTheMarket extends CustomCard {
	
	public static final String ID = "Flood The Market";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int ATTACK_DMG_MULTIPLER = 1;
	private static final int UPGRADE_PLUS_MUL = 1;
	
	public static final String PATH = "cards/flood_the_market.png";
	
	public FloodTheMarket() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		
		this.baseMagicNumber = this.magicNumber = ATTACK_DMG_MULTIPLER;
		this.baseDamage = this.damage = 0;
		this.exhaust = true;
	}

	@Override
	public AbstractCard makeCopy() {
		return new FloodTheMarket();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_MUL);
		}
	}

	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();
		this.baseDamage = this.damage = calculateDamage(AbstractDungeon.player);
	}

	@Override
	public void triggerWhenCopied() {
		super.triggerWhenCopied();
		this.baseDamage = this.damage = calculateDamage(AbstractDungeon.player);
	}
	
	@Override
	public void triggerOnOtherCardPlayed(AbstractCard c) {
		// TODO Auto-generated method stub
		super.triggerOnOtherCardPlayed(c);
		if (c.exhaust == true) {
			this.baseDamage = this.damage += this.magicNumber;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
				new DamageInfo(player, this.damage, this.damageTypeForTurn), 
				AbstractGameAction.AttackEffect.BLUNT_LIGHT));
	}
	
	public int calculateDamage(AbstractPlayer player) {
		return (player.exhaustPile.size() * this.magicNumber);
	}

}
