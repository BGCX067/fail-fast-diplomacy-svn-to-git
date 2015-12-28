
package Graphics;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Purchase.Purchase;
import WorldStateTracker.WorldMap;

public class HexGrid implements Iterable<Hexagon>
{
	private double height, width;
	private ArrayList<Hexagon> gridList = new ArrayList<Hexagon>();
	private Hexagon currentlySelected = null;
    //TODO Remove this object
	ArrayList<Image> gifArray = new ArrayList<Image>();
	Random ross = new Random();
    /**
     * Create a new hex grid
     * @param x The number of hexes across
     * @param y The number of hexes down
     */
    public HexGrid(int x, int y){this(x,y,100);}
    /**
     * Create a new hex grid
     * @param x The number of hexes across
     * @param y The number of hexes down
     * @param sideLength The length of the sides of each hexagon tile
     */
    public HexGrid(int x, int y, int sideLength)
    {
        height = x*2.3*sideLength*(Math.sqrt(3)/2);
        width = y*1.75*sideLength;
        double yDisp;
        Purchase purchase = new Purchase();
        gifArray = purchase.getGifArray();
        
        for (int i = 1; i <= x; i++)
        {
            if (i%2==0)
                yDisp = sideLength*(Math.sqrt(3)/2);
            else
                yDisp = 0;
            for (int j = 1; j <= y; j++)
            {
                Hexagon h = new Hexagon(new Point(10+i*1.5*sideLength,yDisp+j*2*sideLength*(Math.sqrt(3)/2)), sideLength, gifArray.get(ross.nextInt(3)));
                gridList.add(h);
            }  
        }
 
    }
    public HexGrid(WorldMap w)
    {
    	
    }
    public ArrayList<Hexagon> getList()
    {
        return gridList;
    }
    public Iterator<Hexagon> iterator() 
    {
        return gridList.iterator();
    }
    /**
	 * Get the height required to properly display this hex grid
	 * @return The height of the grid in pixels
	 */
    public double getHeight() {
		return height;
	}
	/**
	 * Get the width required to properly display this hex grid
	 * @return The width of the grid in pixels
	 */
	public double getWidth() {
		return width;
	}
	public Hexagon getCurrentlySelected()
	{
		return currentlySelected;
	}
	public void select(Hexagon h)
	{
		if (currentlySelected != null)
		{
			currentlySelected.deselect();
		}
		h.select();
		currentlySelected = h;
	}
	public void deselect()
	{
		currentlySelected.deselect();
		currentlySelected = null;
	}
}
