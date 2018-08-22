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
import gsmith.actions.LoseGoldAction;
import gsmith.patches.AbstractCardEnum;

public class PaymentAccepted extends CustomCard {
	
	public static final String ID = "Payment Accepted";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	
	private static final int ATTACK_DMG = 16;
	private static final int UPGRADE_PLUS_DMG =  4;
	private static final int GOLD_LOSE = 20;
	
	public static final String PATH = "cards/payment_accepted.png";
	
	public PaymentAccepted() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = this.magicNumber = GOLD_LOSE;
	}

	@Override
	public AbstractCard makeCopy() {
		return new PaymentAccepted();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
		}

	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		AbstractDungeon.actionManager.addToBottom(new LoseGoldAction(player, GOLD_LOSE));
		
		if (player.gold == 0) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
					new DamageInfo(player, this.damage, this.damageTypeForTurn), 
					AbstractGameAction.AttackEffect.BLUNT_HEAVY));
			
			this.exhaust = true;
		}	
	}

}
