
/**
 ChangeLog:
 	v1.0 - 	First release.
 	v1.1 - 	Added salmon to supported food, fixed a few bugs.
 	v1.2 - 	Fixed a small bug with fighting kets.
 	v1.3 - 	Fixed a bug in paint.
 	v1.4 - 	Added special attacks, added to the profit tab in paint, made a few alterations in methods, added run checking,
 			added summoning, added option to search for food.
 	v1.5 - 	Fixed a few more bugs.
 	v1.6 - 	Added "current status" to paint.
 	v1.7 - 	Fixed a few bugs, added potting, made GUI smaller, now picks up potions dropped by the NPCs.
 	v1.8 - 	Fixed a couple of bugs with potions.
 	v1.9 - 	Now picks up Chilli Potatoes and Lobsters to use, doesn't pick up items that are too far (unless they are rares),
 			picks up items from the rare drop table.
 	v2.0 - 	Antiban now also checks special attack tab if using special attacks. You can now use ALL Pizzas, Pies and Cakes.
 			Also added Trout. Smaller GUI with tabs for easier startup. Fixed recharging summoning points. Fixed GUI bug from
 			v2.53 of RSBot.
 	v2.1 - 	Fixed a small bug.
 	v2.2 - 	Fixed a small bug.
 	v2.3 - 	Fixed bug where it would fight xil and then ket at same time.
 	v2.4 - 	Changed item picking. Now only picks up less valuable items if they are close enough. Only walks to item if it is
 			more than 7 tiles away (was only 4 tiles before).
 	v2.5 - 	Doesn't bank for food if carrying chilli potato or lobster.
 */

 /*TODO
  * First of all there is a problem with renewing summoning points. I'm not sure what else is wrong with the script
  * so you'll probably have to test, you can also look on the forum page for the script.
  * Naturally you'll change the paint, just double check that you have all the variables you need.
  */

import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.methods.Game;
import org.rsbot.script.methods.Settings;
import org.rsbot.script.methods.Skills;
import org.rsbot.script.util.Filter;
import org.rsbot.script.wrappers.RSArea;
import org.rsbot.script.wrappers.RSComponent;
import org.rsbot.script.wrappers.RSGroundItem;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPath;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.script.wrappers.RSWeb;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import javax.swing.JSlider;
import java.awt.*;
import javax.imageio.ImageIO;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;


@ScriptManifest(name = "Yozo0o's TzHaar Killa", 
		authors = "Yozo0o",
		keywords = "",
		version = 2.5,
		description = "Kills TzHaar-Kets and TzHaar-Xils. Visit forum page for more info."
)

public class Yozo0osTzHaarKilla extends Script implements PaintListener, MouseListener {

	//VARIABLES
	private static final int OBELISK_ID[] = { 29940, 29992 };

	private boolean foodIsLobster, foodIsMonkfish,
	foodIsSwordfish, foodIsTuna, foodIsCavefish, foodIsRocktail, foodIsShark, foodIsSalmon,
	foodIsOther, foodIsTrout;

	private boolean foodIsPlainPizza, foodIsAnchovyPizza, foodIsPineapplePizza, foodIsApplePie, foodIsAdmiralPie,
	foodIsSummerPie, foodIsGardenPie, foodIsRedberryPie, foodIsMeatPie, foodIsFishPie, foodIsCake, foodIsChocCake,
	foodIsFishCake;

	private boolean usingSpecAttack = false;

	private boolean fightKets = false, fightXils = false;

	private boolean stealMonsters = false;

	private boolean isSummoning = false;

	private boolean searchForFood = false;

	private int foodWithdrawAmount;

	private boolean usingSuperDef = false;
	private boolean usingSuperAtt = false;
	private boolean usingSuperStr = false;
	private boolean usingCombatPot = false;
	private boolean usingExtremeAtt = false;
	private boolean usingExtremeDef = false;
	private boolean usingExtremeStr = false;

	private boolean pickObbyItems = true, pickOnyxBoltTips = true,
	pickGems = false, pickObbyCharms = false, pickEffigy = true;

	private int hpPercent;
	private int mouseSpeed;
	private int emptySpaces;
	private boolean foodWithdraw = true;
	private int specAttackPercent;
	private int familiarID;

	private boolean running = false;

	//PAINT VARIABLES
	private String status;

	private int startXpStr;
	private int strXpGained = 0;
	private int strXpPerHour = 0;

	private int startXpAtt;
	private int attXpGained = 0;
	private int attXpPerHour = 0;

	private int startXpDef;
	private int defXpGained = 0;
	private int defXpPerHour = 0;

	private int startXpCon;
	private int conXpGained = 0;
	private int conXpPerHour = 0;

	private int totalXpGained = 0;

	private int tokkulGained = 0;

	private int charmsGained = 0;

	private int boltTipsGained = 0;
	private int maulsGained = 0;
	private int shieldsGained = 0;
	private int swordsGained = 0;
	private int knivesGained = 0;
	private int capesGained = 0;
	private int ringsGained = 0;
	private int macesGained = 0;
	private int obbyItemsGained = 0;

	private int tokkulPerHour = 0;

	private int charmsPerHour = 0;

	private int boltTipsPerHour = 0;

	private int boltTipPrice;
	private int boltTipProfit;

	private int maulPrice;
	private int shieldPrice;
	private int swordPrice;
	private int knifePrice;
	private int capePrice;
	private int ringPrice;
	private int macePrice;

	private int maulProfit;
	private int shieldProfit;
	private int swordProfit;
	private int knifeProfit;
	private int capeProfit;
	private int ringProfit;
	private int maceProfit;

	private int obbyProfit;

	private int sapphiresGained = 0;
	private int emeraldsGained = 0;
	private int rubysGained = 0;
	private int diamondsGained = 0;
	private int gemsGained = 0;

	private int gemsPerHour;

	private int sapphirePrice;
	private int emeraldPrice;
	private int rubyPrice;
	private int diamondPrice;

	private int sapphireProfit;
	private int emeraldProfit;
	private int rubyProfit;
	private int diamondProfit;

	private int gemProfit;

	private int profitGained = 0;
	private int profitPerHour = 0;

	private int effigysGained = 0;

	public long startTime = 0;
	public long millis = 0;
	public long hours = 0;
	public long minutes = 0;
	public long seconds = 0;

	//AREAS/TILES
	private static final RSTile AREA_TILE_1 = new RSTile(2448, 5167), AREA_TILE_2 = new RSTile(2469, 5166);

	private static final RSTile BANK_TILE = new RSTile(2447, 5178);
	private static final RSTile SUMMONING_TILE = new RSTile(2439, 5138);
	private RSTile last; // Used in the walking method - courtesy of icnhzabot.

	private static final RSTile IDLE_TILE_1 = new RSTile(2437, 5172);
	private static final RSTile IDLE_TILE_2 = new RSTile(2460, 5125); //These two are tiles that the bot gets stuck on for some reason

	private static final RSArea TZ_HAAR_WEST = new RSArea(new RSTile(2424, 5181), new RSTile(2471, 5121));
	private static final RSArea TZ_HAAR_EAST = new RSArea(new RSTile(2471, 5181), new RSTile(2532, 5121));

	private boolean isInTzHaarArea(RSNPC npc){
		if(TZ_HAAR_WEST.contains(npc.getLocation()) || TZ_HAAR_EAST.contains(npc.getLocation())){
			if(npc.getLocation().getX() > 2469){
				if(npc.getLocation().getY() < 5142)
					return false;
			} else
				return true;
		}
		return false;
	}

	//NPCS
	private static final int KET_ID_1 = 2614, KET_ID_2 = 2615, KET_ID_3 = 2616;
	private static final int XIL_ID_1 = 2606, XIL_ID_2 = 2607, XIL_ID_3 = 2608;
	private static final int BANKER_ID = 2619;

	//FOOD
	private int foodID;
	private static final int ROCKTAIL_ID = 15272;
	private static final int CAVEFISH_ID = 15266;
	private static final int MONKFISH_ID = 7946;
	private static final int SWORDFISH_ID = 373;
	private static final int TUNA_ID = 361;
	private static final int LOBSTER_ID = 379;
	private static final int SHARK_ID = 385;
	private static final int SALMON_ID = 329;
	private static final int TROUT_ID = 333;
	private static final int POTATO_ID = 7054;

	private static final int[] OTHER_FOOD_IDS = {
		//Plain Pizza 
		2289, 2291,
		//Meat Pizza
		2293, 2295,
		//Anchovy Pizza
		2297, 2299,
		//Pineapple Pizza
		2301, 2303,
		//Apple Pie
		2323, 2335,
		//Admiral Pie
		7198, 7200,
		//Garden Pie
		7178, 7180,
		//Summer Pie
		7218, 7220,
		//Wild Pie
		7208, 7210,
		//Redberry Pie
		2325, 2333,
		//Meat Pie
		2327, 2331,
		//Fish Pie
		7188, 7190,
		//Cake
		1891, 1893, 1895,
		//Choclate Cake
		1897, 1899, 1901, 
		//Fish Cake
		7530
	};

	private static final int PLAIN_PIZZA_ID = 2289;
	private static final int ANCHOVY_PIZZA_ID = 2293;
	private static final int PINEAPPLE_PIZZA_ID = 2301;
	private static final int APPLE_PIE_ID = 2323;
	private static final int ADMIRAL_PIE_ID = 7198;
	private static final int GARDEN_PIE_ID = 7178;
	private static final int SUMMER_PIE_ID = 7218;
	private static final int REDBERRY_PIE_ID = 2325;
	private static final int MEAT_PIE_ID = 2327;
	private static final int FISH_PIE_ID = 7188;
	private static final int CAKE_ID = 1891;
	private static final int chocCAKE_ID = 1897;
	private static final int FISH_CAKE_ID = 7530;

	//ITEMS
	private static final int TOKKUL_ID = 6529;
	private static final int OBBY_CHARM_ID = 12168;
	private static final int[] OBBY_ITEM_IDS = { /*maul*/6528, /*shield*/6524, /*sword*/6523, /*knife*/6525,
		/*mace*/6527, /*ring*/6522, /*cape*/6568 };
	private static final int[] GEM_IDS = { 1617, 1619, 1621, 1623 };
	private static final int ONYX_BOLT_TIP_ID = 9194;
	private static final int EFFIGY_ID = 18778;

	private final int[] nonDeposit = 
	{
			foodID,
			TOKKUL_ID,
			ONYX_BOLT_TIP_ID,
			/*ring*/
			6522,
			/*potions*/
			2440, 157, 159, 161,
			2442, 163, 165, 167,
			2436, 145, 147, 149,
			9739, 9741, 9743, 9745,
			15308, 15309, 15310, 15311,
			15312, 15313, 15314, 15315,
			15316, 15317, 15318, 15319
	};

	private static final int RARE_TIMES[] = 
	{ 
		1216, 9342, 20667, 6686,
		2364, 9143, 892, 533,
		1392, 574, 570, 2999,
		258, 3001, 270, 454, 
		452, 2362, 7937, 372, 
		384, 5315, 5316, 5289, 
		1516, 1445, 1443, 1441 
	};

