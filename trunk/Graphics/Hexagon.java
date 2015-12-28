package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;

import UnitStateTracker.Unit;
import WorldStateTracker.Player;
import WorldStateTracker.TerrainType;

public class Hexagon 
{
    private int side = 100;
    private Point center;
    private Point[] points = new Point[6];
    private int[] x = new int[6], y = new int[6];
    private boolean isSelected = false;
    private HashMap<Player,ArrayList<Unit>> forcesPresent = new HashMap<Player,ArrayList<Unit>>();
    private Image image = null;
    private TerrainType terrainType = null;
    private Color colour = Color.GREEN;
    
	/**
     * Sets up a regular hexagon with a default side length of 100
     * @param c The point at the center of the hexagon
     */
    public Hexagon(Point c){this(c, 100);}
    /**
     * Sets up a hexagon regular hexagon
     * @param c The point at the center of the hexagon
     * @param side The length of each side
     * @param image The background image to draw
     */
    public Hexagon(Point c, int side, Image image)
    {
    	this.image = image;
        center = c;
        this.side = side;
        points[0] = new Point(c.getX()-side,c.getY());
        points[1] = new Point(c.getX()-side/2,c.getY()+side*(Math.sqrt(3)/2));
        points[2] = new Point(c.getX()+side/2,c.getY()+side*(Math.sqrt(3)/2));
        points[3] = new Point(c.getX()+side,c.getY());
        points[4] = new Point(c.getX()+side/2,c.getY()-side*(Math.sqrt(3)/2));
        points[5] = new Point(c.getX()-side/2,c.getY()-side*(Math.sqrt(3)/2));
        for (int i = 0; i<6; i++)
        {
            x[i] = (int)points[i].getX();
            y[i] = (int)points[i].getY();
        }
    }
    /**
     * Sets up a hexagon regular hexagon
     * @param c The point at the center of the hexagon
     * @param side The length of each side
     * @param image The background image to draw
     * @param t The TerrainType of this hexagon
     */
    public Hexagon(Point c, int side, Image image, TerrainType t)
    {
    	this.image = image;
    	this.terrainType = t;
        center = c;
        this.side = side;
        points[0] = new Point(c.getX()-side,c.getY());
        points[1] = new Point(c.getX()-side/2,c.getY()+side*(Math.sqrt(3)/2));
        points[2] = new Point(c.getX()+side/2,c.getY()+side*(Math.sqrt(3)/2));
        points[3] = new Point(c.getX()+side,c.getY());
        points[4] = new Point(c.getX()+side/2,c.getY()-side*(Math.sqrt(3)/2));
        points[5] = new Point(c.getX()-side/2,c.getY()-side*(Math.sqrt(3)/2));
        for (int i = 0; i<6; i++)
        {
            x[i] = (int)points[i].getX();
            y[i] = (int)points[i].getY();
        }
    }
    public Hexagon(Point c, int side)
    {
        center = c;
        this.side = side;
        points[0] = new Point(c.getX()-side,c.getY());
        points[1] = new Point(c.getX()-side/2,c.getY()+side*(Math.sqrt(3)/2));
        points[2] = new Point(c.getX()+side/2,c.getY()+side*(Math.sqrt(3)/2));
        points[3] = new Point(c.getX()+side,c.getY());
        points[4] = new Point(c.getX()+side/2,c.getY()-side*(Math.sqrt(3)/2));
        points[5] = new Point(c.getX()-side/2,c.getY()-side*(Math.sqrt(3)/2));
        for (int i = 0; i<6; i++)
        {
            x[i] = (int)points[i].getX();
            y[i] = (int)points[i].getY();
        }
    }
    /**
     * Draws this hexagon on the specified graphics context 
     * @param g The graphics context on which to draw the hexagon
     */
    public void render(Graphics g)
    {
    	if (image!=null)
    	{
    		Shape s = g.getClip();
    		g.setClip(new Polygon(x,y,6));
    		g.drawImage(image, x[0], y[5], null);
    		g.setClip(s);
    	}
    	if (isSelected)
    	{
    		g.setColor(colour);
        	g.drawPolygon(x,y,6);
    	}	
    	else
    	{
    		g.setColor(Color.BLACK);
    		g.drawPolygon(x,y,6);
    	}
    		
        for (Player p:forcesPresent.keySet())
        {
        	g.setColor(p.getColour());
        	g.drawRect((int)center.getX(),(int)center.getY(),(int)center.getX(),(int)center.getY());
        }
    }
    
    
    /**
     * Takes the vertices of this hexagon and creates Line objects to describe this hexagon
     * @return ArrayList of the sides of this hexagon 
     */
    public ArrayList<Line> getLines()
    {
        ArrayList<Line> lines = new ArrayList<Line>();
        for(int i = 0; i<5; i++)
            lines.add(new Line(points[i],points[i+1]));
        lines.add(new Line(points[5],points[0]));
        return lines;
    }
    public Point[] getPoints() {
        return points;
    }
    /*public void setPoints(Point[] points) {
        this.points = points;
    }*/
    public int getSide() {
        return side;
    }
    /*public void setSide(int side) {
        this.side = side;
    }*/
    public int[] getX() {
        return x;
    }
    /*public void setX(int[] x) {
        this.x = x;
    }*/
    public int[] getY() {
        return y;
    }
    public void setY(int[] y) {
        this.y = y;
    }
    public Point getCenter()
    {
        return center;
    }
    public String toString()
    {
        return ("Center of Hexagon: " + center.toString() + ", isSelected: " + isSelected);
    }
    public boolean isSelected()
    {
    	return isSelected;
    }
    public void select()
	{
		isSelected = true;
	}
	public void deselect()
	{
		isSelected = false;
	}
    public TerrainType getTerrainType() {
        return terrainType;
    }
    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }
    public void removeUnit(Unit unit) 
    {
        forcesPresent.get(unit.getOwner()).remove(unit);
    }
    public void addUnit(Unit unit) 
    {
        forcesPresent.get(unit.getOwner()).add(unit); 
    }
	public HashMap<Player, ArrayList<Unit>> getForcesPresent() {
		return forcesPresent;
	}
	public void setForcesPresent(HashMap<Player,ArrayList<Unit>> forcesPresent) {
		this.forcesPresent = forcesPresent;
	}
    public void select(Color c) 
    {
        isSelected = true;
        colour = c;
    }
    /**
	 * @return the terrainType
	 */
	public TerrainType getTerratinType() {
		return terrainType;
	}
	/**
	 * @param terratinType the terratinType to set
	 */
	public void setTerratinType(TerrainType terrainType) {
		this.terrainType = terrainType;
	}
}
