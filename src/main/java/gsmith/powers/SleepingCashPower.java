package gsmith.powers;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import gsmith.GSmithMod;

public class SleepingCashPower extends AbstractPower {
	
	public static final String PATH = "powers/sleeping_cash.png";
	public static final String POWER_ID = "Sleeping Cash";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String NAME = powerStrings.NAME;
	private AbstractCard source;
	
	
	
	public SleepingCashPower(AbstractCreature owner,int amount, AbstractCard source) {
		super();
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.source = source;
		updateDescription();
		this.img = getTexture();
	}  

	
	@Override
	public void onExhaust(AbstractCard card) {
		super.onExhaust(card);
		
		if (this.amount >= 10) {
		
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, calculateDamage(), 
					source.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		} else {
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, calculateDamage(), 
					source.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		}
	}

	public int[] calculateDamage() {
		ArrayList<Integer> list = new ArrayList<>();
		
		Iterator<AbstractMonster> i = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
		
		while (i.hasNext()) {
			AbstractMonster m = i.next(); 
			if (!m.isDeadOrEscaped()) {
				list.add(this.amount);
			}
		}
		
		list.trimToSize();
		int [] damage = new int[list.size()];
		
		for (int j = 0 ; j < list.size() ; j++) {
			damage[j] = list.get(j);
		}
		
		return damage;
	}

	@Override
	public void updateDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append(DESCRIPTIONS[0]);
		sb.append("#b" + amount);
		sb.append(DESCRIPTIONS[1]);
		
		this.description = sb.toString();
	}

	public static Texture getTexture() { return new Texture(GSmithMod.makePath(PATH)); }
}