package Combat;

import java.util.Comparator;

import UnitStateTracker.Unit;

public class FSCSorter implements Comparator<Unit>
{

	public int compare(Unit u1, Unit u2) {
		if(u1.getFSC() > u2.getFSC())
			return -1;
		else if (u1.getFSC() < u2.getFSC())
			return 1;
		else
			return 0;
	}

}
