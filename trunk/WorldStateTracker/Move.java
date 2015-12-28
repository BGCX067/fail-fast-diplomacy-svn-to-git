package WorldStateTracker;

import java.util.ArrayList;
import java.util.HashMap;

import Graphics.Hexagon;
import UnitStateTracker.Unit;

public class Move extends Action
{
    private Hexagon startHex = null, endHex = null;
    private ArrayList<Unit> unitsMoved = null;
    private HashMap<Unit, Integer> movePointSnapshot = null;
    private int moveNumber;
    private Player actor = null;
    
    public Move(int num, Player actor, Hexagon startHex, Hexagon endHex, ArrayList<Unit> unitsMoved, int moveNumber) throws InsufficientMovementException, IllegalMoveException
    {
    	super (num);
    	unitsMoved = new ArrayList<Unit>();
    	movePointSnapshot = new HashMap<Unit,Integer>();
    	this.actor = actor;
        this.startHex = startHex;
        this.endHex = endHex;
        this.unitsMoved = unitsMoved;
        this.moveNumber = moveNumber;
        for (Unit u:unitsMoved)
        {
            if (u.getMovePointsRemaining()<=0)
                throw new InsufficientMovementException();
            if (!u.canTraverse(endHex.getTerrainType()))
                throw new IllegalMoveException();
        }
        for (Unit u:unitsMoved)
        {
            u.moveTo(endHex);
            u.spendMovePoint();
            movePointSnapshot.put(u, u.getMovePointsRemaining());
        }         
    }
    /**
     * Get the endpoint of this move
     * @return The hexagon this movement action ended on
     */
    public Hexagon getEndHex() {
        return endHex;
    }
    /**
     * Get the startpoint of this move
     * @return The hexagon this move action began on
     */
    public Hexagon getStartHex() {
        return startHex;
    }
    public ArrayList<Unit> getUnitsMoved() {
        return unitsMoved;
    }
    public int getMoveNumber()
    {
        return moveNumber;
    }
    /**
     * Undo this movement - This method attempts to check for conflicting later movements to ensure an illegal undo is not made. 
     * @throws IllegalUndoException 
     */
    public void undo() throws IllegalUndoException
    {
    	for (Unit u:unitsMoved)
    	{
    		if (!endHex.getForcesPresent().get(actor).contains(u)||u.getMovePointsRemaining()!=movePointSnapshot.get(u))
    			throw new IllegalUndoException();
    	}
    	for (Unit u:unitsMoved)
    	{
    		u.moveTo(startHex);
    		u.refundMovePoint();
    	}
    }
    public int compareTo(Move o) 
    {
        if (moveNumber>o.getMoveNumber())
            return 1;
        else if (moveNumber<o.getMoveNumber())
            return -1;
        return 0;
    }
}
