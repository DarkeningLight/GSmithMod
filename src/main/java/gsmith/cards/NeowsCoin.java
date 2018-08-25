package gsmith.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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

public class NeowsCoin extends CustomCard {
	
	public static final String ID = "Neow's Coin";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	
	private static final int ATTACK_DMG = 12;
	private static final int UPGRADE_PLUS_DMG =  4;
	private static final int BLOCK_AMT = 5;
	
	public static final String PATH = "cards/neows_coin.png";
	
	public NeowsCoin() {
		super(ID, NAME, GSmithMod.makePath(PATH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, 
				AbstractCardEnum.GOLD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = ATTACK_DMG;
		this.baseBlock = this.block = BLOCK_AMT;
	}

	@Override
	public AbstractCard makeCopy() {
		return new NeowsCoin();
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
		
		if (player.gold == 0) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, 
					new DamageInfo(player, this.damage, this.damageTypeForTurn), 
					AbstractGameAction.AttackEffect.BLUNT_LIGHT));
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
		}
	}

}
