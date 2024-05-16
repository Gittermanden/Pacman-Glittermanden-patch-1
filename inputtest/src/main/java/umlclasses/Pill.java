package umlclasses; 

import javafx.geometry.BoundingBox;

public class Pill implements Entity {
    Node location;
    double score = 10.0;
    double posY = 0; 
    double posX = 0; 
    BoundingBox hitbox;

    public Pill(Node location){
        this.location = location;
        this.posX = (location.getHitbox().getMinX()+(location.getHitbox().getWidth()/2))-4;
        this.posY = (location.getHitbox().getMinY()+(location.getHitbox().getHeight()/2))-4;
        this.hitbox = new BoundingBox(posX, posY, 8, 8);
    }

    @Override
    public Node getLocation() {
    return location;
    }
    @Override
    public double getScore() {
        return score;
    }
    @Override
    public void vored() {
        this.location = null;
        this.hitbox = null;
    }
    @Override
    public BoundingBox getHitbox() {
        return hitbox;
    }
    @Override
    public double getPosX() {
        return posX;
    }
    @Override
    public double getPosY() {
        return posY;
    }
}