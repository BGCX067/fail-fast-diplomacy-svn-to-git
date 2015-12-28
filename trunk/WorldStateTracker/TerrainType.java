package WorldStateTracker;

import UnitStateTracker.Rule;

public class TerrainType 
{
    private String name;
    private Rule effects;
    
    public TerrainType(String name, Rule effects)
    {
        this.name = name;
        this.effects = effects;
    }

    public Rule getEffects() {
        return effects;
    }

    public void setEffects(Rule effects) {
        this.effects = effects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
