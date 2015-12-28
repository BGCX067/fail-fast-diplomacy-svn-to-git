package Combat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import Networking.client.NamShubClient;
import UnitStateTracker.Unit;
import WorldStateTracker.Player;

public class SendTargets implements Serializable
{
	public SendTargets()
	{
	}
	public SendTargets(HashMap<Player,ArrayList<Unit>> combatants)
	{
		NamShubClient.toServer(0,"combattargets" + combatants);
	}
}
