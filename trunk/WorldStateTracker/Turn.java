package WorldStateTracker;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Graphics.Hexagon;
import UnitStateTracker.Unit;

/**
 * Contains all actions a player took during this turn
 */
public class Turn implements Serializable
{
	private static final long serialVersionUID = 5236876667824100930L;
	private ArrayList<Action> actions = null;
    private int number;
    
    public Turn(int number)
    {
    	this.number = number;
    	actions = new ArrayList<Action>();
    }
    public void addMove(Player actor, Hexagon moveStart, Hexagon selectedHex, ArrayList<Unit> limbo, int gameID)
    {
    	try {
			actions.add(new Move(actions.size()-1, actor, moveStart, selectedHex, limbo, gameID));
    	} catch (InsufficientMovementException e1) {
			JOptionPane.showMessageDialog(null,
                    "Error",
                    "One or more selected units do not have enough movement points remaining.",
                    JOptionPane.ERROR_MESSAGE);
		} catch (IllegalMoveException e1) {
			JOptionPane.showMessageDialog(null,
                    "Error",
                    "One or more selected units cannot complete this movement action.",
                    JOptionPane.ERROR_MESSAGE);
		}
    }
    public void removeAction(Action a)
    {
    	actions.remove(a);
    }
    public ArrayList<Action> getActions()
    {
    	return actions;
    }
    public String toString()
    {
    	String temp = "";
    	for (Action a:actions)
    		temp+=a;
    	return temp;
    }
	public int getNumber() 
	{
		return number;
	}
	public void addUnit(Unit unit, Hexagon d) {
		d.addUnit(unit);
		actions.add(new UnitPurchase(actions.size()-1, unit, d));
	}
}
