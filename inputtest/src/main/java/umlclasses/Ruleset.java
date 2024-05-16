package umlclasses;

public enum Ruleset {
    Path(true, true),
    Wall(false, true),
    GhostFields(false, false),
    Gate(false,false);
    
    //Which rules apply to which terrain
    private boolean traversable;
    private boolean fullSetOfNeighbors;
    
    Ruleset (boolean traversable, boolean fullSetOfNeighbors) {
        this.traversable = traversable;
        this.fullSetOfNeighbors = fullSetOfNeighbors;
    }


    boolean isTraversable() {
        return traversable;
    }
    boolean livesInTheHood(){
        return fullSetOfNeighbors;
    }
}
