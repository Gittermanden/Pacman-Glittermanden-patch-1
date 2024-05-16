package umlclasses;
import javafx.geometry.BoundingBox;

public class Ghost implements Entity, Move {
    double score = 25;
    Node location;
    double speed = 0.75;
    Ghostlogic AI;
    int orientation;
    double posY = 0; 
    double posX = 0; 
    BoundingBox hitbox = new BoundingBox(0, 0, 0, 0);

    public void setSearchAlgorithm(Ghostlogic algo) {
        AI = algo;
    }

    public void release() {
        location = null; //Or wherever the fuck the ghosts reside
    }

    @Override
    public double getScore() {
        return score;
    }
    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void vored() {
        getScore();
        timeout();
    }
    @Override
    public Node getLocation() {
        return location;
    }

    @Override
    public void timeout() {
        location = null; //Or wherever the fuck the ghosts reside
    }
    @Override
    public void move() {
        switch (orientation) {
            case 1:
                location = location.getNodeUp();
                break;
            case 2:
                location = location.getNodeRight();
                break;
            case 3:
                location = location.getNodeDown();
                break;
            case 4:
                location = location.getNodeLeft();
                break;
        }

    }

    @Override
    public int getOrientation() {
        //The assigned algorithm decides which direction it moves
        
        return orientation;
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
