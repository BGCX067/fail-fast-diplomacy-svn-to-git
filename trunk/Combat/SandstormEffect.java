package Combat;

import java.util.ArrayList;

import UnitStateTracker.Unit;

public class SandstormEffect extends Rule {

    public SandstormEffect()
    {
        super("Sandstorm Effect",5);
    }
    public void apply(Combat c)
    {
        ArrayList<Unit> combatants = c.getCombatants();
        for (Unit u:combatants)
            u.setAccuracy(u.getAccuracy() - 10);
    }
    public void remove(Combat c)
    {
    	ArrayList<Unit> combatants = c.getCombatants();
        for (Unit u:combatants)
            u.setAccuracy(u.getAccuracy() + 10);
    }
}
