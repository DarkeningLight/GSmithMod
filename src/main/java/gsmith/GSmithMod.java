package gsmith;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import gsmith.cards.BagFu;
import gsmith.cards.BagSlam;
import gsmith.cards.BestBag;
import gsmith.cards.BiggerBag;
import gsmith.cards.BlockChain;
import gsmith.cards.BouncingBag;
import gsmith.cards.Bribe;
import gsmith.cards.Cheapskate;
import gsmith.cards.CoinBarrage;
import gsmith.cards.CoinCaddy;
import gsmith.cards.CoinToss;
import gsmith.cards.CoinTrick;
import gsmith.cards.CoinWall;
import gsmith.cards.CountingDesk;
import gsmith.cards.EatTheCoins;
import gsmith.cards.Edge;
import gsmith.cards.FistFulOfCoin;
import gsmith.cards.GoldFu;
import gsmith.cards.GoldRush;
import gsmith.cards.GreedIsGood;
import gsmith.cards.HalfPenny;
import gsmith.cards.HardCrash;
import gsmith.cards.Heads;
import gsmith.cards.HeavyCoin;
import gsmith.cards.LooseChange;
import gsmith.cards.MarketCrash;
import gsmith.cards.Money;
import gsmith.cards.NoCoin;
import gsmith.cards.OldSurprise;
import gsmith.cards.Payday;
import gsmith.cards.PaymentAccepted;
import gsmith.cards.Rainmaker;
import gsmith.cards.SecretBags;
import gsmith.cards.SleightOfHand;
import gsmith.cards.SpareChange;
import gsmith.cards.StrikeD;
import gsmith.cards.Tails;
import gsmith.cards.Thief;
import gsmith.cards.TreasureMap;
import gsmith.characters.TheGoldsmith;
import gsmith.patches.AbstractCardEnum;
import gsmith.patches.TheGoldsmithEnum;
import gsmith.relics.BagOfCoins;

