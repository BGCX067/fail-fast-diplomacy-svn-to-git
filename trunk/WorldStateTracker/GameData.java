package WorldStateTracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import Combat.Rule;
import Combat.SandstormEffect;
import UnitStateTracker.Commander;
import UnitStateTracker.Unit;
import UnitStateTracker.Upgrade;

public class GameData implements Serializable
{
	/**
	 * GameData stores a list of all currently present armies and the players who control them
	 */
	private static final long serialVersionUID = -2691507752113681378L;
	private static HashMap<Player, ArrayList<Unit>> armies = new HashMap<Player, ArrayList<Unit>>();
	private static ArrayList<Unit> units = null;
	private static ArrayList<Commander> commanders = null;
	private static ArrayList<WorldMap> maps = null;
	private static ArrayList<Upgrade> upgrades = null;
	private static HashMap<String,Rule> ruleIndex = new HashMap<String,Rule>();
	private static HashMap<String,Unit> unitIndex = new HashMap<String,Unit>();
	private static HashMap<Integer, Player> localPlayerIndex = new HashMap<Integer, Player>();
	private ArrayList<Player> players = null;
	
	private ArrayList<Turn> turns = null;
	
	public static void ruleIndexInit()
	{
		SandstormEffect r = new SandstormEffect();
		ruleIndex.put(r.getName(),r);
	}
	/**
	 * Set up the game data for the current match.
	 * WARNING: This method should only be called ONCE per game.
	 * 			If these values are altered during a match, you will probably get some very strange behavior.
	 * @param u An ArrayList of all of the Units available this match
	 * @param c An ArrayList of all of the Commanders available this match
	 * @param m An ArrayList of all of the WorldMaps available this match
	 * @param up An ArrayList of all of the Upgrades available this match
	 */
	public static void initialise(ArrayList<Unit> u, ArrayList<Commander> c, ArrayList<WorldMap> m, ArrayList<Upgrade> up)
	{
		units = u;
		commanders = c;
		upgrades = up;
		maps = m;
		for (Unit unit:units)
			unitIndex.put(unit.getName(),unit);
	}
	public GameData()
	{
		players = new ArrayList<Player>();
		turns = new ArrayList<Turn>();
	}
	/**
	 * Add a player to the game
	 * @param p The new player
	 * @param u Their initial list of units (This list can be empty)
	 */
	public static void addPlayer(Player p, ArrayList<Unit> u)
	{
		armies.put(p, u);
	}
	/**
	 * Remove a player from the game
	 * @param p The player to be removed
	 */
	public static void removePlayer(Player p)
	{
		armies.remove(p);
	}
	/**
	 * Add a unit to a player's army
	 * @param p The controlling player
	 * @param u The unit to add
	 */
	public static void addUnit(Player p, Unit u)
	{
		armies.get(p).add(u);
	}
	/**
	 * Add a group of units to a player's army
	 * @param p The controlling player
	 * @param u The list of units to add
	 */
	public static void addUnit(Player p, ArrayList<Unit> u)
	{
		armies.get(p).addAll(u);
	}
	/**
	 * Remove a unit from a player's army
	 * @param p The controlling player
	 * @param u The unit to remove
	 */
	public static void removeUnit(Player p, Unit u)
	{
		armies.get(p).remove(u);
	}
	/**
	 * Remove a group of units from a player's army
	 * @param p The controlling player
	 * @param u The list of units to remove
	 */
	public static void removeUnit(Player p, ArrayList<Unit> u)
	{
		armies.get(p).removeAll(u);
	}
	/**
	 * @return A mapping of Players to ArrayLists of units they control
	 */
	public static HashMap<Player, ArrayList<Unit>> getArmies() {
		return armies;
	}
	/**
	 * @return the units
	 */
	public static ArrayList<Unit> getUnits() {
		return units;
	}
	/**
	 * @return the commanders
	 */
	public static ArrayList<Commander> getCommanders() {
		return commanders;
	}
	/**
	 * @return the maps
	 */
	public static ArrayList<WorldMap> getMaps() {
		return maps;
	}
	/**
	 * @return the upgrades
	 */
	public static ArrayList<Upgrade> getUpgrades() {
		return upgrades;
	}
	public static Rule ruleLookup(String name)
	{
		return ruleIndex.get(name);
	}
	public static Unit unitLookup(String name) {
		return unitIndex.get(name);
	}
	public static void setLocalPlayer(int gameID, Player lp)
	{
		localPlayerIndex.put(gameID,lp);
	}
	public static Player getLocalPlayer(int gameID) 
	{
		return localPlayerIndex.get(gameID);
	}
	public void addTurn(Turn t)
	{
		turns.add(t);
	}
	public ArrayList<Turn> getTurns()
	{
		return turns;
	}
	public void setPlayers(ArrayList<Player> alp)
	{
		players = alp;
	}
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
}
