package umlclasses; 

import javafx.geometry.BoundingBox;

public class BigPill implements Entity {
    Node location;
    double score = 30.0;
    double posY = 0; 
    double posX = 0; 
    BoundingBox hitbox;

    public BigPill(Node location){
        this.location = location;
        this.posX = (location.getHitbox().getMinX()+(location.getHitbox().getWidth()/2)-5);
        this.posY = (location.getHitbox().getMinY()+(location.getHitbox().getHeight()/2)-5);
        this.hitbox = new BoundingBox(posX, posY, 12, 12);
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