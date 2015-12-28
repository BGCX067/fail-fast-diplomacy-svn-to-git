package UnitStateTracker;

import java.util.ArrayList;
import Combat.Rule;

public class Upgrade
{
	private static ArrayList<Rule> rules = null;
	private static double points;
	public Upgrade(ArrayList<Rule> rules, double points)
	{
		this.rules = rules;
		this.points = points;
	}
}