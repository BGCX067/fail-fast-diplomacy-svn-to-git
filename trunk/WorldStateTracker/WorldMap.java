package WorldStateTracker;

import java.util.ArrayList;

import Graphics.Hexagon;

public class WorldMap 
{
	private ArrayList<Hexagon> hexes = null;
	private int height, width;
	public WorldMap(ArrayList<Hexagon> hexes, int height, int width)
	{
		this.hexes = hexes;
		this.height = height;
		this.width = width;
	}
	/**
	 * @return the hexes
	 */
	public ArrayList<Hexagon> getHexes() {
		return hexes;
	}
	/**
	 * @param hexes the hexes to set
	 */
	public void setHexes(ArrayList<Hexagon> hexes) {
		this.hexes = hexes;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

}
