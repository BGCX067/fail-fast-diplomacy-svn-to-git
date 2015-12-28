package UnitStateTracker;

import java.security.acl.Owner;
import java.util.HashMap;
import WorldStateTracker.Player;

public class Infantry extends Unit
{
	
	public Infantry(String name, double unitStrength, double attacks, double accuracy, double mobility, double piercing, double armour, double stoppingPower, double resilience, double fsc)
	{
		super(name, unitStrength, attacks, accuracy, mobility, piercing, armour, stoppingPower, resilience, fsc);
	}
	public Infantry(HashMap<String, Upgrade> upgrades, String name, HashMap<String, Commander> commanders, Player owner, double unitStrength, double attacks, double fsc, double acc, double mob, double pier, double res, double stop, double armour, double movement, double morale, double supplyC, double supplyPC, double supplyPMove, double cost)
	{
		super(upgrades, name, commanders, owner, unitStrength, attacks, fsc, acc, mob, pier, res, stop, armour, movement, morale, supplyC, supplyPC, supplyPMove, cost);
	}

}