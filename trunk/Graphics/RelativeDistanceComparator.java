package Graphics;

import java.io.Serializable;
import java.util.Comparator;

public class RelativeDistanceComparator implements Comparator<Hexagon>, Serializable
{
	private static final long serialVersionUID = 8286039290782714083L;
	
	private Point clickCoords = null;
	
	/**
	 * @return the current clickCoords
	 */
	public Point getClickCoords() {
		return clickCoords;
	}
	/**
	 * @param clickCoords the coordinates of the point to sort based on relative distance from
	 */
	public void setClickCoords(Point clickCoords) {
		this.clickCoords = clickCoords;
	}
	public RelativeDistanceComparator(){}
	public int compare(Hexagon h1, Hexagon h2) 
	{
		if (new Line(clickCoords,h1.getCenter()).length()<new Line(clickCoords,h2.getCenter()).length())
			return -1;
		else if (new Line(clickCoords,h1.getCenter()).length()>new Line(clickCoords,h2.getCenter()).length())
			return 1;
		return 0;
	}
}