	//POTIONS
	private static final int[] SUPER_STRENGTH = { 2440, 157, 159, 161 };
	private static final int[] SUPER_DEFENCE = { 2442, 163, 165, 167 };
	private static final int[] SUPER_ATTACK = { 2436, 145, 147, 149 };
	private static final int[] COMBAT_POTS = { 9739, 9741, 9743, 9745 };

	private static final int[] EXTREME_ATT = { 15308, 15309, 15310, 15311 };
	private static final int[] EXTREME_STR = { 15312, 15313, 15314, 15315  };
	private static final int[] EXTREME_DEF = { 15316, 15317, 15318, 15319 };

	private static final int[] POTION_DROPS = { 149, 167, 161 };
	private static final int[] FOOD_DROPS = { POTATO_ID, LOBSTER_ID };
	private static final int VIAL_ID = 229;

	//METHODS
	public boolean onStart() {

		createGUI();

		log("Loading prices, please wait...");
		try {
			loadPrices();
		} catch(NullPointerException e){
			log("Prices weren't successfully loaded - starting anyway...");
		}
		log("Prices successfully loaded.");

		setMouseSpeed();
		getSelectedFoodID();

		startXpAtt = skills.getCurrentExp(Skills.ATTACK);
		startXpStr = skills.getCurrentExp(Skills.STRENGTH);
		startXpDef = skills.getCurrentExp(Skills.DEFENSE);
		startXpCon = skills.getCurrentExp(Skills.CONSTITUTION);

		startTime = System.currentTimeMillis();

		if(!foodWithdraw){
			if(pickObbyCharms){
				emptySpaces = 10;
			} else {
				emptySpaces = 6;
			}
		}

		new CameraAntiBan();
		return true;
	}

	public void onFinish(){
		if(!show){
			show = true;
			sleep(200, 400);
		}
		running = false;
		env.saveScreenshot(true);
		log("Thank-you for using this script.");
		log("If you would like to donate, make suggestions, or report bugs, please visit the forum page.");
		Toolkit.getDefaultToolkit().beep();
	}

	private void loadPrices(){
		sapphirePrice = grandExchange.lookup(1623).getGuidePrice();
		emeraldPrice = grandExchange.lookup(1621).getGuidePrice();
		rubyPrice = grandExchange.lookup(1619).getGuidePrice();
		diamondPrice = grandExchange.lookup(1617).getGuidePrice();

		maulPrice = grandExchange.lookup(6528).getGuidePrice();
		shieldPrice = grandExchange.lookup(6524).getGuidePrice();
		capePrice = grandExchange.lookup(6568).getGuidePrice();
		swordPrice = grandExchange.lookup(6523).getGuidePrice();
		knifePrice = grandExchange.lookup(6525).getGuidePrice();
		ringPrice = grandExchange.lookup(6522).getGuidePrice();
		macePrice = grandExchange.lookup(6527).getGuidePrice();

		boltTipPrice = grandExchange.lookup(9194).getGuidePrice();
	}

	private void setMouseSpeed(){
		mouse.setSpeed(random((mouseSpeed - 1), (mouseSpeed + 2)));
	}

	private void getSelectedFoodID(){
		if(foodIsTuna){
			foodID = TUNA_ID;
		} else if(foodIsLobster){
			foodID = LOBSTER_ID;
		} else if(foodIsRocktail){
			foodID = ROCKTAIL_ID;
		} else if(foodIsCavefish){
			foodID = CAVEFISH_ID;
		} else if(foodIsShark){
			foodID = SHARK_ID;
		} else if(foodIsMonkfish){
			foodID = MONKFISH_ID;
		} else if(foodIsSwordfish){
			foodID = SWORDFISH_ID;
		} else if(foodIsSalmon){
			foodID = SALMON_ID;
		} else if (foodIsTrout){
			foodID = TROUT_ID;
		} else if(foodIsPlainPizza){
			foodID = PLAIN_PIZZA_ID;
		} else if(foodIsAnchovyPizza){
			foodID = ANCHOVY_PIZZA_ID;
		} else if(foodIsPineapplePizza){
			foodID = PINEAPPLE_PIZZA_ID;
		} else if(foodIsApplePie){
			foodID = APPLE_PIE_ID;
		} else if(foodIsRedberryPie){
			foodID = REDBERRY_PIE_ID;
		} else if(foodIsMeatPie){
			foodID = MEAT_PIE_ID;
		} else if(foodIsFishPie){
			foodID = FISH_PIE_ID;
		} else if(foodIsCake){
			foodID = CAKE_ID;
		} else if(foodIsChocCake){
			foodID = chocCAKE_ID;
		} else if(foodIsFishCake){
			foodID = FISH_CAKE_ID;
		} else if(foodIsAdmiralPie){
			foodID = ADMIRAL_PIE_ID;
		} else if(foodIsGardenPie){
			foodID = GARDEN_PIE_ID;
		} else if(foodIsSummerPie){
			foodID = SUMMER_PIE_ID;
		}
	}

	private boolean canFight(){
		if(!inventory.contains(foodID) || getMyPlayer().getInteracting() != null && !getMyPlayer().isIdle())
			return false;
		else
			return true;
	}

	private RSNPC getKet(){
		status = "Searching for ket.";
		RSNPC ket;
		if(!stealMonsters){
			ket = npcs.getNearest(new Filter<RSNPC>(){
				/*
				 * I'm sure you've used a filter before but if you haven't then PM me.
				 */
				public boolean accept(RSNPC npc) {
					if (npc.getID() == KET_ID_1 || npc.getID() == KET_ID_2 || npc.getID() == KET_ID_3) {
						if (npc.getInteracting() != null || npc.isInCombat() || !isInTzHaarArea(npc))
							return false;

						return true;
					}

					return false;
				}
			});
			return ket;
		} else if(stealMonsters){
			ket = npcs.getNearest(new Filter<RSNPC>(){
				public boolean accept(RSNPC npc) {
					if (npc.getID() == KET_ID_1 || npc.getID() == KET_ID_2 || npc.getID() == KET_ID_3) {
						if (npc.getHPPercent() < 70 || !isInTzHaarArea(npc))
							return false;

						return true;
					}

					return false;
				}
			});
			return ket;
		} else {
			return null;
		}
	}

	private void fightKet(RSNPC ket){
		status = "Fighting ket.";
		if(!getMyPlayer().isInCombat() && getMyPlayer().getInteracting() == null && getMyPlayer().isIdle()){
			if(ket != null){
				if(calc.distanceTo(ket) > 15){
					walk(ket.getLocation(), 3);
					sleep(1000, 2000);
				}
				if(ket.isOnScreen()){
					pickUpItems();
					ket.interact("Attack " + ket.getName());
					sleep(800, 1200);
				} else if(!ket.isOnScreen()){
					pickUpItems();
					camera.turnTo(ket, 10);
					walk(ket.getLocation(), 2);
					sleep(500, 800);
					ket.interact("Attack " + ket.getName());
				}
			} else{
				while(calc.distanceTo(AREA_TILE_1) > 15){
					walk(AREA_TILE_1, random(5, 10));
					sleep(random(500, 1000));
				}
			}
		}
	}

	private RSNPC getXil(){
		status = "Searching for xil.";
		RSNPC xil;
		if(!stealMonsters){
			xil = npcs.getNearest(new Filter<RSNPC>(){
				public boolean accept(RSNPC npc) {
					if (npc.getID() == XIL_ID_1 || npc.getID() == XIL_ID_2 || npc.getID() == XIL_ID_3) {
						if (npc.getInteracting() != null || npc.isInCombat() || !isInTzHaarArea(npc))
							return false;

						return true;
					}

					return false;
				}
			});
			return xil;
		} else if(stealMonsters){
			xil = npcs.getNearest(new Filter<RSNPC>(){
				public boolean accept(RSNPC npc) {
					if (npc.getID() == XIL_ID_1 || npc.getID() == XIL_ID_2 || npc.getID() == XIL_ID_3) {
						if (npc.getHPPercent() < 70 || !isInTzHaarArea(npc))
							return false;

						return true;
					}

					return false;
				}
			});
			return xil;
		} else {
			return null;
		}
	}

	private void fightXil(RSNPC xil){
		status = "Fighting xil.";
		if(!getMyPlayer().isInCombat() && getMyPlayer().getInteracting() == null && getMyPlayer().isIdle()){
			if(xil!= null){
				if(calc.distanceTo(xil) > 15){
					walk(xil.getLocation(), 3);
					sleep(1000, 2000);
				}
				if(xil.isOnScreen()){
					pickUpItems();
					xil.interact("Attack " + xil.getName());
					sleep(500, 800);
				} else if(!xil.isOnScreen()){
					pickUpItems();
					camera.turnTo(xil, 10);
					sleep(100, 300);
					if(xil.isOnScreen()){
						xil.interact("Attack " + xil.getName());
					} else {
						walk(xil.getLocation(), 2);
						sleep(500, 800);
						xil.interact("Attack " + xil.getName());
					}
				}
			} else{
				while(calc.distanceTo(AREA_TILE_1) > 15){
					walk(AREA_TILE_1, random(5, 10));
					sleep(random(500, 1000));
				}
			}
		}
	}

	private void fight(){
		RSNPC ket = getKet();
		RSNPC xil = getXil();
		if(!getMyPlayer().isInCombat() && getMyPlayer().getInteracting() == null && getMyPlayer().isIdle()){
			if(ket != null && xil != null){
				if(fightKets && fightXils){
					if(calc.distanceTo(ket.getLocation()) > calc.distanceTo(xil.getLocation())){
						pickUpItems();
						fightXil(xil);
					} else {
						pickUpItems();
						fightKet(ket);
					}
				}
			}
			if(ket != null){
				if(fightKets && !fightXils){
					pickUpItems();
					fightKet(ket);
				}
			}
			if(xil != null){
				if(fightXils && !fightKets){
					pickUpItems();
					fightXil(xil);
				}
			}
		}
	}

	private void eatFood(){
		if(inventory.contains(POTATO_ID)){
			status = "Eating " + inventory.getItem(POTATO_ID).getName() + ".";
			inventory.getItem(POTATO_ID).doClick(true);
			sleep(800, 1000);
			return;
		} else if(inventory.contains(LOBSTER_ID) && LOBSTER_ID != foodID){
			status = "Eating " + inventory.getItem(LOBSTER_ID).getName() + ".";
			inventory.getItem(LOBSTER_ID).doClick(true);
			sleep(800, 1000);
			return;
		} else if(foodIsOther){
			status = "Eating " + inventory.getItem(OTHER_FOOD_IDS).getName() + ".";
			if(inventory.containsOneOf(OTHER_FOOD_IDS)){
				inventory.getItem(OTHER_FOOD_IDS).doClick(true);
				sleep(800, 1000);
			}
		} else {
			status = "Eating " + inventory.getItem(foodID).getName() + ".";
			if(inventory.contains(foodID)){
				inventory.getItem(foodID).doClick(true);
				sleep(800, 1000);
			}
		}
	}