@SpireInitializer
public class GSmithMod implements PostInitializeSubscriber, EditRelicsSubscriber, EditStringsSubscriber, 
									EditCardsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber {
	
	private static final String MODNAME = "Goldsmith Mod";
	private static final String AUTHOR = "People";
	private static final String DESCRIPTION = "This is a thingy";
	
	private static final Color GOLD = CardHelper.getColor(210.0f, 187.0f, 26.0f);
	private static final String ASSETS_FOLDER = "img";
	
	//card backgrounds
	
	private static final String ATTACK_GOLD = "512/bg_attack_gold.png";
	private static final String SKILL_GOLD = "512/bg_skill_gold.png";
	private static final String POWER_GOLD = "512/bg_power_gold.png";
	private static final String ENERGY_ORB_GOLD = "512/card_gold_orb.png";
	
	private static final String ATTACK_GOLD_PORTRAIT = "1024/bg_attack_gold.png";
	private static final String SKILL_GOLD_PORTRAIT = "1024/bg_skill_gold.png";
	private static final String POWER_GOLD_PORTRAIT = "1024/bg_power_gold.png";
	private static final String ENERGY_ORB_GOLD_PORTRAIT = "1024/card_gold_orb.png";
	
	//relic images
	public static final String BAG_OF_COINS_RELIC = "relics/bagofcoins.png";
	
	//badge image
	public static final String BADGE_IMG = "badge.png";
	
	private static final String GOLDSMITH_BUTTON = "charSelect/goldsmithButton.png";
    private static final String GOLDSMITH_PORTRAIT = "charSelect/goldsmithPortraitBG.jpg";
	
	
	// Short Texture Methods
	public static Texture getBagOfCoinsTexture() { return new Texture(makePath(BAG_OF_COINS_RELIC)); }

	public static final String makePath(String resource) {
		String s = ASSETS_FOLDER + "/" + resource;
		return s;
	}
	
	public GSmithMod() {
		BaseMod.subscribe(this);
		
		//Creating the colour Gold
		BaseMod.addColor(AbstractCardEnum.GOLD.toString(), 
				GOLD, GOLD, GOLD, GOLD, GOLD, GOLD, GOLD, 
				makePath(ATTACK_GOLD), makePath(SKILL_GOLD), 
				makePath(POWER_GOLD), makePath(ENERGY_ORB_GOLD), 
				makePath(ATTACK_GOLD_PORTRAIT), makePath(SKILL_GOLD_PORTRAIT), 
				makePath(POWER_GOLD_PORTRAIT), makePath(ENERGY_ORB_GOLD_PORTRAIT));
	}
	
    public static void initialize() {
		@SuppressWarnings("unused")
		GSmithMod gsmith = new GSmithMod();
    }
    
    @Override
    public void receivePostInitialize() {
    	//Mod badge
    	BaseMod.registerModBadge(new Texture(makePath(BADGE_IMG)), MODNAME, AUTHOR, DESCRIPTION, new ModPanel());
    	
    	ModPanel settingsPanel = new ModPanel();
    	settingsPanel.addLabel("Mod", 400.0f, 700.0f, (me) -> {});
    	
    	Settings.isDailyRun = false;
    	Settings.isTrial = false;
    	Settings.isDemo = false;
    }

	@Override
	public void receiveEditCharacters() {
		BaseMod.addCharacter(TheGoldsmith.class, "The Goldsmith", "Goldsmith class string",
				AbstractCardEnum.GOLD.toString(), "The Goldsmith", 
				makePath(GOLDSMITH_BUTTON), makePath(GOLDSMITH_PORTRAIT), 
				TheGoldsmithEnum.THE_GOLDSMITH.toString());
		
	}

	@Override
	public void receiveEditRelics() {
		BaseMod.addRelicToCustomPool(new BagOfCoins(), AbstractCardEnum.GOLD.toString());
		
	}
	
	@Override
	public void receiveEditCards() {
		BaseMod.addCard(new Thief());
		BaseMod.addCard(new SpareChange());
		BaseMod.addCard(new CoinWall());
		BaseMod.addCard(new Payday());
		BaseMod.addCard(new TreasureMap());
		BaseMod.addCard(new StrikeD());
		BaseMod.addCard(new BagSlam());
		BaseMod.addCard(new CoinToss());
		BaseMod.addCard(new FistFulOfCoin());
		BaseMod.addCard(new LooseChange());
		BaseMod.addCard(new BestBag());
		BaseMod.addCard(new BouncingBag());
		BaseMod.addCard(new Cheapskate());
		BaseMod.addCard(new OldSurprise());
		BaseMod.addCard(new HalfPenny());
		BaseMod.addCard(new Bribe());
		BaseMod.addCard(new NoCoin());
		BaseMod.addCard(new CoinBarrage());
		BaseMod.addCard(new HardCrash());
		BaseMod.addCard(new HeavyCoin());
		BaseMod.addCard(new SecretBags());
		//BaseMod.addCard(new BiggerBag());
		BaseMod.addCard(new BlockChain());
		BaseMod.addCard(new GoldRush());
		//BaseMod.addCard(new SleightOfHand());
		BaseMod.addCard(new Rainmaker());
		BaseMod.addCard(new BagFu());
		BaseMod.addCard(new GoldFu());
		BaseMod.addCard(new MarketCrash());
		BaseMod.addCard(new Money());
		BaseMod.addCard(new Heads());
		BaseMod.addCard(new Tails());
		BaseMod.addCard(new Edge());
		BaseMod.addCard(new CoinTrick());
		BaseMod.addCard(new GreedIsGood());
		BaseMod.addCard(new PaymentAccepted());
		BaseMod.addCard(new CountingDesk());
		BaseMod.addCard(new EatTheCoins());
		BaseMod.addCard(new CoinCaddy());
		
		UnlockTracker.unlockCard("Thief");
		UnlockTracker.unlockCard("Spare Change");
		UnlockTracker.unlockCard("Coin Wall");
		UnlockTracker.unlockCard("Payday");
		UnlockTracker.unlockCard("Treasure Map");
		UnlockTracker.unlockCard("Strike_D");
		UnlockTracker.unlockCard("Bag Slam");
		UnlockTracker.unlockCard("Coin Toss");
		UnlockTracker.unlockCard("Fistful of Coin");
		UnlockTracker.unlockCard("Loose Change");
		UnlockTracker.unlockCard("Best Bag");
		UnlockTracker.unlockCard("Bouncing Bag");
		UnlockTracker.unlockCard("Cheapskate");
		UnlockTracker.unlockCard("Old Surprise");
		UnlockTracker.unlockCard("Half Penny");
		UnlockTracker.unlockCard("Bribe");
		UnlockTracker.unlockCard("No Coin");
		UnlockTracker.unlockCard("Coin Barrage");
		UnlockTracker.unlockCard("Hard Crash");
		UnlockTracker.unlockCard("Heavy Coin");
		UnlockTracker.unlockCard("Secret Bags");
		//UnlockTracker.unlockCard("Bigger Bag");
		UnlockTracker.unlockCard("Block Chain");
		UnlockTracker.unlockCard("Gold Rush");
		//UnlockTracker.unlockCard("Sleight of Hand");
		UnlockTracker.unlockCard("Rainmaker");
		UnlockTracker.unlockCard("Bag Fu");
		UnlockTracker.unlockCard("Gold Fu");
		UnlockTracker.unlockCard("Market Crash");
		UnlockTracker.unlockCard("Money");
		UnlockTracker.unlockCard("Heads");
		UnlockTracker.unlockCard("Tails");
		UnlockTracker.unlockCard("Edge");
		UnlockTracker.unlockCard("Coin Trick");
		UnlockTracker.unlockCard("Greed is Good");
		UnlockTracker.unlockCard("Payment Accepted");
		UnlockTracker.unlockCard("Counting Desk");
		UnlockTracker.unlockCard("Eat The Coins");
		UnlockTracker.unlockCard("Coin Caddy");
	}
	
	@Override
	public void receiveEditStrings() {
		// RelicStrings
		String relicStrings = Gdx.files.internal("localization/GMod-RelicStrings.json").readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
		
		// CardStrings
		String cardStrings = Gdx.files.internal("localization/GMod-CardStrings.json").readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
		
		// PowerStrings
		String powerStrings= Gdx.files.internal("localization/GMod-PowerStrings.json").readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
	}
	
	@Override
	public void receiveEditKeywords() {
		BaseMod.addKeyword(new String[] {"bankrupt" , "Bankrupt"}, 
				"Text after bankrupt will only have an effect if you have no gold");
		BaseMod.addKeyword(new String[] {"prosperous" , "Prosperous"}, 
				"Text after prosperous will only have an effect if you have 500 or more gold");
	}

	public static int getActNumber() {
		AbstractDungeon dungeon = CardCrawlGame.dungeon;
		
		if (dungeon instanceof Exordium) {
			return 1;
		} 
		else if (dungeon instanceof TheBeyond) {
			return 3;
		}
		else if (dungeon instanceof TheCity) {
			return 2;
		}
		else {
			System.out.println("Can't find act");
			return 0;
		}
	}
}