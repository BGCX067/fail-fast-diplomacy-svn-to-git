package Combat;

import java.util.ArrayList;
import java.util.HashMap;

import UnitStateTracker.Unit;
import WorldStateTracker.Player;

public class CombatGUITest 
{
	public static void main (String[]args)
	{
		HashMap<Player,ArrayList<Unit>> com = new HashMap<Player,ArrayList<Unit>>();
		CombatWindow c = new CombatWindow(com);
		c.setVisible(true);
	}
}
