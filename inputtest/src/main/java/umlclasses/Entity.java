package umlclasses;

import javafx.geometry.BoundingBox;

public interface Entity{
    double posY = 0; 
    double posX = 0; 
    BoundingBox hitbox = new BoundingBox(0, 0, 0, 0);

    public BoundingBox getHitbox();
    public double getPosX();
    public double getPosY();

    public double getScore();
    //Returns the score value of the entity
    public void vored();
    //Executes the interactions plays when eaten by pacman //pacman specific, won't be used by other entities unless we make special mechanics
    //Rather than triggering the modeChange on all entities at once, maybe it would be better to only apply them on the entity when it is getting vored.
    public Node getLocation();
    //Gives the coordinates for the node, which the entity occupies

    

}   