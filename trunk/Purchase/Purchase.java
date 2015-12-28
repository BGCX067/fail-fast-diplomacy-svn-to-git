package Purchase;


import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Combat.Rule;
import UnitStateTracker.Armour;
import UnitStateTracker.CapitolShip;
import UnitStateTracker.Commander;
import UnitStateTracker.Gunship;
import UnitStateTracker.Infantry;
import UnitStateTracker.StrikeCraft;
import UnitStateTracker.Unit;
import UnitStateTracker.Upgrade;
import WorldStateTracker.GameData;

// Purchase system
// Bruce Delo

public class Purchase 
{
	//ArrayList<Maps> maps = new ArrayList();
	ArrayList<Unit> units = new ArrayList<Unit>();
	//ArrayList<Commander> commanders = new ArrayList();
	//ArrayList<Upgrade> upgrades = new ArrayList();
	UnitFilter myUnitFilter = new UnitFilter();
	File[] unitArray = null;
	File[] commanderArray = null;
	File[] mapArray = null;
	File[] upgradeArray = null;
	File[] gifArray = null;
	ArrayList<Image> mapTiles = new ArrayList<Image>();
	ArrayList<Upgrade> upgradeArrayFinal= new ArrayList<Upgrade>();
	ArrayList<Commander> commanderArrayFinal= new ArrayList<Commander>();
	
	/**
	 * This is a comment
	 */
	public Purchase()
	{
		String dir = (System.getProperty("user.dir")) + System.getProperty("file.separator")+ "trunk" + System.getProperty("file.separator") + "Graphics";
		System.out.println(System.getProperty("user.dir"));
		System.out.println(dir);
		
		Scanner reader = null;
		File f = new File(dir);
			
			// This is putting all the files from directory f into the arrayLists.
			UnitFilter myUnitFilter = new UnitFilter();
			unitArray = f.listFiles(myUnitFilter);
			commanderArray = f.listFiles(new CommanderFilter());
			mapArray = f.listFiles(new MapFilter());
			upgradeArray = f.listFiles(new upgradeFilter());
			gifArray = f.listFiles(new gifFilter());
			
			
			for(File nf: gifArray)
			{
				try {
					mapTiles.add(ImageIO.read(nf));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(File nf: unitArray)
			{
					try {
						reader = new Scanner(nf);
					} catch (FileNotFoundException e) {	// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String [] unitList = reader.nextLine().split("[|][|]");
					if (unitList[0].equals("Infantry"))
					{
						Infantry.setUMovement(Integer.parseInt(unitList[1]));
						Infantry.setUUnitStrength(Integer.parseInt(unitList[2]));
						Infantry.setUAccuracy(Integer.parseInt(unitList[3]));
						Infantry.setUPiercing(Integer.parseInt(unitList[4]));
						Infantry.setUStoppingPower(Integer.parseInt(unitList[5]));
						Infantry.setUMobility(Integer.parseInt(unitList[6]));
						Infantry.setUResilience(Integer.parseInt(unitList[7]));
						/**
						 * This assumes that the stats are stored in the following order:
						 * [Unit Type][Speed][Strength][Accuracy][Penetration][Stopping Power][Mobility][Armor][Resilience]
						 */
					}				
					else if (unitList[0].equals("Armour"))
					{
						Armour.setUMovement(Integer.parseInt(unitList[1]));
						Armour.setUUnitStrength(Integer.parseInt(unitList[2]));
						Armour.setUAccuracy(Integer.parseInt(unitList[3]));
						Armour.setUPiercing(Integer.parseInt(unitList[4]));
						Armour.setUStoppingPower(Integer.parseInt(unitList[5]));
						Armour.setUMobility(Integer.parseInt(unitList[6]));
						Armour.setUResilience(Integer.parseInt(unitList[7]));
					}
					else if (unitList[0].equals("Gunship"))
					{
						Gunship.setUMovement(Integer.parseInt(unitList[1]));
						Gunship.setUUnitStrength(Integer.parseInt(unitList[2]));
						Gunship.setUAccuracy(Integer.parseInt(unitList[3]));
						Gunship.setUPiercing(Integer.parseInt(unitList[4]));
						Gunship.setUStoppingPower(Integer.parseInt(unitList[5]));
						Gunship.setUMobility(Integer.parseInt(unitList[6]));
						Gunship.setUResilience(Integer.parseInt(unitList[7]));
					}
					else if (unitList[0].equals("StrikeCraft"))
					{
						StrikeCraft.setUMovement(Integer.parseInt(unitList[1]));
						StrikeCraft.setUUnitStrength(Integer.parseInt(unitList[2]));
						StrikeCraft.setUAccuracy(Integer.parseInt(unitList[3]));
						StrikeCraft.setUPiercing(Integer.parseInt(unitList[4]));
						StrikeCraft.setUStoppingPower(Integer.parseInt(unitList[5]));
						StrikeCraft.setUMobility(Integer.parseInt(unitList[6]));
						StrikeCraft.setUResilience(Integer.parseInt(unitList[7]));
					}
					else if (unitList[0].equals("CapitolShip"))
					{
						CapitolShip.setUMovement(Integer.parseInt(unitList[1]));
						CapitolShip.setUUnitStrength(Integer.parseInt(unitList[2]));
						CapitolShip.setUAccuracy(Integer.parseInt(unitList[3]));
						CapitolShip.setUPiercing(Integer.parseInt(unitList[4]));
						CapitolShip.setUStoppingPower(Integer.parseInt(unitList[5]));
						CapitolShip.setUMobility(Integer.parseInt(unitList[6]));
						CapitolShip.setUResilience(Integer.parseInt(unitList[7]));
					}
			}
			/*for (File nf: upgradeArray)
			{
				try 
				{
					reader = new Scanner(nf);
				} 
				catch (FileNotFoundException e) {	// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<Rule> ruleArray = new ArrayList<Rule>();
				String [] upgradeList = reader.nextLine().split("[|][|]");
				int x = Integer.parseInt(upgradeList[0]);
				for (int i=1; i<=x; i++)
				{
					ruleArray.add(GameData.ruleLookup(upgradeList[i]));
				}
				Upgrade upgrade = new Upgrade(ruleArray, Double.parseDouble(upgradeList[upgradeList.length-1]));
				upgradeArrayFinal.add(upgrade);
				/**
				 * For now we're assuming that the text file elements are as follows:
				 * [Number of rules n][rule 1]...[rule n][cost]
				 
				
			}
			for (File nf: commanderArray)
			{
				try 
				{
					reader = new Scanner(nf);
				} 
				catch (FileNotFoundException e) {	// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<Rule> ruleArray = new ArrayList<Rule>();
				String [] commanderList = reader.nextLine().split("[|][|]");
				int x = Integer.parseInt(commanderList[0]);
				for (int i=1; i<=x; i++)
				{
					ruleArray.add(GameData.ruleLookup(commanderList[i]));
				}
				Commander commander = new Commander(ruleArray);
				commanderArrayFinal.add(commander);
				/**
				 * This assumes that the data is stored in the same order as upgrade.
				 *
			}*/
			
	}
	public ArrayList<Image> getGifArray()
	{
		return mapTiles;
	}
	public ArrayList<Upgrade> getUpgradeFinal()
	{
		return upgradeArrayFinal;
	}
	public ArrayList<Commander> getCommanderFinal()
	{
		return commanderArrayFinal;
	}
}

