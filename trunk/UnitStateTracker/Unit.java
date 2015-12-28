package UnitStateTracker;
import java.util.ArrayList;
import java.util.HashMap;

import Graphics.Hexagon;
import WorldStateTracker.Player;
import WorldStateTracker.TerrainType;
import WorldStateTracker.TerrainType;
public class Unit
{
	public final int AGGRESSIVE = 0, DEFENSIVE = 1, VANGUARD = 2, NORMAL = 3;
	HashMap<String, Upgrade> upgrades = null;
	private String name;
	HashMap<String, Commander> commanders = null;
	Player owner = null;
	private Hexagon location = null;
	private ArrayList<TerrainType> traversableTerrainTypes = null;
	private Unit target;
	private double unitStrength, attacks, fsc, accuracy, mobility, piercing, resilience, stoppingPower, armour, movement, morale, supplyCapacity, supplyPCombat, supplyPMove, cost;
	private static double uUnitStrength, uAttacks, ufsc, uAccuracy, uMobility, uPiercing, uResilience, uStoppingPower, uArmour, uMovement, uMorale, uMupplyCapacity, uMupplyPCombat, uSupplyPMove, uCost;
		
	public Unit(String name, double unitStrength, double attacks, double accuracy, double mobility, double piercing, double armour, double stoppingPower, double resilience, double fsc)
	{
		this.name = name;
		this.unitStrength = unitStrength;
		this.attacks = attacks;
		this.accuracy = accuracy;
		this.mobility = mobility;
		this.piercing = piercing;
		this.armour = armour;
		this.stoppingPower = stoppingPower;
		this.resilience = resilience;
		this.fsc = fsc;
	}
	public Unit(HashMap<String, Upgrade> upgrades, String name, HashMap<String, Commander> commanders, Player owner, double acc, double mob)
	{
		this.upgrades = upgrades;
		this.name = name;
		this.commanders = commanders;
		this.owner = owner;
		this.accuracy = acc;
		this.mobility = mob;
	}
	public Unit(HashMap<String, Upgrade> upgrades, String name, HashMap<String, Commander> commanders, Player owner, double unitStrength, double attacks, double fsc, double acc, double mob, double pier, double res, double stop, double armour, double movement, double morale, double supplyC, double supplyPC, double supplyPMove, double cost)
	{
		this.upgrades = upgrades;
		this.name = name;
		this.commanders = commanders;
		this.owner = owner;
		this.unitStrength = unitStrength;
		this.attacks = attacks;
		this.fsc = fsc;
		this.accuracy = acc;
		this.mobility = mob;
		this.piercing = pier;
		this.resilience = res;
		this.stoppingPower = stop;
		this.armour = armour;
		this.movement = movement;
		this.morale = morale;
		this.supplyCapacity = supplyC;
		this.supplyPCombat = supplyPC;
		this.supplyPMove = supplyPMove;
		this.cost = cost;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Hexagon getLocation() {
		return location;
	}
	public void setLocation(Hexagon location) {
		this.location = location;
	}
	public double getUnitStrength() {
		return unitStrength;
	}
	public void setUnitStrength(double unitStrength) {
		this.unitStrength = unitStrength;
	}
	public double getAttacks() {
		return attacks;
	}
	public void setAttacks(double attacks) {
		this.attacks = attacks;
	}
	public double getFSC() {
		return fsc;
	}
	public void setFSC(double fsc) {
		this.fsc = fsc;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	public double getMobility() {
		return mobility;
	}
	public void setMobility(double mobility) {
		this.mobility = mobility;
	}
	public double getPiercing() {
		return piercing;
	}
	public void setPiercing(double piercing) {
		this.piercing = piercing;
	}
	public double getResilience() {
		return resilience;
	}
	public void setResilience(double resilience) {
		this.resilience = resilience;
	}
	public double getStoppingPower() {
		return stoppingPower;
	}
	public void setStoppingPower(double stoppingPower) {
		this.stoppingPower = stoppingPower;
	}
	public double getArmour() {
		return armour;
	}
	public void setArmour(double armour) {
		this.armour = armour;
	}
	public double getMovement() {
		return movement;
	}
	public void setMovement(double movement) {
		this.movement = movement;
	}
	public double getMorale() {
		return morale;
	}
	public void setMorale(double morale) {
		this.morale = morale;
	}
	public double getSupplyCapacity() {
		return supplyCapacity;
	}
	public void setSupplyCapacity(double supplyCapacity) {
		this.supplyCapacity = supplyCapacity;
	}
	public double getSupplyPCombat() {
		return supplyPCombat;
	}
	public void setSupplyPCombat(double supplyPCombat) {
		this.supplyPCombat = supplyPCombat;
	}
	public double getSupplyPMove() {
		return supplyPMove;
	}
	public void setSupplyPMove(double supplyPMove) {
		this.supplyPMove = supplyPMove;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public Unit getTarget() {
		return target;
	}
	public void setTarget(Unit target) {
		this.target = target;
	}
	public static double getUUnitStrength() {
		return uUnitStrength;
	}
	public static void setUUnitStrength(double unitStrength) {
		uUnitStrength = unitStrength;
	}
	public static double getUAttacks() {
		return uAttacks;
	}
	public static void setUAttacks(double attacks) {
		uAttacks = attacks;
	}
	public static double getUFSC() {
		return ufsc;
	}
	public static void setUFSC(double ufsc) {
		Unit.ufsc = ufsc;
	}
	public static double getUAccuracy() {
		return uAccuracy;
	}
	public static void setUAccuracy(double accuracy) {
		uAccuracy = accuracy;
	}
	public static double getUMobility() {
		return uMobility;
	}
	public static void setUMobility(double mobility) {
		uMobility = mobility;
	}
	public static double getUPiercing() {
		return uPiercing;
	}
	public static void setUPiercing(double piercing) {
		uPiercing = piercing;
	}
	public static double getUResilience() {
		return uResilience;
	}
	public static void setUResilience(double resilience) {
		uResilience = resilience;
	}
	public static double getUStoppingPower() {
		return uStoppingPower;
	}
	public static void setUStoppingPower(double stoppingPower) {
		uStoppingPower = stoppingPower;
	}
	public static double getUArmour() {
		return uArmour;
	}
	public static void setUArmour(double armour) {
		uArmour = armour;
	}
	public static double getUMovement() {
		return uMovement;
	}
	public static void setUMovement(double movement) {
		uMovement = movement;
	}
	public static double getUMorale() {
		return uMorale;
	}
	public static void setUMorale(double morale) {
		uMorale = morale;
	}
	public static double getUMupplyCapacity() {
		return uMupplyCapacity;
	}
	public static void setUMupplyCapacity(double mupplyCapacity) {
		uMupplyCapacity = mupplyCapacity;
	}
	public static double getUMupplyPCombat() {
		return uMupplyPCombat;
	}
	public static void setUMupplyPCombat(double mupplyPCombat) {
		uMupplyPCombat = mupplyPCombat;
	}
	public static double getUSupplyPMove() {
		return uSupplyPMove;
	}
	public static void setUSupplyPMove(double supplyPMove) {
		uSupplyPMove = supplyPMove;
	}
	public static double getUCost() {
		return uCost;
	}
	public static void setUCost(double cost) {
		uCost = cost;
	}
	public HashMap<String, Upgrade> getUpgrades() {
		return upgrades;
	}
	public void setUpgrades(HashMap<String, Upgrade> upgrades) {
		this.upgrades = upgrades;
	}
	public void kill(int casualties)
	{
		if (casualties > unitStrength)
			unitStrength = 0;
		else
			unitStrength -= casualties;
	}
	public void spendMovePoint() {
        // TODO reduce movement points by one
        
    }
	public void refundMovePoint() {
		// TODO Auto-generated method stub
		
	}
    public int getMovePointsRemaining() {
        // TODO return remaining movement points
        return 0;
    }
    //public boolean canTraverse(TerrainType terrainType) {
        // TODO if this unit can traverse the argument terrain type, return true, otherwise, false
        //return false;
    //}
    public void moveTo(Hexagon endHex) 
    {
        location.removeUnit(this);
        location = endHex;
        location.addUnit(this);
    }
	public boolean canTraverse(TerrainType terrainType) {
		if (traversableTerrainTypes.contains(terrainType))
			return true;
		else
			return false;
	}
}
