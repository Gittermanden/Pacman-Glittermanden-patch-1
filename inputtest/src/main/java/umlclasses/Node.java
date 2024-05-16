package umlclasses;

import javafx.geometry.BoundingBox;

public class Node {

    private Node nodeUp;
    private Node nodeRight;
    private Node nodeDown;
    private Node nodeLeft;
    private BoundingBox hitbox;
    private int[] coordinate = new int[2];
    //private Entity occupier;

    private Ruleset type;

    public Node createNode(Node up, Node right, Node down, Node left, int coordinate1, int coordinate2, Ruleset type) {
        this.nodeUp = up;
        this.nodeRight = right;
        this.nodeDown = down;
        this.nodeLeft = left;
        this.coordinate[0] = coordinate1;    
        this.coordinate[1] = coordinate2;      
        this.type = type;
        this.hitbox = new BoundingBox(coordinate1*39, coordinate2*39, 39, 39);

        return this;
    }
    public int[] getCoordinates() {
        return coordinate;
    }
    public void setCoordinates(int newSpot1, int newSpot2) {
        this.coordinate[0] = newSpot1;
        this.coordinate[1] = newSpot2;
    }
    public Ruleset getType() {
        return type;
    }
    public void setType(Ruleset tile) {
        this.type = tile;
    }
    public Node getNodeUp(){
        return nodeUp;
    }
    public Node getNodeRight(){
        return nodeRight;
    }
    public Node getNodeDown(){
        return nodeDown;
    }
    public Node getNodeLeft(){
        return nodeLeft;
    }
    public BoundingBox getHitbox(){
        return hitbox;
    }
    public void setNodeUp(Node up) {
        this.nodeUp = up;
    }
    public void setNodeRight(Node right) {
        this.nodeRight = right;
    }
    public void setNodeDown(Node down) {
        this.nodeDown = down;
    }
    public void setNodeLeft(Node left) {
        this.nodeLeft = left;
    } 
    /*public void setEntity(Entity friend) {
        this.occupier = friend;
    }
    public Entity getEntity() {
        return occupier;
    }
    It makes more sense to check the other way around, desu.
    */
}
