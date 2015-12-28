package UnitStateTracker;

import java.util.ArrayList;
import Combat.Rule;

public class Commander
{
	private static ArrayList<Rule> rules = null;
	public Commander(ArrayList<Rule> rules)
	{
		this.rules = rules;
	}
}