	private boolean haveFood(){
		if(foodIsOther){
			if(inventory.containsOneOf(OTHER_FOOD_IDS) || inventory.containsOneOf(FOOD_DROPS)){
				return true;
			}
		} else {
			if(inventory.contains(foodID) || inventory.containsOneOf(FOOD_DROPS)){
				return true;
			}
		}
		return false;
	}

	private boolean needToEat(){
		if(combat.getHealth() < hpPercent){
			return true;
		} else
			return false;
	}

	private boolean needToBank(){
		if(isSummoning && !summoning.isFamiliarSummoned() && !inventory.contains(familiarID)){
			return true;
		}
		if(foodIsOther){
			if(inventory.isFull() || !inventory.containsOneOf(OTHER_FOOD_IDS)){
				return true;
			}
		} else if(inventory.isFull() || (!inventory.contains(foodID) && !inventory.containsOneOf(FOOD_DROPS))){
			if(getMyPlayer().isInCombat() && getMyPlayer().getInteracting() != null && combat.getHealth() > 45){
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean isAtBank(){
		if(calc.distanceTo(BANK_TILE) < 7){
			return true;
		} else
			return false;
	}

	private void bank(){
		status = "Banking.";
		RSNPC banker = npcs.getNearest(BANKER_ID);
		banker.interact("Bank " + banker.getName());
		sleep(random(1000, 2000));
		if(bank.isOpen()){
			status = "Banking - Depositing.";
			bank.depositAllExcept(nonDeposit);
			sleep(random(500, 1000));
			status = "Banking - Withdrawing.";
			withdrawFood();
			sleep(random(300, 600));
			if(isSummoning){
				if(needToSummon()){
					if(!inventory.contains(familiarID)){
						if(bank.getItem(familiarID) != null){
							bank.withdraw(familiarID, 1);
						} else
							log("Out of familiar pouches - continuing without...");
					}
				}
			}
			if(usingPots()){
				withdrawPotions();
				sleep(200, 400);
			}
			if(inventory.contains(foodID)){
				bank.close();
			}
			sleep(300, 600);
			walking.walkTo(AREA_TILE_1);
			sleep(300, 500);
		}
	}

	private void withdrawPotions(){
		if(!bank.isOpen()){
			RSNPC banker = npcs.getNearest(BANKER_ID);
			banker.interact("Bank " + banker.getName());
			sleep(600, 800);
		}
		if(usingSuperDef){
			if(!inventory.containsOneOf(SUPER_DEFENCE)){
				if(bank.getItem(2442) != null){
					bank.withdraw(2442, 1);
				} else {
					log("Out of Super Defence(4), continuing without...");
					usingSuperDef = false;
				}
				sleep(400, 600);
			}
		}
		if(usingSuperAtt){
			if(!inventory.containsOneOf(SUPER_ATTACK)){
				if(bank.getItem(2436) != null){
					bank.withdraw(2436, 1);
				} else {
					log("Out of Super Attack(4), continuing without...");
					usingSuperAtt = false;
				}
				sleep(400, 600);
			}
		}
		if(usingSuperStr){
			if(!inventory.containsOneOf(SUPER_STRENGTH)){
				if(bank.getItem(2440) != null){
					bank.withdraw(2440, 1);
				} else {
					log("Out of Super Strength(4), continuing without...");
					usingSuperStr = false;
				}
				sleep(400, 600);
			}
		}
		if(usingCombatPot){
			if(!inventory.containsOneOf(COMBAT_POTS)){
				if(bank.getItem(9739) != null){
					bank.withdraw(9739, 1);
				} else {
					log("Out of Combat Potion(4), continuing without...");
					usingCombatPot = false;
				}
				sleep(400, 600);
			}
		}
		if(usingExtremeAtt){
			if(!inventory.containsOneOf(EXTREME_ATT)){
				if(bank.getItem(15308) != null){
					bank.withdraw(15308, 1);
				} else {
					log("Out of Extreme Attack(4), continuing without...");
					usingExtremeAtt = false;
				}
			}
		}
		if(usingExtremeStr){
			if(!inventory.containsOneOf(EXTREME_STR)){
				if(bank.getItem(15312) != null){
					bank.withdraw(15312, 1);
				} else {
					log("Out of Extreme Strength(4), continuing without...");
					usingExtremeStr = false;
				}
			}
		}
		if(usingExtremeDef){
			if(!inventory.containsOneOf(EXTREME_DEF)){
				if(bank.getItem(15316) != null){
					bank.withdraw(15316, 1);
				} else {
					log("Out of Extreme Defence(4), continuing without...");
					usingExtremeDef = false;
				}
			}
		}
	}

	private void withdrawFood(){
		if(bank.isOpen() && bank.getItem(foodID) != null){
			if(searchForFood){
				bank.searchItem(bank.getItem(foodID).getName());
				sleep(500, 800);
				if(foodWithdraw){
					bank.withdraw(foodID, foodWithdrawAmount);
				} else
					bank.withdraw(foodID, ((28 - inventory.getCount()) - emptySpaces));
			} else {
				if(foodWithdraw){
					bank.withdraw(foodID, foodWithdrawAmount);
				} else
					bank.withdraw(foodID, ((28 - inventory.getCount()) - emptySpaces));
			}
		} else if(bank.getItem(foodID) == null){
			log("Out of selected food - stopping script.");
			game.logout(true);
			stopScript();
		}
	}

	private boolean isTokkulOnFloor(){
		RSGroundItem tokkul = groundItems.getNearest(TOKKUL_ID);
		if(tokkul != null){
			return true;
		} else
			return false;
	}

	private void pickUpTokkul(){
		RSGroundItem tokkul = groundItems.getNearest(TOKKUL_ID);
		int tokkulCount;
		if(inventory.getItem(TOKKUL_ID) != null){
			tokkulCount = inventory.getItem(TOKKUL_ID).getStackSize();
		} else {
			tokkulCount = 0;
		}
		if(tokkul != null){
			if(calc.distanceTo(tokkul.getLocation()) > 7){
				while(!isPaused() && calc.distanceTo(tokkul.getLocation()) > 7){
					if(needToEat() && haveFood()){
						eatFood();
					}
					walk(tokkul.getLocation(), 2);
					sleep(500, 800);
				}
				sleep(500, 800);
			}
			if(tokkul.isOnScreen()){
				tokkul.interact("Take " + tokkul.getItem().getName());
				sleep(800, 1200);
			} else if(!tokkul.isOnScreen()){
				camera.turnTo(tokkul.getLocation());
				sleep(random(500, 1000));
				tokkul.interact("Take " + tokkul.getItem().getName());
				sleep(800, 1200);
			}
			tokkulGained = tokkulGained + ((inventory.getItem(TOKKUL_ID).getStackSize()) - tokkulCount);
		}
	}

	private boolean isObbyCharmOnFloor(){
		RSGroundItem obbyCharm = groundItems.getNearest(OBBY_CHARM_ID);
		if(obbyCharm != null){
			return true;
		} else
			return false;
	}

	private void pickUpObbyCharm(){
		RSGroundItem obbyCharm = groundItems.getNearest(OBBY_CHARM_ID);
		int charmCount;
		if(inventory.getItem(OBBY_CHARM_ID) != null){
			charmCount = inventory.getCount(OBBY_CHARM_ID);
		} else {
			charmCount = 0;
		}
		if(obbyCharm != null){
			if(calc.distanceTo(obbyCharm.getLocation()) > 7){
				while(!isPaused() && calc.distanceTo(obbyCharm.getLocation()) > 7){
					if(needToEat() && haveFood()){
						eatFood();
					}
					walk(obbyCharm.getLocation(), 2);
					sleep(500, 800);
				}
				sleep(500, 800);
			}
			if(obbyCharm.isOnScreen()){
				obbyCharm.interact("Take " + obbyCharm.getItem().getName());
				sleep(800, 1200);
			} else if(!obbyCharm.isOnScreen()){
				camera.turnTo(obbyCharm.getLocation());
				sleep(random(500, 1000));
				obbyCharm.interact("Take " + obbyCharm.getItem().getName());
				sleep(800, 1200);
			}
			charmsGained = charmsGained + ((inventory.getCount(OBBY_CHARM_ID)) - charmCount);
		}
	}

	private boolean isObbyItemOnFloor(){
		RSGroundItem obbyItem = groundItems.getNearest(OBBY_ITEM_IDS);
		if(obbyItem != null){
			return true;
		} else
			return false;
	}

	private void countObbyItem(){
		int maulCount;
		int swordCount;
		int knifeCount;
		int ringCount;
		int shieldCount;
		int capeCount;
		int maceCount;

		RSGroundItem obbyItem = groundItems.getNearest(OBBY_ITEM_IDS);

		if(obbyItem != null && obbyItem.getItem() != null && obbyItem.getItem().getName() != null){
			if(obbyItem.getItem().getName().contains("haar-ket-om")){
				if(inventory.getItem(6528) != null){
					maulCount = inventory.getCount(6528);
				} else {
					maulCount = 0;
				}
				pickUpObbyItem(obbyItem);
				sleep(1000, 2000);
				maulsGained = maulsGained + ((inventory.getCount(6528)) - maulCount);
			} else if(obbyItem.getItem().getName().contains("ktz-xil-ak")){
				if(inventory.getItem(6523) != null){
					swordCount = inventory.getCount(6523);
				} else {
					swordCount = 0;
				}
				pickUpObbyItem(obbyItem);
				sleep(1000, 2000);
				swordsGained = swordsGained + ((inventory.getCount(6523)) - swordCount);
			} else if(obbyItem.getItem().getName().contains("ktz-xil-ek")){
				if(inventory.getItem(6525) != null){
					knifeCount = inventory.getCount(6525);
				} else {
					knifeCount = 0;
				}
				pickUpObbyItem(obbyItem);
				sleep(1000, 2000);
				knivesGained = knivesGained + ((inventory.getCount(6525)) - knifeCount);
			} else if(obbyItem.getItem().getName().contains("ktz-xil-ul")){
				if(inventory.getItem(6522) != null){
					ringCount = inventory.getItem(6522).getStackSize();
				} else {
					ringCount = 0;
				}
				pickUpObbyItem(obbyItem);
				sleep(1000, 2000);
				ringsGained = ringsGained + ((inventory.getItem(6522).getStackSize()) - ringCount);
			} else if(obbyItem.getItem().getName().contains("ktz-ket-xil")){
				if(inventory.getItem(6524) != null){
					shieldCount = inventory.getCount(6524);
				} else {
					shieldCount = 0;
				}
				pickUpObbyItem(obbyItem);
				sleep(1000, 2000);
				shieldsGained = shieldsGained + ((inventory.getCount(6524)) - shieldCount);
			} else if(obbyItem.getItem().getName().contains("sidian cape")){
				if(inventory.getItem(6568) != null){
					capeCount = inventory.getCount(6568);
				} else {
					capeCount = 0;
				}
				pickUpObbyItem(obbyItem);
				sleep(1000, 2000);
				capesGained = capesGained + ((inventory.getCount(6568)) - capeCount);
			} else if(obbyItem.getItem().getName().contains("haar-ket-em")){
				if(inventory.getItem(6527) != null){
					maceCount = inventory.getCount(6527);
				} else {
					maceCount = 0;
				}
				pickUpObbyItem(obbyItem);
				sleep(1000, 2000);
				macesGained = macesGained + ((inventory.getCount(6527)) - maceCount);
			}
		}
	}


	private void pickUpObbyItem(RSGroundItem obbyItem){
		if(calc.distanceTo(obbyItem.getLocation()) > 4){
			walking.walkTo(obbyItem.getLocation());
		}
		if(obbyItem != null){
			if(obbyItem.isOnScreen()){
				if(calc.distanceTo(obbyItem.getLocation()) > 7){
					while(!isPaused() && calc.distanceTo(obbyItem.getLocation()) > 7){
						if(needToEat() && haveFood()){
							eatFood();
						}
						walk(obbyItem.getLocation(), 2);
						sleep(500, 800);
					}
					sleep(500, 800);
				}
				obbyItem.interact("Take " + obbyItem.getItem().getName());
				sleep(800, 1200);
			} else if(!obbyItem.isOnScreen()){
				camera.turnTo(obbyItem.getLocation());
				sleep(random(500, 1000));
				obbyItem.interact("Take " + obbyItem.getItem().getName());
				sleep(800, 1200);
			}
		}
	}

	private boolean isBoltTipOnFloor(){
		RSGroundItem boltTip = groundItems.getNearest(ONYX_BOLT_TIP_ID);
		if(boltTip != null){
			return true;
		} else
			return false;
	}

	private void pickUpBoltTip(){
		RSGroundItem boltTip = groundItems.getNearest(ONYX_BOLT_TIP_ID);
		int tipCount;
		if(inventory.getItem(ONYX_BOLT_TIP_ID) != null){
			tipCount = inventory.getItem(ONYX_BOLT_TIP_ID).getStackSize();
		} else {
			tipCount = 0;
		}
		if(boltTip != null){
			if(calc.distanceTo(boltTip.getLocation()) > 7){
				while(!isPaused() && calc.distanceTo(boltTip.getLocation()) > 7){
					if(needToEat() && haveFood()){
						eatFood();
					}
					walk(boltTip.getLocation(), 2);
					sleep(500, 800);
				}
				sleep(500, 800);
			}
			if(boltTip.isOnScreen()){
				boltTip.interact("Take " + boltTip.getItem().getName());
				sleep(800, 1200);
			} else if(!boltTip.isOnScreen()){
				camera.turnTo(boltTip.getLocation());
				sleep(random(500, 1000));
				boltTip.interact("Take " + boltTip.getItem().getName());
				sleep(800, 1200);
			}
			boltTipsGained = boltTipsGained + ((inventory.getItem(ONYX_BOLT_TIP_ID).getStackSize()) - tipCount);
		}
	}

	private boolean isEffigyOnFloor(){
		RSGroundItem effigy = groundItems.getNearest(EFFIGY_ID);
		if(effigy != null){
			return true;
		} else
			return false;
	}

	private void pickUpEffigy(){
		RSGroundItem effigy = groundItems.getNearest(EFFIGY_ID);
		int effigyCount;
		if(inventory.getItem(EFFIGY_ID) != null){
			effigyCount = inventory.getCount(EFFIGY_ID);
		} else {
			effigyCount = 0;
		}
		if(effigy != null){
			if(calc.distanceTo(effigy.getLocation()) > 7){
				while(!isPaused() && calc.distanceTo(effigy.getLocation()) > 7){
					if(needToEat() && haveFood()){
						eatFood();
					}
					walk(effigy.getLocation(), 2);
					sleep(500, 800);
				}
				sleep(500, 800);
			}
			if(effigy.isOnScreen()){
				effigy.interact("Take " + effigy.getItem().getName());
				sleep(500, 800);
			} else if(!effigy.isOnScreen()){
				camera.turnTo(effigy.getLocation());
				sleep(random(500, 1000));
				effigy.interact("Take " + effigy.getItem().getName());
				sleep(800, 1200);
			}
			effigysGained = effigysGained + ((inventory.getCount(EFFIGY_ID)) - effigyCount);
		}
	}


	private boolean isGemOnFloor(){
		RSGroundItem gem = groundItems.getNearest(GEM_IDS);
		if(gem != null && calc.distanceTo(gem.getLocation()) < 15){
			return true;
		} else
			return false;
	}

	private void countUncutGems(){
		RSGroundItem gem = groundItems.getNearest(GEM_IDS);
		int sapphCount;
		int emCount;
		int rubCount;
		int diCount;

		if(gem != null && gem.getItem().getName() != null){
			if(gem.getItem().getName().contains("sapphire")){
				if(inventory.getItem(1623) != null){
					sapphCount = inventory.getCount(1623);
				} else {
					sapphCount = 0;
				}
				pickUpGem(gem);
				sleep(1000, 2000);
				sapphiresGained = sapphiresGained + ((inventory.getCount(1623)) - sapphCount);
			} else if(gem.getItem().getName().contains("emerald")){
				if(inventory.getItem(1621) != null){
					emCount = inventory.getCount(1621);
				} else {
					emCount = 0;
				}
				pickUpGem(gem);
				sleep(1000, 2000);
				emeraldsGained = emeraldsGained + ((inventory.getCount(1621)) - emCount);
			} else if(gem.getItem().getName().contains("ruby")){
				if(inventory.getItem(1619) != null){
					rubCount = inventory.getCount(1619);
				} else {
					rubCount = 0;
				}
				pickUpGem(gem);
				sleep(1000, 2000);
				rubysGained = rubysGained + ((inventory.getCount(1619)) - rubCount);
			} else if(gem.getItem().getName().contains("diamond")){
				if(inventory.getItem(1617) != null){
					diCount = inventory.getCount(1617);
				} else {
					diCount = 0;
				}
				pickUpGem(gem);
				sleep(1000, 2000);
				diamondsGained = diamondsGained + ((inventory.getCount(1617)) - diCount);
			}
		}
	}

	private void pickUpGem(RSGroundItem gem){
		if(gem != null){
			if(calc.distanceTo(gem.getLocation()) > 7){
				while(!isPaused() && calc.distanceTo(gem.getLocation()) > 7){
					if(needToEat() && haveFood()){
						eatFood();
					}
					walk(gem.getLocation(), 2);
					sleep(500, 800);
				}
				sleep(500, 800);
			}
			if(gem.isOnScreen()){
				gem.interact("Take " + gem.getItem().getName());
				sleep(500, 800);
			} else if(!gem.isOnScreen()){
				camera.turnTo(gem.getLocation());
				sleep(random(500, 1000));
				gem.interact("Take " + gem.getItem().getName());
				sleep(800, 1200);
			}
		}
	}

	private boolean isPotionOnFloor(){
		RSGroundItem pot = groundItems.getNearest(POTION_DROPS);
		if(pot != null && calc.distanceTo(pot.getLocation()) < 15){
			if(pot.getItem().getID() == 161){
				if(inventory.containsOneOf(SUPER_STRENGTH) || usingExtremeStr){
					return false;
				}
			} else if(pot.getItem().getID() == 167){
				if(inventory.containsOneOf(SUPER_DEFENCE) || usingExtremeDef){
					return false;
				}
			} else if(pot.getItem().getID() == 149){
				if(inventory.containsOneOf(SUPER_ATTACK) || usingExtremeAtt){
					return false;
				}
			}
			return true;
		} else
			return false;
	}

	private void pickUpPot(){
		RSGroundItem pot = groundItems.getNearest(POTION_DROPS);
		if(pot != null){
			if(calc.distanceTo(pot.getLocation()) > 7){
				while(!isPaused() && calc.distanceTo(pot.getLocation()) > 7){
					if(needToEat() && haveFood()){
						eatFood();
					}
					walk(pot.getLocation(), 2);
					sleep(500, 800);
				}
				sleep(500, 800);
			}
			if(pot.isOnScreen()){
				pot.interact("Take " + pot.getItem().getName());
				sleep(500, 800);
			} else if(!pot.isOnScreen()){
				camera.turnTo(pot.getLocation());
				sleep(random(500, 1000));
				pot.interact("Take " + pot.getItem().getName());
				sleep(800, 1200);
			}
		}
	}

	private boolean isFoodOnFloor(){
		RSGroundItem food = groundItems.getNearest(FOOD_DROPS);
		if(food != null && calc.distanceTo(food.getLocation()) < 15){
			return true;
		} else
			return false;
	}

	private void pickUpFood(){
		RSGroundItem food = groundItems.getNearest(FOOD_DROPS);
		if(food != null){
			if(calc.distanceTo(food.getLocation()) > 7){
				while(!isPaused() && calc.distanceTo(food.getLocation()) > 7){
					if(needToEat() && haveFood()){
						eatFood();
					}
					walk(food.getLocation(), 2);
					sleep(500, 800);
				}
				sleep(500, 800);
			}
			if(food.isOnScreen()){
				food.interact("Take " + food.getItem().getName());
				sleep(500, 800);
			} else if(!food.isOnScreen()){
				camera.turnTo(food.getLocation());
				sleep(random(500, 1000));
				food.interact("Take " + food.getItem().getName());
				sleep(800, 1200);
			}
		}
	}

	private boolean isRareItemOnFloor(){
		RSGroundItem rareItem = groundItems.getNearest(RARE_TIMES);
		if(rareItem != null){
			if(rareItem.getItem().getID() == 9143){
				if(rareItem.getItem().getStackSize() < 10){
					return false;
				}
			}
			return true;
		} else
			return false;
	}

	private void pickUpRareItem(){
		RSGroundItem rareItem = groundItems.getNearest(RARE_TIMES);
		if(rareItem != null){
			if(calc.distanceTo(rareItem.getLocation()) > 7){
				while(!isPaused() && calc.distanceTo(rareItem.getLocation()) > 7){
					if(needToEat() && haveFood()){
						eatFood();
					}
					walk(rareItem.getLocation(), 2);
					sleep(500, 800);
				}
				sleep(500, 800);
			}
			if(rareItem.isOnScreen()){
				rareItem.interact("Take " + rareItem.getItem().getName());
				sleep(500, 800);
			} else if(!rareItem.isOnScreen()){
				camera.turnTo(rareItem.getLocation());
				sleep(random(500, 1000));
				rareItem.interact("Take " + rareItem.getItem().getName());
				sleep(800, 1200);
			}
		}
	}

	private void pickUpItems(){
		if(!inventory.isFull()){
			if(isTokkulOnFloor()){
				status = "Picking up tokkul.";
				while(!isPaused() && isTokkulOnFloor()){
					pickUpTokkul();
				}
			}
			if(pickObbyCharms){
				if(isObbyCharmOnFloor()){
					status = "Picking up charm.";
					while(!isPaused() && isObbyCharmOnFloor()){
						pickUpObbyCharm();
					}
				}
			}
			if(pickObbyItems){
				if(isObbyItemOnFloor()){
					status = "Picking up obby.";
					while(!isPaused() && isObbyItemOnFloor()){
						countObbyItem();
					}
				}
			}
			if(pickOnyxBoltTips){
				if(isBoltTipOnFloor()){
					status = "Picking up bolt tip.";
					while(!isPaused() && isBoltTipOnFloor()){
						pickUpBoltTip();
					}
				}
			}
			if(pickEffigy){
				if(isEffigyOnFloor()){
					status = "Picking up effigy.";
					while(!isPaused() && isEffigyOnFloor()){
						pickUpEffigy();
					}
				}
			}
			if(pickGems){
				if(isGemOnFloor()){
					status = "Picking up gem.";
					while(!isPaused() && isGemOnFloor()){
						countUncutGems();
					}
				}
			}
			if(isPotionOnFloor()){
				status = "Picking up potion.";
				while(!isPaused() && isPotionOnFloor()){
					pickUpPot();
				}
			}
			if(isFoodOnFloor() && !inventory.isFull()){
				pickUpFood();
			}
			if(isRareItemOnFloor()){
				pickUpRareItem();
			}
		}
	}

	private int getSpecialPercent(){
		return settings.getSetting(300) / 10;
	}

	@SuppressWarnings("deprecation")
	private void enableSpecial(){
		if(settings.getSetting(Settings.SETTING_SPECIAL_ATTACK_ENABLED) == 0){
			status = "Enabling special.";
			RSComponent child = interfaces.getComponent(884, 4);
			if(game.getCurrentTab() != Game.TAB_ATTACK)
				game.openTab(Game.TAB_ATTACK);
			child.doClick();
			while(settings.getSetting(Settings.SETTING_SPECIAL_ATTACK_ENABLED) == 0){
				sleep(600, 800);
			}
		}
	}

	/**
	 * @author - icnhzabot
	 * 
	 * Walks to a destination. Should be used in a loop. v1.1
	 * @param destination  The tile to walk to
	 * @param distance The distance from the tile to stop at
	 * @return True if walked the next tile, false if at destination, else false
	 * 
	 */
	private boolean walk(RSTile destination, int distance) {
		status = "Walking.";
		if (calc.distanceTo(destination) > distance) {
			RSPath path = walking.getPath(destination);
			if (path != null) {
				RSTile next = path.getNext();
				if (next != null) {
					Point point = calc.tileToMinimap(next);
					if (point != null) {
						if (last != null) {
							if (getMyPlayer().isMoving()) {
								while (calc.distanceTo(last) >= random(3, 7)) {
									sleep(80, 120);
									if (!getMyPlayer().isMoving()) {
										sleep(200, 300);
										if (!getMyPlayer().isMoving()) {
											break;
										}
									}
								}
							}
						}
						int pointX = point.x;
						int pointY = point.y;
						mouse.move(pointX, pointY, random(1, 20), random(1, 20));
						point = calc.tileToMinimap(next);
						if (point.x == -1 || point.y == -1) {
							while (!walking.isLocal(next)) {
								sleep(80, 120);
								point = calc.tileToMinimap(next);
							}
						}
						mouse.click(point, true);
						last = next;
						if (next.equals(destination)
								|| last.equals(destination)) {
							return false;
						}
						return true;
					}
					return false;
				}
				return false;
			}
			return false;
		} else if (calc.distanceTo(destination) <= distance) {
			camera.setAngle(camera.getTileAngle(destination));
			return false;
		} else {
			return false;
		}
	}

	private void walkToBank(){
		pickUpItems();
		while(!isPaused() && calc.distanceTo(BANK_TILE) > 5){
			if(needToEat() && haveFood()){
				eatFood();
			}
			walk(BANK_TILE, 2);
		}
	}

	private void checkRun(){
		if(!walking.isRunEnabled()){
			if(walking.getEnergy() > random(70, 80)){
				walking.setRun(true);
			}
		}
	}

	private boolean needToSummon(){
		if(!summoning.isFamiliarSummoned() && (summoning.getSummoningPoints() > 10)){
			return true;
		} else
			return false;
	}

	private boolean needToRenewPoints(){
		if(summoning.getSummoningPoints() < 10){
			return true;
		} else
			return false;
	}

	private boolean isAtObelisk(){
		if(calc.distanceTo(SUMMONING_TILE) < 6){
			return true;
		} else
			return false;
	}

	private void renewPoints(){
		status = "Renewing summoning points.";
		while(!isPaused() && needToRenewPoints()){
			if(needToEat() && haveFood()){
				eatFood();
			}
			if(isAtObelisk()){
				RSObject obelisk = objects.getNearest(OBELISK_ID);
				Point p = obelisk.getModel().getPoint();
				if(p.getX() != -1 && p.getY() != -1){
					mouse.click(p, true);
				}
				sleep(500, 800);
			}
		}
	}

	private void walkToObelisk(){
		while(!isPaused() && !isAtObelisk()){
			status = "Walking to obelisk.";
			if(needToEat() && haveFood()){
				eatFood();
			}
			RSWeb webToSumm = web.getWeb(getMyPlayer().getLocation(), SUMMONING_TILE);
			if(webToSumm != null){
				webToSumm.step();
				return;
			} else if(webToSumm == null){
				walk(SUMMONING_TILE, 2);
				sleep(200, 400);
				if(!getMyPlayer().isMoving()){
					walking.walkTo(SUMMONING_TILE);
				}
				sleep(500, 800);
			}
		}
	}

	private void summonFamiliar(){
		status = "Summoning familiar.";
		if(inventory.getItem(familiarID) != null){
			inventory.getItem(familiarID).interact("Summon");
			sleep(500, 800);
		}
	}

	private boolean shouldDrinkPot() {
		return
		skills.getCurrentLevel(Skills.STRENGTH) <= skills.getRealLevel(Skills.STRENGTH) + random(3, 5)
		|| skills.getCurrentLevel(Skills.ATTACK) <= skills.getRealLevel(Skills.ATTACK) + random(3, 5)
		|| skills.getCurrentLevel(Skills.DEFENSE) <= skills.getRealLevel(Skills.DEFENSE) + random(3, 5);
	}

	private void drinkPot(String type) {
		if (type.equals("Super Attack")) {
			for (final int id : SUPER_ATTACK) {
				if (skills.getCurrentLevel(Skills.ATTACK) <= skills
						.getRealLevel(Skills.ATTACK) + random(2, 4)) {
					if (inventory.getCount(id) > 0) {
						status = "Drinking Super Attack.";
						inventory.getItem(SUPER_ATTACK).interact("Drink");
						sleep(300, 500);
						while (getMyPlayer().getAnimation() != -1) {
							sleep(random(300, 600));
						}
					}
				}
			}
			return;
		} else if (type.equals("Super Strength")) {
			for (final int id : SUPER_STRENGTH) {
				if (skills.getCurrentLevel(Skills.STRENGTH) <= skills
						.getRealLevel(Skills.STRENGTH) + random(2, 4)) {
					if (inventory.getCount(id) > 0) {
						status = "Drinking Super Strength.";
						inventory.getItem(SUPER_STRENGTH).interact("Drink");
						sleep(300, 500);
						while (getMyPlayer().getAnimation() != -1) {
							sleep(random(300, 600));
						}
					}
				}
			}
			return;
		}else if (type.equals("Super Defence")) {
			for (final int id : SUPER_DEFENCE) {
				if (skills.getCurrentLevel(Skills.DEFENSE) <= skills
						.getRealLevel(Skills.DEFENSE) + random(2, 4)) {
					if (inventory.getCount(id) > 0) {
						status = "Drinking Super Defence.";
						inventory.getItem(SUPER_DEFENCE).interact("Drink");
						sleep(300, 500);
						while (getMyPlayer().getAnimation() != -1) {
							sleep(random(300, 600));
						}
					}
				}
			}
			return;
		}else if (type.equals("Combat Potion")) {
			for (final int id : COMBAT_POTS) {
				if (skills.getCurrentLevel(Skills.STRENGTH) <= skills
						.getRealLevel(Skills.STRENGTH) + random(2, 4)
						|| skills.getCurrentLevel(Skills.ATTACK) <= skills
						.getRealLevel(Skills.ATTACK) + random(2, 4)) {
					if (inventory.getCount(id) > 0) {
						status = "Drinking Combat Potion.";
						inventory.getItem(COMBAT_POTS).interact("Drink");
						sleep(300, 500);
						while (getMyPlayer().getAnimation() != -1) {
							sleep(random(300, 600));
						}
					}
				}
			}
		}else if (type.equals("Extreme Attack")) {
			for (final int id : EXTREME_ATT) {
				if (skills.getCurrentLevel(Skills.ATTACK) <= skills
						.getRealLevel(Skills.ATTACK) + random(2, 4)) {
					if (inventory.getCount(id) > 0) {
						status = "Drinking Extreme Attack.";
						inventory.getItem(EXTREME_ATT).interact("Drink");
						sleep(300, 500);
						while (getMyPlayer().getAnimation() != -1) {
							sleep(random(300, 600));
						}
					}
				}
			}
		}else if (type.equals("Extreme Defence")) {
			for (final int id : EXTREME_DEF) {
				if (skills.getCurrentLevel(Skills.DEFENSE) <= skills
						.getRealLevel(Skills.DEFENSE) + random(2, 4)) {
					if (inventory.getCount(id) > 0) {
						status = "Drinking Extreme Defence.";
						inventory.getItem(EXTREME_DEF).interact("Drink");
						sleep(300, 500);
						while (getMyPlayer().getAnimation() != -1) {
							sleep(random(300, 600));
						}
					}
				}
			}
		}else if (type.equals("Extreme Strength")) {
			for (final int id : EXTREME_STR) {
				if (skills.getCurrentLevel(Skills.STRENGTH) <= skills
						.getRealLevel(Skills.STRENGTH) + random(2, 4)) {
					if (inventory.getCount(id) > 0) {
						status = "Drinking Extreme Strength.";
						inventory.getItem(EXTREME_STR).interact("Drink");
						sleep(300, 500);
						while (getMyPlayer().getAnimation() != -1) {
							sleep(random(300, 600));
						}
					}
				}
			}
		}
	}

	private void drinkPotions(){
		if(inventory.containsOneOf(SUPER_ATTACK)){
			drinkPot("Super Attack");
			sleep(200, 400);
		}
		if(inventory.containsOneOf(SUPER_DEFENCE)){
			drinkPot("Super Defence");
			sleep(200, 400);
		}
		if(inventory.containsOneOf(SUPER_STRENGTH)){
			drinkPot("Super Strength");
			sleep(200, 400);
		}
		if(inventory.containsOneOf(COMBAT_POTS)){
			drinkPot("Combat Potion");
			sleep(200, 400);
		}
		if(inventory.containsOneOf(EXTREME_ATT)){
			drinkPot("Extreme Attack");
			sleep(200, 400);
		}
		if(inventory.containsOneOf(EXTREME_DEF)){
			drinkPot("Extreme Defence");
			sleep(200, 400);
		}
		if(inventory.containsOneOf(EXTREME_STR)){
			drinkPot("Extreme Strength");
			sleep(200, 400);
		}
	}

	private boolean usingPots(){
		if(usingSuperDef || usingSuperStr || usingSuperAtt || usingCombatPot
				|| usingExtremeAtt || usingExtremeDef || usingExtremeStr){
			return true;
		} else
			return false;
	}

	@Override
	public int loop(){
		try{
			checkRun();
			if(inventory.contains(VIAL_ID)){
				inventory.getItem(VIAL_ID).interact("Drop");
			}
			if(getMyPlayer().getInteracting() == null && getMyPlayer().isIdle()){
				pickUpItems();
			}
			if(getMyPlayer().getInteracting() == null && getMyPlayer().isIdle()){
				if(calc.distanceTo(IDLE_TILE_1) < 7 || calc.distanceTo(IDLE_TILE_2) < 7){
					while(calc.distanceTo(AREA_TILE_2) > 15){
						walk(AREA_TILE_2, random(5, 10));
						sleep(random(500, 1000));
					}
				}
			}
			if(shouldDrinkPot()){
				drinkPotions();
			}
			if(isSummoning){
				if(needToSummon() && inventory.contains(familiarID)){
					summonFamiliar();
				} else if(needToRenewPoints()){
					if(isAtObelisk()){
						renewPoints();
					} else if(!isAtObelisk()){
						walkToObelisk();
						sleep(500, 800);
						renewPoints();
					}
				}
			}
			if(getSpecialPercent() >= specAttackPercent){
				if(usingSpecAttack && getMyPlayer().isInCombat() && getMyPlayer().getInteracting() != null && !getMyPlayer().isIdle()){
					enableSpecial();
				}
			}
			if(needToEat() && haveFood()){
				eatFood();
			} else if(needToBank()){
				if(!isAtBank()){
					walkToBank();
				} else if(isAtBank()){
					bank();
				}
			} else if(canFight() && !needToBank()){
				if(getMyPlayer().getInteracting() == null && !getMyPlayer().isInCombat() && getMyPlayer().isIdle()){
					fight();
					if(combat.getHealth() >= (hpPercent + 10)){
						antiBan();
					}
				}
			}
			return random(200, 500);
		} catch(Exception e){
			e.printStackTrace();
			return random(200, 500);
		}
	}

	//ANTIBAN
	class CameraAntiBan extends Thread{
		public CameraAntiBan(){
			this.start();
		}

		public void run(){
			try{
				while(!isPaused() && running){
					if(game.isLoggedIn()){
						switch(random(0, 13)){
						case 0:
						case 4:
							char R = KeyEvent.VK_RIGHT;
							keyboard.pressKey(R);
							Thread.sleep(random(500, 2300));
							keyboard.releaseKey(R);
							break;
						case 10:
						case 14:
							char L = KeyEvent.VK_LEFT;
							keyboard.pressKey(L);
							Thread.sleep(random(500, 2300));
							keyboard.releaseKey(L);
							break;
						case 6:
							if(camera.getPitch() <= 50){
								camera.setPitch(random(80, 100));
							} else{
								camera.setPitch(random(38, 79));
							}
							break;
						case 8:
							if(camera.getPitch() <= 50){
								camera.setPitch(random(80, 100));
							} else{
								camera.setPitch(random(38, 79));
							}
							break;
						default:
							break;
						}
					}
					sleep(random(3600, 5250));
				}
			} catch(Exception ee){
				ee.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void antiBan(){
		int rand = random(1, 20);
		switch(rand){
		case 1:
			mouse.moveRandomly(800);
			sleep(random(500, 800));
			mouse.moveRandomly(800);
			break;
		case 3:
			if(strXpGained > 0){
				skills.doHover(Skills.INTERFACE_STRENGTH);
				sleep(random(1100, 2400));
			} else
				mouse.moveSlightly();
			break;
		case 4:
			if(attXpGained > 0){
				skills.doHover(Skills.INTERFACE_ATTACK);
				sleep(random(1100, 2400));
			} else
				mouse.moveSlightly();
			break;
		case 5:
			if(defXpGained > 0){
				skills.doHover(Skills.INTERFACE_DEFENSE);
				sleep(random(1100, 2400));
			} else
				mouse.moveSlightly();
			break;
		case 6:
			skills.doHover(Skills.INTERFACE_CONSTITUTION);
			sleep(random(1100, 2400));
			break;
		case 7:
			mouse.moveRandomly(800);
			sleep(random(500, 800));
			mouse.moveRandomly(800);
			break;
		case 8:
			mouse.moveRandomly(800);
			break;
		case 9:
			if(usingSpecAttack){
				game.openTab(Game.TAB_ATTACK);
				sleep(1200, 1600);
			}
			break;
		}
	}

	//GUI
	public class TzHaarGUINew extends JFrame {

		private static final long serialVersionUID = 1L;
		private JFrame frame;
		private JTextField pouchIDField;

		JComboBox foodSelect = new JComboBox();
		JSlider hpSlider = new JSlider();
		JCheckBox ketBox = new JCheckBox("TzHaar-Ket (149)");
		JCheckBox xilBox = new JCheckBox("TzHaar-Xil (133)");
		JCheckBox monsterStealBox = new JCheckBox("Attack monsters already in combat? (monster steal)...");
		JCheckBox obbyItemsBox = new JCheckBox("Obsidian Items");
		JCheckBox onyxBoltBox = new JCheckBox("Onyx bolt tips");
		JCheckBox gemBox = new JCheckBox("Uncut gems");
		JCheckBox obbyCharmBox = new JCheckBox("Obsidian charms");
		JCheckBox effigyBox = new JCheckBox("Ancient Effigy");
		JSlider mouseSpeedSlider = new JSlider();
		JPanel specAttackPanel1 = new JPanel();
		JSlider specPercentSlider = new JSlider();
		JCheckBox specAttackBox = new JCheckBox("Use special attack.");
		JPanel specAttackPanel2 = new JPanel();
		JCheckBox summBox = new JCheckBox("Use summoning.");
		JCheckBox searchBox = new JCheckBox("Search for food in bank.");
		JCheckBox combatPotBox = new JCheckBox("Use Combat Potion");
		JCheckBox superStrBox = new JCheckBox("Use Super Strength");
		JCheckBox superAttBox = new JCheckBox("Use Super Attack");
		JCheckBox superDefBox = new JCheckBox("Use Super Defence");
		JCheckBox extremeStrBox = new JCheckBox("Use Extreme Strength");
		JCheckBox extremeDefBox = new JCheckBox("Use Extreme Defence");
		JCheckBox extremeAttBox = new JCheckBox("Use Extreme Attack");
		JSlider specAttackSlider = new JSlider();
		JCheckBox using2H = new JCheckBox("Using 2H?");
		private JTextField foodWithdrawField;

		//METHODS
		public void openURL(String urlText)
		{
			if (Desktop.isDesktopSupported())
			{
				URI uri = URI.create(urlText);
				try
				{
					Desktop.getDesktop().browse(uri);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		private void getFoodWithdrawSettings(){
			try{
				foodWithdrawAmount = Integer.parseInt(foodWithdrawField.getText());
			} catch(NullPointerException e){
				foodWithdraw = false;
			} catch(NumberFormatException e){
				foodWithdraw = false;
			}
		}

		private void getSpecAttackSettings(){
			if(specAttackBox.isSelected()){
				usingSpecAttack = true;
				specAttackPercent = specPercentSlider.getValue();
			}
		}

		private void loadSummoningSettings(){
			if(summBox.isSelected()){
				isSummoning = true;
				try{
					familiarID = Integer.parseInt(pouchIDField.getText());
				} catch(NullPointerException e){
					JOptionPane.showInputDialog("Enter correct data in text field.");
				} catch(NumberFormatException e){
					JOptionPane.showInputDialog("Enter correct data in text field.");
				}
			}
		}

		private void getSelectedItems(){
			if(!obbyItemsBox.isSelected()){
				pickObbyItems = false;
			}
			if(!onyxBoltBox.isSelected()){
				pickOnyxBoltTips = false;
			}
			if(gemBox.isSelected()){
				pickGems = true;
			}
			if(obbyCharmBox.isSelected()){
				pickObbyCharms = true;
			}
			if(!effigyBox.isSelected()){
				pickEffigy = false;
			}
		}

		private void getMouseSpeed(){
			mouseSpeed = mouseSpeedSlider.getValue();
		}

		private void getSelectedFood(){
			if((String)foodSelect.getSelectedItem() == "Tuna"){
				foodIsTuna = true;
			} else if((String)foodSelect.getSelectedItem() == "Lobster"){
				foodIsLobster = true;
			} else if((String)foodSelect.getSelectedItem() == "Swordfish"){
				foodIsSwordfish = true;
			} else if((String)foodSelect.getSelectedItem() == "Monkfish"){
				foodIsMonkfish = true;
			} else if((String)foodSelect.getSelectedItem() == "Shark"){
				foodIsShark = true;
			} else if((String)foodSelect.getSelectedItem() == "Cavefish"){
				foodIsCavefish = true;
			} else if((String)foodSelect.getSelectedItem() == "Rocktail"){
				foodIsRocktail = true;
			} else if((String)foodSelect.getSelectedItem() == "Salmon"){
				foodIsSalmon = true;
			} else if((String)foodSelect.getSelectedItem() == "Trout"){
				foodIsTrout = true;
			} else if((String)foodSelect.getSelectedItem() == "Plain Pizza"){
				foodIsOther = true;
				foodIsPlainPizza = true;
			} else if((String)foodSelect.getSelectedItem() == "Anchovy Pizza"){
				foodIsOther = true;
				foodIsAnchovyPizza = true;
			} else if((String)foodSelect.getSelectedItem() == "Pineapple Pizza"){
				foodIsOther = true;
				foodIsPineapplePizza = true;
			} else if((String)foodSelect.getSelectedItem() == "Apple Pie"){
				foodIsOther = true;
				foodIsApplePie = true;
			} else if((String)foodSelect.getSelectedItem() == "Redberry Pie"){
				foodIsOther = true;
				foodIsRedberryPie = true;
			} else if((String)foodSelect.getSelectedItem() == "Meat Pie"){
				foodIsOther = true;
				foodIsMeatPie = true;
			} else if((String)foodSelect.getSelectedItem() == "Fish Pie"){
				foodIsOther = true;
				foodIsFishPie = true;
			} else if((String)foodSelect.getSelectedItem() == "Cake"){
				foodIsOther = true;
				foodIsCake = true;
			} else if((String)foodSelect.getSelectedItem() == "Chocolate Cake"){
				foodIsOther = true;
				foodIsChocCake = true;
			} else if((String)foodSelect.getSelectedItem() == "Fish Cake"){
				foodIsOther = true;
				foodIsFishCake = true;
			} else if((String)foodSelect.getSelectedItem() == "Admiral Pie"){
				foodIsOther = true;
				foodIsAdmiralPie = true;
			} else if((String)foodSelect.getSelectedItem() == "Garden Pie"){
				foodIsOther = true;
				foodIsGardenPie = true;
			} else if((String)foodSelect.getSelectedItem() == "Summer Pie"){
				foodIsOther = true;
				foodIsSummerPie = true;
			}
		}

		private void getHpPercent(){
			hpPercent = hpSlider.getValue();
		}

		private void getSearchOption(){
			if(searchBox.isSelected()){
				searchForFood = true;
			}
		}

		private void getMonstersToFight(){
			if(ketBox.isSelected()){
				fightKets = true;
			}
			if(xilBox.isSelected()){
				fightXils = true;
			}
		}

		private void getStealMonsters(){
			if(monsterStealBox.isSelected()){
				stealMonsters = true;
			}
		}

		private void getPotionSettings(){
			if(superDefBox.isSelected()){
				usingSuperDef = true;
			}
			if(superAttBox.isSelected()){
				usingSuperAtt = true;
			}
			if(superStrBox.isSelected()){
				usingSuperStr = true;
			}
			if(combatPotBox.isSelected()){
				usingCombatPot = true;
			}
			if(extremeAttBox.isSelected()){
				usingExtremeAtt = true;
			}
			if(extremeDefBox.isSelected()){
				usingExtremeDef = true;
			}
			if(extremeStrBox.isSelected()){
				usingExtremeStr = true;
			}
		}



		/**
		 * Create the application.
		 */
		public TzHaarGUINew() {
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			frame = new JFrame();
			frame.setBounds(100, 100, 531, 475);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.getContentPane().setLayout(null);

			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(10, 91, 495, 256);
			frame.getContentPane().add(tabbedPane);

			JPanel foodTabPane = new JPanel();
			tabbedPane.addTab("Food", null, foodTabPane, null);
			foodTabPane.setLayout(null);

			JPanel foodPanel = new JPanel();
			foodPanel.setBounds(0, 0, 490, 78);
			foodTabPane.add(foodPanel);
			foodPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			JLabel label_1 = new JLabel("Select type of food to use:");
			label_1.setFont(new Font("Calibri", Font.BOLD, 10));
			foodPanel.add(label_1);

			foodSelect.setModel(new DefaultComboBoxModel(new String[] {"Trout", "Salmon", "Tuna", "Lobster", "Swordfish", "Monkfish", "Shark", "Cavefish", "Rocktail", "Plain Pizza", "Anchovy Pizza", "Pineapple Pizza", "Apple Pie", "Admiral Pie", "Garden Pie", "Summer Pie", "Redberry Pie", "Meat Pie", "Fish Pie", "Cake", "Chocolate Cake", "Fish Cake"}));
			foodSelect.setToolTipText("Select food type.");
			foodSelect.setFont(new Font("Calibri", Font.PLAIN, 11));
			foodPanel.add(foodSelect);

			JLabel label_2 = new JLabel("and HP percentage to eat at:");
			label_2.setFont(new Font("Calibri", Font.BOLD, 10));
			foodPanel.add(label_2);

			hpSlider.setValue(75);
			hpSlider.setToolTipText("% to eat at.");
			hpSlider.setSnapToTicks(true);
			hpSlider.setPaintTicks(true);
			hpSlider.setPaintLabels(true);
			hpSlider.setMinorTickSpacing(5);
			hpSlider.setMinimum(20);
			hpSlider.setMajorTickSpacing(20);
			hpSlider.setFont(new Font("Calibri", Font.PLAIN, 10));
			foodPanel.add(hpSlider);

			searchBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			foodPanel.add(searchBox);

			JPanel panel_17 = new JPanel();
			panel_17.setBounds(0, 72, 490, 27);
			foodTabPane.add(panel_17);

			JLabel lblAmountToWithdraw = new JLabel("Amount to withdraw (leave blank if you want script to decide):");
			panel_17.add(lblAmountToWithdraw);

			foodWithdrawField = new JTextField();
			panel_17.add(foodWithdrawField);
			foodWithdrawField.setColumns(10);

			JPanel attackingTabPanel = new JPanel();
			tabbedPane.addTab("Attacking", null, attackingTabPanel, null);
			attackingTabPanel.setLayout(null);

			JPanel panel = new JPanel();
			panel.setBounds(133, 0, 221, 23);
			attackingTabPanel.add(panel);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			JLabel label = new JLabel("Select which monster(s) to fight (choose at least one):");
			label.setFont(new Font("Calibri", Font.BOLD, 10));
			panel.add(label);

			JPanel panel_1 = new JPanel();
			panel_1.setBounds(25, 23, 441, 31);
			attackingTabPanel.add(panel_1);
			panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			ketBox.setSelected(true);
			ketBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_1.add(ketBox);

			xilBox.setSelected(true);
			xilBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_1.add(xilBox);

			monsterStealBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_1.add(monsterStealBox);

			JPanel panel_3 = new JPanel();
			panel_3.setBounds(0, 66, 490, 24);
			attackingTabPanel.add(panel_3);
			panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			JLabel label_3 = new JLabel("Select special attack settings (if your not sure about percentage then choose 100):");
			label_3.setFont(new Font("Calibri", Font.BOLD, 10));
			panel_3.add(label_3);

			JPanel panel_4 = new JPanel();
			panel_4.setBounds(0, 85, 490, 48);
			attackingTabPanel.add(panel_4);

			specAttackBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_4.add(specAttackBox);

			JLabel label_4 = new JLabel("Percent to use at:");
			label_4.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_4.add(label_4);

			specPercentSlider.setValue(100);
			specPercentSlider.setToolTipText("% to eat at.");
			specPercentSlider.setSnapToTicks(true);
			specPercentSlider.setPaintTicks(true);
			specPercentSlider.setPaintLabels(true);
			specPercentSlider.setMinorTickSpacing(5);
			specPercentSlider.setMajorTickSpacing(20);
			specPercentSlider.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_4.add(specPercentSlider);

			JPanel panel_6 = new JPanel();
			panel_6.setBounds(0, 155, 490, 31);
			attackingTabPanel.add(panel_6);

			summBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_6.add(summBox);

			JLabel label_6 = new JLabel("Familiar pouch ID:");
			label_6.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_6.add(label_6);

			pouchIDField = new JTextField();
			pouchIDField.setColumns(10);
			panel_6.add(pouchIDField);

			JPanel panel_7 = new JPanel();
			panel_7.setBounds(0, 133, 490, 24);
			attackingTabPanel.add(panel_7);
			panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			JLabel label_7 = new JLabel("Select summoning settings (special attacks are not supported):");
			label_7.setFont(new Font("Calibri", Font.BOLD, 10));
			panel_7.add(label_7);

			JPanel panel_8 = new JPanel();
			tabbedPane.addTab("Items & Potions", null, panel_8, null);
			panel_8.setLayout(null);

			JPanel panel_9 = new JPanel();
			panel_9.setBounds(146, 5, 197, 23);
			panel_8.add(panel_9);
			panel_9.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			JLabel label_8 = new JLabel("Select which items to pick up (tokkul is default):");
			label_8.setFont(new Font("Calibri", Font.BOLD, 10));
			panel_9.add(label_8);

			JPanel panel_10 = new JPanel();
			panel_10.setBounds(25, 33, 439, 31);
			panel_8.add(panel_10);
			panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			obbyItemsBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_10.add(obbyItemsBox);

			onyxBoltBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_10.add(onyxBoltBox);

			gemBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_10.add(gemBox);

			obbyCharmBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_10.add(obbyCharmBox);

			effigyBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_10.add(effigyBox);

			JPanel panel_11 = new JPanel();
			panel_11.setBounds(0, 81, 490, 24);
			panel_8.add(panel_11);

			JLabel label_9 = new JLabel("Select Potion settings (potions drops from monsters are automatically used):");
			label_9.setFont(new Font("Calibri", Font.BOLD, 10));
			panel_11.add(label_9);

			JPanel panel_12 = new JPanel();
			panel_12.setBounds(0, 116, 490, 57);
			panel_8.add(panel_12);

			superDefBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_12.add(superDefBox);

			superAttBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_12.add(superAttBox);

			superStrBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_12.add(superStrBox);

			combatPotBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_12.add(combatPotBox);

			extremeAttBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_12.add(extremeAttBox);

			extremeDefBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_12.add(extremeDefBox);

			extremeStrBox.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_12.add(extremeStrBox);

			JPanel panel_13 = new JPanel();
			tabbedPane.addTab("Misc", null, panel_13, null);

			JPanel panel_14 = new JPanel();
			panel_13.add(panel_14);
			panel_14.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			JLabel label_10 = new JLabel("Select desired mouse speed (lower is faster):");
			label_10.setFont(new Font("Calibri", Font.BOLD, 10));
			panel_14.add(label_10);

			JPanel panel_15 = new JPanel();
			panel_13.add(panel_15);
			panel_15.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			mouseSpeedSlider.setValue(10);
			mouseSpeedSlider.setSnapToTicks(true);
			mouseSpeedSlider.setPaintTicks(true);
			mouseSpeedSlider.setPaintLabels(true);
			mouseSpeedSlider.setMinimum(5);
			mouseSpeedSlider.setMaximum(12);
			mouseSpeedSlider.setMajorTickSpacing(1);
			mouseSpeedSlider.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_15.add(mouseSpeedSlider);

			JPanel descriptionPanel = new JPanel();
			descriptionPanel.setBounds(0, 21, 522, 58);
			frame.getContentPane().add(descriptionPanel);

			JTextPane description = new JTextPane();
			description.setText("Welcome to Yozo0o's TzHaar Killa. Please fill out this GUI completely and make sure you start with\r\nauto-retaliate turned on. If you have any suggestions / bug reports or if you would like to make a\r\ndonation please refer to the forum page. Oh and make sure you start the script somewhere in the caves.");
			description.setForeground(Color.BLACK);
			description.setFont(new Font("Calibri", Font.PLAIN, 11));
			description.setEditable(false);
			description.setBackground(SystemColor.menu);
			descriptionPanel.add(description);

			JPanel titlePanel = new JPanel();
			titlePanel.setBounds(0, 0, 522, 24);
			frame.getContentPane().add(titlePanel);

			JLabel title = new JLabel("Yozo0o's TzHaar Killa");
			title.setFont(new Font("Calibri", Font.PLAIN, 16));
			titlePanel.add(title);

			JPanel panel_2 = new JPanel();
			panel_2.setBounds(0, 397, 515, 34);
			frame.getContentPane().add(panel_2);

			JButton startButton = new JButton("Start Script!");
			startButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getSelectedFood();
					getHpPercent();
					getMonstersToFight();
					getStealMonsters();
					getSelectedItems();
					getMouseSpeed();
					getSpecAttackSettings();
					loadSummoningSettings();
					getSearchOption();
					getPotionSettings();
					getFoodWithdrawSettings();
					running = true;
					frame.setVisible(false);
				}
			});
			startButton.setToolTipText("Start!");
			startButton.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_2.add(startButton);

			JPanel panel_16 = new JPanel();
			panel_16.setBounds(0, 359, 515, 34);
			frame.getContentPane().add(panel_16);

			JLabel lblSupportMeBy = new JLabel("Support me by visiting my");
			lblSupportMeBy.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_16.add(lblSupportMeBy);

			JButton btnWebsite = new JButton("website.");
			btnWebsite.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openURL("http://www.iFreeGifts.com/");
				}
			});
			btnWebsite.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_16.add(btnWebsite);

			JLabel lblTakeALook = new JLabel("Take a look around and click on the google ads.");
			lblTakeALook.setFont(new Font("Calibri", Font.PLAIN, 10));
			panel_16.add(lblTakeALook);

			frame.setVisible(true);
		}
	}

	TzHaarGUINew g = new TzHaarGUINew();

	private void createGUI(){
		if(SwingUtilities.isEventDispatchThread()){
			g.setVisible(true);
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable(){

					public void run(){
						g.setVisible(true);
					}
				});
			} catch(InvocationTargetException ite){
			} catch(InterruptedException ie){
			}
		}
		sleep(100);
		while (!running) {
			sleep(100);
		}
		if(running){
			g.setVisible(false);
		}
	}

	//PAINT
	//START: Code generated using Enfilade's Easel
	private final RenderingHints antialiasing = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch(IOException e) {
			return null;
		}
	}

