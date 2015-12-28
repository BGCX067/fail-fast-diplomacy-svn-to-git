package WorldStateTracker;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import UnitStateTracker.Unit;

public class Player implements Serializable
{
	private static final long serialVersionUID = -1574092051333668437L;
	private String name = null;
    private ArrayList<Unit> forces = null;
    private Color colour;
    private int number;
    public Player(String name, int number, Color colour, ArrayList<Unit> forces)
    {
        this.name = name;
        this.forces = forces;
        this.colour = colour;
    }
    public Player(String name, ArrayList<Unit> units) {
		this.name = name;
		this.forces = units;
	}
	public String getName()
    {
        return name;
    }
    public ArrayList<Unit> getForces() 
    {
        return forces;
    }
    public void setForces(ArrayList<Unit> forces) 
    {
        this.forces = forces;
    }
    public void addToForces(Unit u)
    {
        forces.add(u);
    }
    public void removeFromForces(Unit u)
    {
        forces.remove(u);
    }
	public Color getColour() {
		return colour;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
}
