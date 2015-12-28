package Combat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Graphics.Hexagon;
import UnitStateTracker.Unit;
import WorldStateTracker.Player;
public class Combat
{
	ArrayList<Player> players;
	HashMap<Player,ArrayList<Unit>> combatants;
	TreeMap<Double,Unit> strikeSorter;
	ArrayList<Unit> strikeOrder;
	Hexagon location;
	CombatWindow cGUI;
	
	JFrame combatWindow;
	JPanel combatPanel;
	
	public Combat()
	{
		
	}
	public Combat(Hexagon loc, HashMap<Player,ArrayList<Unit>> com)
	{
		players = new ArrayList<Player>();
		strikeSorter = new TreeMap<Double,Unit>();
		strikeOrder = new ArrayList<Unit>();
		location = loc;
		combatants = com;
		GenOrder();
		CombatWindow c = new CombatWindow(com);
		c.setVisible(true);
		AttackPhase();
	}
	
	public int calcHits(double stat1, double stat2, double trials)
	{
		Random r = new Random();
		int i, hits = 0;
		double randNum1, randNum2, hitCheck;
		for (i = 0; i <= trials; i ++)
		{
			randNum1 = r.nextInt(100)+1;
			randNum2 = r.nextInt(100)+1;
			hitCheck = (stat1-randNum1)-(stat2-randNum2);
			if (hitCheck > 0)
				hits++;
			else if (hitCheck == 0)
			{
				if (r.nextInt(1) == 1)
					hits++;
			}
		}
		return hits;
	}
	
	public void GenOrder()
	{
		for (Player player:combatants.keySet())
		{
			for (Unit unit:combatants.get(player))
			{
				strikeOrder.add(unit);
			}
		}
		Collections.sort(strikeOrder, new FSCSorter());
		for (Unit u:strikeOrder)
		{
			System.err.println(u.getName() + " " + u.getFSC());
		}
	}
	public void AttackPhase()
	{
		/* ArrayList<String> unitNames = new ArrayList<String>;
		for (Unit u:strikeOrder)
			unitNames.add(u.getName());
		CombatGUI GUI = new CombatGUI(location.getName(),unitNames) */
		for (Unit u:strikeOrder)
		{
			u.getTarget().kill(calcHits(u.getTarget().getStoppingPower(),u.getResilience(),calcHits(u.getTarget().getPiercing(),u.getArmour(),calcHits(u.getTarget().getAccuracy(),u.getMobility(),u.getTarget().getUnitStrength()))));
		}
		for (Player player:combatants.keySet())
		{
			for (Unit unit:combatants.get(player))
			{
				System.err.println(unit.getName() + " " + unit.getUnitStrength());
			}
		}
	}
	
	public void acceptCommand(String s)
	{
		
	}
    public ArrayList<Unit> getCombatants() {
        // TODO return an ArrayList of the participants of this combat
        return null;
    }
}