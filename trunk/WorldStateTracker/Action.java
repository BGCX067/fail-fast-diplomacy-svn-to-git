package WorldStateTracker;

public class Action implements Comparable<Action>
{
	int num;
	public Action(int num)
	{
	this.num=num;	
	}
	public int compareTo(Action a) 
	{
		if (a.getNum()>num)
			return 1;
		else if (a.getNum()<num)
			return -1;
		else
			return 0;
	}
	private int getNum() {
		return num;
	}
	public void undo() throws IllegalUndoException {}

}
