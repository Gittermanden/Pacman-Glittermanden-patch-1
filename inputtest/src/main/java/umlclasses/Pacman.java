package umlclasses;

import javafx.geometry.BoundingBox;

public class Pacman implements Entity, Move{
     //Is also gonna implement a controller for keyboard inputs at some point.
     // nevermind. Keep that shit in the board/app module.
    Node location;
    double score = -100.0;
    double speed = 1.9;
    int orientation = 2;
    double posY; 
    double posX; 
    BoundingBox hitbox;
    boolean activity = false;
    public Pacman(Pacman oldpacman){
        this.location = oldpacman.location;
        this.posX = oldpacman.posX;
        this.posY = oldpacman.posY;
        this.hitbox = oldpacman.hitbox;
    }
    public Pacman(Node startNode, double startposX, double startposY){
        this.location = startNode;
        this.posX = startposX;
        this.posY = startposY;
        this.hitbox = new BoundingBox(this.location.getHitbox().getMinX()+1,this.location.getHitbox().getMinY()+1,this.location.getHitbox().getWidth()-2,this.location.getHitbox().getHeight()-2);
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
        //Header.setlife(-1);
        //if (Header.getlife<1) {
        //  setGamemode("Death");
        //}
        // If eaten, then interaction
        //Check amount of lives
        //if no lives, enter die gamemode
        //if lives; de-increment lives by one and activate timeout, maybe even scorechange

        throw new UnsupportedOperationException("Unimplemented method 'vore'");
    }

