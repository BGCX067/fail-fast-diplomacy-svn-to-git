package Purchase;

import java.util.ArrayList;

import UnitStateTracker.Unit;
import WorldStateTracker.GameData;
import WorldStateTracker.Player;
import WorldStateTracker.WorldMap;

public class PurchaseGUITest 
{
	public static void main(String[]args)
	{
		Purchase p = new Purchase();
		ArrayList<Unit> u = new ArrayList<Unit>();
		ArrayList<WorldMap> m = new ArrayList<WorldMap>();
		GameData.initialise(u, p.getCommanderFinal(), m, p.getUpgradeFinal());
		PurchaseGUI gui = new PurchaseGUI(500, new Player("TestPlayer", new ArrayList<Unit>()));
		gui.setVisible(true);
	}
}
