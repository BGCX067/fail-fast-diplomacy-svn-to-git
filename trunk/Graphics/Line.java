package Graphics;

public class Line 
{
    private Point start,end;
    private double A, B, C;
    public Line(Point a, Point b)
    {
        start=a;
        end=b;
        //calculate coefficients A,B, and C in Ax+By=C
        A = end.getY()-start.getY();
        B = start.getX()-end.getX();
        C = (A*start.getX())+(B*start.getY());
    }
    /**
     * Checks for intersection between this line segment and another
     * @param oL The other line segment
     * @return True if they cross, false otherwise
     */
    public boolean crosses(Line oL)
    {
        double det = ((A*oL.getB())-(oL.getA()*B)), x = 0, y = 0;
        //Check to see if the lines are parallel
        if(det == 0){
            return false;
        }
        else
        {
            //Calculate coordinates of intersection point
            x = (oL.getB()*C - B*oL.getC())/det;
            y = (A*oL.getC() - oL.getA()*C)/det;
            //Check to see if the intersection point is within the bounds of the line segments
            return (Math.min(start.getX(),end.getX())<=x 
                    && x<= Math.max(start.getX(),end.getX())
                    && Math.min(start.getY(),end.getY())<=y 
                    && y<= Math.max(start.getY(),end.getY())
                    && Math.min(oL.getStart().getX(),oL.getEnd().getX())<=x 
                    && x<= Math.max(oL.getStart().getX(),oL.getEnd().getX())
                    && Math.min(oL.getStart().getY(),oL.getEnd().getY())<=y 
                    && y<= Math.max(oL.getStart().getY(),oL.getEnd().getY()));
        }
    }
    /**
     * @return The length of this line segment
     */
    public double length()
    {
        return Math.sqrt(Math.pow(end.getX()-start.getX(),2)+Math.pow(end.getY()-start.getY(),2));
    }
    /**
     * @return The point at the 'end' of this line segment
     */
    public Point getEnd() {
        return end;
    }
    /**
     * @param end The new endpoint of this line segment
     */
    public void setEnd(Point end) {
        this.end = end;
        A = end.getY()-start.getY();
        B = start.getX()-end.getX();
        C = (A*start.getX())+(B*start.getY());
    }
    /**
     * @return The point at the beginning of this line segment
     */
    public Point getStart() {
        return start;
    }
    /**
     * @param start The new beginning point of this line segment
     */
    public void setStart(Point start) {
        this.start = start;
        A = end.getY()-start.getY();
        B = start.getX()-end.getX();
        C = (A*start.getX())+(B*start.getY());
    }
    public double getA() {
        return A;
    }
    public double getB() {
        return B;
    }
    public double getC() {
        return C;
    }
}
