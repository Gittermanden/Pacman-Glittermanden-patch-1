package umlclasses; 

public interface Move {


    //Move the location from Entity over to this interface, so that the pills only have to use this interface #Done
    public void move();
    //Moves the entity from one node to another
    public int getOrientation();
    //Sets orientation based on UP/DOWN/LEFT/RIGHT
    //Sets the node which the entity is moving towards // Might become redundant when the controller is created 
    public double getSpeed();
    //Returns the speed value of the entity
    public void timeout();
    //This interaction might play when eaten // only needed for pacman and ghost. This method is thus not a suitable fit for the interface
    /*
    public void modeChange(GameState mode);
    //This interaction affects the attributes of the entity in respects to the gamemode

    Put this shit in gameboird
    */


}
