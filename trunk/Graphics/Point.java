package Graphics;

public class Point 
{
    public static final Point ORIGIN = new Point(0,0);
    private double x, y;
    /**
     * Create a new point with coordinates x and y
     * @param x The initial x coordinate
     * @param y The initial y coordinate
     */
    public Point(double x, double y)
    {
        this.x=x;
        this.y=y;
    }
    /**
     * @return The x coordinate of this point
     */
    public double getX() {
        return x;
    }
    /**
     * @param x The new x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * @return The y coordinate of this point
     */
    public double getY() {
        return y;
    }
    /**
     * @param y The new y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }
    /**
     * Reset the point to new coordinates
     * @param x New x coordinate
     * @param y New y coordinate
     */
    public void set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    /**
     * @return Returns this point as a String of its cartesian coordinates
     */
    public String toString()
    {
        return "("+x+","+y+")";
    }
}
