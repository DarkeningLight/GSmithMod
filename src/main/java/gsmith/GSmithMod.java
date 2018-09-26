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
import gsmith.cards.BedBank;
import gsmith.cards.BestBag;
import gsmith.cards.BigSpender;
import gsmith.cards.BlockChain;
import gsmith.cards.BouncingBag;
import gsmith.cards.Bribe;
import gsmith.cards.CashToBurn;
import gsmith.cards.Cheapskate;
import gsmith.cards.CoinBarrage;
import gsmith.cards.CoinCaddy;
import gsmith.cards.CoinToss;
import gsmith.cards.CoinTrick;
import gsmith.cards.CoinWall;
import gsmith.cards.CountingDesk;
import gsmith.cards.Distraction;
import gsmith.cards.Dive;
import gsmith.cards.EatTheCoins;
import gsmith.cards.Edge;
import gsmith.cards.EmptyHands;
import gsmith.cards.EmptyWallet;
import gsmith.cards.Exchange;
import gsmith.cards.FistFulOfCoin;
import gsmith.cards.FiveFingerDiscount;
import gsmith.cards.FloodTheMarket;
import gsmith.cards.GoldBar;
import gsmith.cards.GoldFu;
import gsmith.cards.GoldRush;
import gsmith.cards.GoldenGlow;
import gsmith.cards.GreedIsGood;
import gsmith.cards.HalfPenny;
import gsmith.cards.HammerCoin;
import gsmith.cards.HardCrash;
import gsmith.cards.Heads;
import gsmith.cards.HeavyCoin;
import gsmith.cards.Hoard;
import gsmith.cards.LooseChange;
import gsmith.cards.LuckyFind;
import gsmith.cards.MarketCrash;
import gsmith.cards.MarketLove;
import gsmith.cards.Money;
import gsmith.cards.MoneyInjection;
import gsmith.cards.NeowsCoin;
import gsmith.cards.NoCoin;
import gsmith.cards.OldSurprise;
import gsmith.cards.PayToWin;
import gsmith.cards.Payday;
import gsmith.cards.PaymentAccepted;
import gsmith.cards.ProtectionMoney;
import gsmith.cards.Rainmaker;
import gsmith.cards.SaleSign;
import gsmith.cards.SecretBags;
import gsmith.cards.SleepingCash;
import gsmith.cards.SmashAndRun;
import gsmith.cards.SofaHunt;
import gsmith.cards.SpareChange;
import gsmith.cards.SpinningBag;
import gsmith.cards.StrikeD;
import gsmith.cards.Tails;
import gsmith.cards.Thief;
import gsmith.cards.TreasureHunt;
import gsmith.cards.TreasureMap;
import gsmith.characters.TheGoldsmith;
import gsmith.patches.AbstractCardEnum;
import gsmith.patches.TheGoldsmithEnum;
import gsmith.relics.BagOfCoins;

/**
 * @version 0.1.2 26 Sep 2018
 *
 */
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
	
	//badge & button images
	public static final String BADGE_IMG = "badge.png";
	private static final String GOLDSMITH_BUTTON = "charSelect/goldsmithButton.png";
    private static final String GOLDSMITH_PORTRAIT = "charSelect/goldsmithPortraitBG.jpg";
    
    //thresholds
    public static final int BANKRUPT = 50;
    public static final int PROSPEROUS = 400;
	
	
	// Short Texture Methods
	public static Texture getBagOfCoinsTexture() { return new Texture(makePath(BAG_OF_COINS_RELIC)); }

	public static final String makePath(String resource) {
		String s = ASSETS_FOLDER + "/" + resource;
		return s;
	}
	
	public GSmithMod() {
		BaseMod.subscribe(this);
		
		//Creating the colour Gold
		BaseMod.addColor(AbstractCardEnum.GOLD, 
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
    	
    	Settings.isDailyRun = false;
    	Settings.isTrial = false;
    	Settings.isDemo = false;
    }

	@Override
	public void receiveEditCharacters() {
		BaseMod.addCharacter(TheGoldsmith.class, "The Goldsmith", "Goldsmith class string",
				AbstractCardEnum.GOLD, "The Goldsmith", 
				makePath(GOLDSMITH_BUTTON), makePath(GOLDSMITH_PORTRAIT), 
				TheGoldsmithEnum.THE_GOLDSMITH);
		
	}

	@Override
	public void receiveEditRelics() {
		BaseMod.addRelicToCustomPool(new BagOfCoins(), AbstractCardEnum.GOLD );
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
		BaseMod.addCard(new HammerCoin());
		BaseMod.addCard(new SaleSign());
		BaseMod.addCard(new SofaHunt());
		BaseMod.addCard(new EmptyWallet());
		BaseMod.addCard(new PayToWin());
		BaseMod.addCard(new SmashAndRun());
		BaseMod.addCard(new NeowsCoin());
		BaseMod.addCard(new Distraction());
		BaseMod.addCard(new CashToBurn());
		BaseMod.addCard(new FloodTheMarket());
		BaseMod.addCard(new MoneyInjection());
		BaseMod.addCard(new ProtectionMoney());
		BaseMod.addCard(new BedBank());
		BaseMod.addCard(new EmptyHands());
		BaseMod.addCard(new Dive());
		BaseMod.addCard(new GoldBar());
		BaseMod.addCard(new TreasureHunt());
		BaseMod.addCard(new BigSpender());
		BaseMod.addCard(new LuckyFind());
		BaseMod.addCard(new SpinningBag());
		BaseMod.addCard(new Exchange());
		//BaseMod.addCard(new FiveFingerDiscount()); 
		BaseMod.addCard(new SleepingCash());
		BaseMod.addCard(new MarketLove());
		BaseMod.addCard(new Hoard());
		BaseMod.addCard(new GoldenGlow());
		
		unlockCards();
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
				"You are #yBankrupt if you have 50 or fewer gold");
		BaseMod.addKeyword(new String[] {"prosperous" , "Prosperous"}, 
				"You are #yProsperous if you have 400 or more gold.");
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
	
	private void unlockCards() {
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
		UnlockTracker.unlockCard("Hammer & Coin");
		UnlockTracker.unlockCard("Sale Sign");
		UnlockTracker.unlockCard("Sofa Hunt");
		UnlockTracker.unlockCard("Empty Wallet");
		UnlockTracker.unlockCard("Pay To Win");
		UnlockTracker.unlockCard("Smash & Run");
		UnlockTracker.unlockCard("Neow's Coin");
		UnlockTracker.unlockCard("Distraction_D");
		UnlockTracker.unlockCard("Cash To Burn");
		UnlockTracker.unlockCard("Flood The Market");
		UnlockTracker.unlockCard("Money Injection");
		UnlockTracker.unlockCard("Protection Money");
		UnlockTracker.unlockCard("Bed Bank");
		UnlockTracker.unlockCard("Empty Hands");
		UnlockTracker.unlockCard("Dive");
		UnlockTracker.unlockCard("Gold Bar");
		UnlockTracker.unlockCard("Treasure Hunt");
		UnlockTracker.unlockCard("Big Spender");
		UnlockTracker.unlockCard("Lucky Find");
		UnlockTracker.unlockCard("Spinning Bag");
		UnlockTracker.unlockCard("Exchange");
		//UnlockTracker.unlockCard("Five Finger Discount");
		UnlockTracker.unlockCard("Sleeping Cash");
		UnlockTracker.unlockCard("Market Love");
		UnlockTracker.unlockCard("Hoard");
		UnlockTracker.unlockCard("Golden Glow");
	}
}