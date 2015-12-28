package Combat;

public abstract class Rule implements Comparable<Rule>
{
    private int priority;
    private String name;
    
    public Rule(String name, int priority)
    {
        this.priority = priority;
        this.name = name;
    }
    
    /**
     * TODO This method will apply this rule to the argument combat.
     * Rules should be sorted using the Collections.sort method before their application, to organise them in order of priority.
     * Rules can be anything from environmental conditions to special abilities.
     * @param c The combat to which the rule is to be applied
     */
    public abstract void apply(Combat c);
    
    public int getPriority()
    {
        return priority;
    }
    public String getName()
    {
    	return name;
    }
    public int compareTo(Rule r)
    {
        if (priority>r.getPriority())
            return 1;
        else if (priority<r.getPriority())
            return -1;
        return 0;
    }
}
