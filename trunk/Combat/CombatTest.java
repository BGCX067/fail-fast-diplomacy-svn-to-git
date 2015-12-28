package Combat;
import java.util.ArrayList;
import java.util.HashMap;

import Graphics.Hexagon;
import UnitStateTracker.Infantry;
import UnitStateTracker.Unit;
import WorldStateTracker.Player;
public class CombatTest extends Combat
{
	public static void main(String[] args)
	{
		Hexagon location = null;
		ArrayList<Unit> cUnits = new ArrayList<Unit>();
		ArrayList<Unit> bUnits = new ArrayList<Unit>();
		ArrayList<Player> players = new ArrayList<Player>();
		HashMap<Player,ArrayList<Unit>> combatants = new HashMap<Player,ArrayList<Unit>>();
		
		Player C, B;
		
		// Name, unitStrength, attacks, accuracy, mobility, piercing, armour, stoppingPower, resilience, FSC
		
		Infantry chris = new Infantry("Chris", 100, 3, 50, 50, 50, 50, 50, 50, 4);
		Infantry chris2 = new Infantry("Chris2", 100, 3, 50, 50, 50, 50, 50, 50, 5);
		cUnits.add(chris);
		cUnits.add(chris2);
		
		C = new Player("C", cUnits);
		combatants.put(C, cUnits);
		players.add(C);
		
		Infantry bruce = new Infantry("Bruce", 100, 3, 50, 50, 50, 50, 50, 50, 7);
		bUnits.add(bruce);
		B = new Player("B", bUnits);
		combatants.put(B, bUnits);
		players.add(B);
		
		chris.setTarget(bruce);
		chris2.setTarget(bruce);
		bruce.setTarget(chris2);
		
		Combat combat = new Combat(location, combatants);
	}
}