	private final Color color1 = new Color(0, 0, 0, 0);
	private final Color color2 = new Color(0, 0, 0, 178);
	private final Color color3 = new Color(153, 153, 153);
	private final Color color4 = new Color(255, 255, 255);
	private final Color color5 = new Color(0, 0, 0);
	private final Color hiderColor = new Color(245, 245, 0, 255);

	private boolean tab1 = false;
	private boolean tab2 = true;
	private boolean tab3 = false;
	private boolean tab4 = false;
	private boolean show = true;

	private final Font font1 = new Font("Arial", 0, 10);
	private final Font font3 = new Font("Arial", 1, 9);


	Rectangle profitTab = new Rectangle(48, 112, 64, 28);
	Rectangle xpTab = new Rectangle(122, 112, 64, 28);
	Rectangle gemTab = new Rectangle(196, 112, 64, 28);
	Rectangle obbyTab = new Rectangle(270, 112, 64, 28);
	Rectangle hider = new Rectangle(473, 30, 18, 13);

	Point p;

	private final Image img1 = getImage("http://scripters.powerbot.org/files/130590/images/TzHaarPaint.png");

	public void onRepaint(Graphics g1) {
		Graphics2D g = (Graphics2D)g1;
		g.setRenderingHints(antialiasing);

		millis = System.currentTimeMillis() - startTime;
		hours = millis / (1000 * 60 * 60);
		millis -= hours * (1000 * 60 * 60);
		minutes = millis / (1000 * 60);
		millis -= minutes * (1000 * 60);
		seconds = millis / 1000;

		attXpGained = skills.getCurrentExp(Skills.ATTACK) - startXpAtt;
		strXpGained = skills.getCurrentExp(Skills.STRENGTH) - startXpStr;
		defXpGained = skills.getCurrentExp(Skills.DEFENSE) - startXpDef;
		conXpGained = skills.getCurrentExp(Skills.CONSTITUTION) - startXpCon;

		totalXpGained = attXpGained + strXpGained + conXpGained + defXpGained;

		attXpPerHour = (int) ((attXpGained) * 3600000D / (System.currentTimeMillis() - startTime));
		strXpPerHour = (int) ((strXpGained) * 3600000D / (System.currentTimeMillis() - startTime));
		defXpPerHour = (int) ((defXpGained) * 3600000D / (System.currentTimeMillis() - startTime));
		conXpPerHour = (int) ((conXpGained) * 3600000D / (System.currentTimeMillis() - startTime));

		tokkulPerHour = (int) ((tokkulGained) * 3600000D / (System.currentTimeMillis() - startTime));
		boltTipsPerHour = (int) ((boltTipsGained) * 3600000D / (System.currentTimeMillis() - startTime));
		charmsPerHour = (int) ((charmsGained) * 3600000D / (System.currentTimeMillis() - startTime));

		gemsGained = sapphiresGained + emeraldsGained + rubysGained + diamondsGained;
		gemsPerHour = (int) ((gemsGained) * 3600000D / (System.currentTimeMillis() - startTime));

		maulProfit = maulsGained * maulPrice;
		shieldProfit = shieldsGained * shieldPrice;
		swordProfit = swordsGained * swordPrice;
		knifeProfit = knivesGained * knifePrice;
		capeProfit = capesGained * capePrice;
		ringProfit = ringsGained * ringPrice;
		maceProfit = macesGained * macePrice;

		obbyItemsGained = maulsGained + shieldsGained + swordsGained + knivesGained + capesGained + ringsGained + macesGained;

		obbyProfit = maulProfit + shieldProfit + swordProfit + knifeProfit + capeProfit + ringProfit + maceProfit;

		sapphireProfit = sapphiresGained * sapphirePrice;
		emeraldProfit = emeraldsGained * emeraldPrice;
		rubyProfit = rubysGained * rubyPrice;
		diamondProfit = diamondsGained * diamondPrice;

		gemProfit = sapphireProfit + emeraldProfit + rubyProfit + diamondProfit;

		boltTipProfit = boltTipsGained * boltTipPrice;

		profitGained = obbyProfit + gemProfit + boltTipProfit;
		profitPerHour = (int) ((profitGained) * 3600000D / (System.currentTimeMillis() - startTime));

		if(!show){
			g.setColor(hiderColor);
			g.fillRect(473, 30, 18, 13);
			//Draw current enemy
		}
		if(show){
			g.drawImage(img1, 17, 20, null);
			g.setColor(color1);
			//HIDE/SHOW RECT
			g.fillRect(473, 30, 18, 13);
			g.setColor(color2);
			//TAB RECT
			g.fillRect(32, 102, 441, 199);
			g.setColor(color3);
			//PROFIT TAB RECT
			g.fillRect(48, 112, 64, 28);
			//XP TAB RECT
			g.fillRect(122, 112, 64, 28);
			//GEM TAB RECT
			g.fillRect(196, 112, 64, 28);
			//OBBY TAB RECT
			g.fillRect(270, 112, 64, 28);
			g.setFont(font1);
			g.setColor(color4);

			//ALWAYS ON-SCREEN
			g.drawString("Runtime: " + hours +":"+ minutes + ":" + seconds, 41, 81);
			g.drawString("Tokkul Gained: " + tokkulGained, 41, 95);
			g.drawString("Tokkul/hour: " + tokkulPerHour, 194, 95);
			g.setFont(font3);
			g.drawString("Status: " + status, 194, 81);
			g.setFont(font1);

			g.setColor(color5);

			//TABS
			g.drawString("Profit", 66, 130);
			g.drawString("Experience", 126, 130);
			g.drawString("Items: Gems", 198, 130);
			g.drawString("Items: Obby", 273, 130);

			g.setColor(color4);

			//PROFIT TAB
			if(tab1){
				g.drawString("Profit Gained: " + profitGained, 46, 166);
				g.drawString("Profit/hour: " + profitPerHour, 46, 183);
				g.drawString("Gem Profit: " + gemProfit, 46, 210);
				g.drawString("Obby Profit: " + obbyProfit, 46, 233);
				g.drawString("Bolt Tip Profit " + boltTipProfit, 46, 260);
			}

			//XP TAB
			if(tab2){
				if(attXpGained > 0){
					g.drawString("Attack Xp/hour: " + attXpPerHour, 46, 183);
					g.drawString("Attack Xp Gained: " + attXpGained, 46, 166);
				}
				if(strXpGained > 0){
					g.drawString("Strength Xp/hour: " + strXpPerHour, 46, 233);
					g.drawString("Strength Xp Gained: " + strXpGained, 46, 210);
				}
				if(defXpGained > 0){
					g.drawString("Defence Xp/hour: " + defXpPerHour, 46, 283);
					g.drawString("Defence Xp Gained: " + defXpGained, 46, 260);
				}
				if(conXpGained > 0){
					g.drawString("Constitution Xp/hour: " + conXpPerHour, 246, 183);
					g.drawString("Constitution Xp Gained: " + conXpGained, 246, 166);
				}
				g.drawString("Total Xp Gained: " + totalXpGained, 246, 283);
			}

			//GEM TAB
			if(tab3){
				g.drawString("Diamonds Gained: " + diamondsGained, 46, 233);
				g.drawString("Rubys Gained: " + rubysGained, 46, 210);
				g.drawString("Emeralds Gained: " + emeraldsGained, 46, 183);
				g.drawString("Sapphires Gained: " + sapphiresGained, 46, 166);
				g.drawString("Gems/hour: " + gemsPerHour, 246, 283);
				g.drawString("Gems Gained: " + gemsGained, 246, 260);
			}

			//OBBY TAB
			if(tab4){
				g.drawString("BoltTips/Hour: " + boltTipsPerHour, 46, 183);
				g.drawString("BoltTips Gained: " + boltTipsGained, 46, 166);
				g.drawString("Chamrs/Hour: " + charmsPerHour, 246, 283);
				g.drawString("Charms Gained: " + charmsGained, 246, 260);
				g.drawString("TotalObbysGained: " + obbyItemsGained, 246, 233);
				if(maulsGained > 0){
					g.drawString("Mauls Gained: " + maulsGained, 46, 210);
				}
				if(shieldsGained > 0){
					g.drawString("Shields Gained: " + shieldsGained, 46, 233);
				}
				if(capesGained > 0){
					g.drawString("Capes Gained: " + capesGained, 46, 260);
				}
				if(ringsGained > 0){
					g.drawString("Rings Gained: " + ringsGained, 46, 283);
				}
				if(macesGained > 0){
					g.drawString("Maces Gained: " + macesGained, 246, 166);
				}
				if(swordsGained > 0){
					g.drawString("Swords Gained: " + swordsGained, 246, 183);
				}
				if(knivesGained > 0){
					g.drawString("Knives Gained: " + knivesGained, 246, 210);
				}
			}
		}
		//Mouse
		g.setColor(Color.YELLOW);
		g.drawLine(mouse.getLocation().x - 6, mouse.getLocation().y,
				mouse.getLocation().x +6, mouse.getLocation().y);
		g.drawLine(mouse.getLocation().x, mouse.getLocation().y - 6,
				mouse.getLocation().x, mouse.getLocation().y + 6);
	}
	//END: Code generated using Enfilade's Easel

	public void drawModel(Graphics g, RSNPC o, Color c){
		if(o != null) {
			Polygon[] model = o.getModel().getTriangles();
			for(Polygon p : model){
				g.setColor(c);
				g.fillPolygon(p);
				g.setColor(c.darker());
				g.drawPolygon(p);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		p = e.getPoint();
		if(profitTab.contains(p)){
			tab1 = true;
			tab2 = false;
			tab3 = false;
			tab4 = false;
		}
		if(xpTab.contains(p)){
			tab1 = false;
			tab2 = true;
			tab3 = false;
			tab4 = false;
		}
		if(gemTab.contains(p)){
			tab1 = false;
			tab2 = false;
			tab3 = true;
			tab4 = false;
		}
		if(obbyTab.contains(p)){
			tab1 = false;
			tab2 = false;
			tab3 = false;
			tab4 = true;
		}
		if(hider.contains(p)){
			if(!show){
				show = true;
			} else if(show){
				show = false;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}