    @Override
    public void timeout() {
        Node i = location;
        location = null;
        //Wait for however long you want the timeout
        location = i;
        // If hit, then give invunrebility.
        throw new UnsupportedOperationException("Unimplemented method 'timeout'");
    }
    @Override
    public Node getLocation() {
        return location;
    }
    @Override
    public void move() {
        // Set the neighboring node to = location, according to the orientation of the variable.
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
            case 5:
                //Gamemode.setGamemode("Pause");
                //modeChange(null);
                //Ghost.modechange();
                break;
            default:
                break;
        }
    }
    public void updatePos(){
        double x = 0;
        double y = 0;
        switch(this.orientation){
            case 1: //Case up
                y = speed*(-1);
                break;
            case 2: //Case right
                x = speed;
                break;
            case 3: //Case down
                y = speed;
                break;
            case 4: //Case left
                x = speed*(-1);
                break;
        }
        
        this.posX += x;
        this.posY += y;
        this.hitbox = new BoundingBox(
            this.hitbox.getMinX()+x,
            this.hitbox.getMinY()+y,
            this.hitbox.getWidth(),
            this.hitbox.getHeight());

    if (this.hitbox.intersects(this.location.getHitbox()) == false){
        move();
        }
    }

    public boolean canMove(){
        switch(orientation){
            case 1:
                return canMoveUp();

            case 2:
                return canMoveRight();

            case 3:
                return canMoveDown();

            case 4:
                return canMoveLeft();

            default:
                //Errors only
                return false;
        }  
    }

    public boolean canMoveUp(){
        Node[] targets = {this.location.getNodeUp(),
            this.location.getNodeUp().getNodeLeft(),
            this.location.getNodeUp().getNodeRight()
            };
        for(int i = 0; i < 3; i++){
            if(targets[i] != null && targets[i].getType().isTraversable() == false){
                if (new BoundingBox(this.hitbox.getMinX(),
                this.hitbox.getMinY()-speed,
                this.hitbox.getWidth(),
                this.hitbox.getHeight()
                ).intersects(targets[i].getHitbox())){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMoveRight(){
        Node[] targets = {
            location.getNodeRight(),
            location.getNodeRight().getNodeUp(),
            location.getNodeRight().getNodeDown()
        };
        for(int i = 0; i < 3; i++){
            if(targets[i] != null && targets[i].getType().isTraversable() == false){
                if (new BoundingBox(
                    this.hitbox.getMinX()+speed,
                    this.hitbox.getMinY(),
                    this.hitbox.getWidth(),
                    this.hitbox.getHeight()
                    ).intersects(targets[i].getHitbox())){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMoveDown(){
        Node[] targets = {this.location.getNodeDown(),this.location.getNodeDown().getNodeLeft(),this.location.getNodeDown().getNodeRight()};
        for(int i = 0; i < 3; i++){
            if(targets[i] != null && targets[i].getType().isTraversable() == false){
                if (new BoundingBox(
                    this.hitbox.getMinX(),
                    this.hitbox.getMinY()+speed,
                    this.hitbox.getWidth(),
                    this.hitbox.getHeight()
                    ).intersects(targets[i].getHitbox())){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMoveLeft(){
        //gets the 3 nodes directly in front of pacman
        Node[] targets = {this.location.getNodeLeft(),this.location.getNodeLeft().getNodeUp(),this.location.getNodeLeft().getNodeDown()};
        for(int i = 0; i < 3; i++){
            //System.out.println(i);
            /*known bug involving the border nodes where if you turn around while moving away from a border on the same node
            an error is caused where targets[1] is null and the below line fails to compute.
            applied a bandaid fix (targets[i] != null) and the game still works fine, but a better solution should be found.
            UPDATE: bug found and targets[i] != null seems to be a sufficient fix that covers all cases, the bug is caused by border nodes
            not assigning neighbor nodes upon the regular neighbor designation, and the default value is null, so the if() statement
            bugs out seems getType() doesn't work for "datatype" null*/
            if(targets[i] != null && targets[i].getType().isTraversable() == false){

                //Makes a dummy boundingbox based on one frame in the future.
                if (new BoundingBox(
                    this.hitbox.getMinX()-speed,
                    this.hitbox.getMinY(),
                    this.hitbox.getWidth(),
                    this.hitbox.getHeight()
                    ).intersects(targets[i].getHitbox())){
                    return false;
                }
            }
        }
        return true;
    }

    public Entity playerCollision(Entity[] collidee) {
        for (int i = 0; i < collidee.length; i++) {
            if (hitbox.contains(collidee[i].getHitbox())) {
                return collidee[i];
            }
        }
        return null;
    }
    /* OLD NODE COLLISION DESUYO >.<
    public boolean canMove(int orientation){
        switch(orientation){
            case 1:
                return canMoveUp();
            case 2:
                return canMoveRight();
            case 3:
                return canMoveDown();
            case 4:
                return canMoveLeft();
            default:
                System.out.println("How did you get here???");
                return false;
        }
    }

    public boolean canMoveUp(){
        return this.location.getNodeUp().getType().isTraversable();
    }

    public boolean canMoveRight(){
        return this.location.getNodeRight().getType().isTraversable();
    }

    public boolean canMoveDown(){
        return this.location.getNodeDown().getType().isTraversable();
    }

    public boolean canMoveLeft(){
        return this.location.getNodeLeft().getType().isTraversable();
    }
    */ 

    //for testing only
    static String stringify(int[] a){
        String stringed = new String("");
        for(int i = 0; i < a.length; i++){
            stringed = stringed + a[i] + " ";
        }
        return stringed;
    }

    public void setOrientation(String orientation) {
        switch (orientation){
            case "UP":
                this.orientation = 1;
                break;
            case "RIGHT":
                this.orientation = 2;
                break;
            case "DOWN":
                this.orientation = 3;
                break;
            case "LEFT":
                this.orientation = 4;
                break;
        }
    }

    public void setActivity(boolean currentActivity){
        this.activity = currentActivity;
    }

    @Override
    public int getOrientation() {
        // Choose between north, south, east or west. Direction is represented as variables. Chose is made by keyboard input. 

        return this.orientation;
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

    public boolean isMoving() {
        return this.activity;
    }
}
