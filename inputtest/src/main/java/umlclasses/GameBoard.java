package umlclasses;

import java.util.Date;
import java.util.HashSet;

import com.example.AnimatedImage;
import com.example.App;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class GameBoard {
    public Board board;
    public static int score = 0;
    public static int lives;
    protected static Gamemodes mode;
    public Pacman pacman;
    protected Image[] visualPills;
    protected Image[] visualBigPills;

    //Below was moved from app to here
    static Image pacLeft;
    static AnimatedImage pacAnimatedLeft;

    static Image pacRight;
    static AnimatedImage pacAnimatedRight;

    static Image pacUp;
    static AnimatedImage pacAnimatedUp;

    static Image pacDown;
    static AnimatedImage pacAnimatedDown;

    static Image background;


    static Image[][] visualboard;
    static HashSet<String> currentlyActiveKeys;
    static String bufferKey = new String("");


    public GameBoard(){
        this.board = new Board();
        this.score = 0;
        this.lives = 3;
        this.mode = Gamemodes.Play;
        this.pacman = new Pacman(board.tileSet[9][13], 355, 511);
        this.visualPills = new Image[board.pills.length];
        this.visualBigPills = new Image[board.powerPills.length];
    }

    public void prepareActionHandlers()
    {
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        App.mainScene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.add(event.getCode().toString());
                bufferKey = new String(event.getCode().toString());
            }
        });
        App.mainScene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }

    public void loadGraphics()
    {
        //background
        background = new Image(getClass().getResourceAsStream("/pacmanboard.jpg"));
        
        //board
        visualboard = new Image[19][25];
        for(int i = 0; i < visualboard.length; i++){
            for(int j = 0; j < visualboard[i].length; j++){
                if (board.tileSet[i][j].getType() == Ruleset.Wall){
                    visualboard[i][j] = new Image(getClass().getResourceAsStream("/wall.png"));
                }
                else if (board.tileSet[i][j].getType() == Ruleset.GhostFields)
                {
                    visualboard[i][j] = new Image(getClass().getResourceAsStream("/border.png"));
                }
                else if (board.tileSet[i][j].getType() == Ruleset.Path)
                {
                    visualboard[i][j] = new Image(getClass().getResourceAsStream("/path.png"));
                }
                else if (board.tileSet[i][j].getType() == Ruleset.Gate)
                {
                    visualboard[i][j] = new Image(getClass().getResourceAsStream("/gate.png"));
                }
            }
        }
        //Pacman moving left sprites
        pacLeft = new Image(getResource("/pacmanleft1.png"));
        pacAnimatedLeft = new AnimatedImage("/pacmanleft", 2, 12, ".png");

        //Pacman moving right sprites
        pacRight = new Image(getResource("/pacmanright1.png"));
        pacAnimatedRight = new AnimatedImage("/pacmanright", 2, 12, ".png");

        //Pacman up sprites
        pacUp = new Image(getResource("/pacmanup1.png"));
        pacAnimatedUp = new AnimatedImage("/pacmanup", 2, 12, ".png");

        //Pacman down sprites
        pacDown = new Image(getResource("/pacmandown1.png"));
        pacAnimatedDown = new AnimatedImage("/pacmandown", 2, 12, ".png");

        //Pill sprite
        for(int i = 0; i < board.pills.length; i++){
            visualPills[i] = new Image(getResource("/pill.png"));
        }
        for(int i = 0; i < board.powerPills.length; i++){
            visualBigPills[i] = new Image(getResource("/BigPill.png"));
        }
    }
    
    private String getResource(String filename)
    {
        return App.class.getResource(filename).toString();
    }

    public void renderBackground()
    {
        App.levelContext.drawImage(background,0,0);
        for(int i = 0; i < visualboard.length; i++){
            for(int j = 0; j < visualboard[i].length; j++){
                App.levelContext.drawImage(visualboard[i][j],i*39,j*39);
            }
        }
    }

    public void renderPacman()
    {

        // clear previous render of pacman
        App.graphicsContext.clearRect(0, 0, App.WIDTH, App.HEIGHT);

        //initiates bufferman
        Pacman bufferman = new Pacman(this.pacman);
        if (bufferKey != ""){
            bufferman.setOrientation(bufferKey);
            //int[] coordinates = bufferman.getLocation().getCoordinates();
            //System.out.println("Buffermans location is: "+ coordinates[0] + coordinates[1]);
            //System.out.println("Buffermans orientation is: "+bufferman.getOrientation());
        }
        if (bufferman.canMove()){
            this.pacman.setOrientation(bufferKey);
        }

        // Set pacmans speed



        //when input is given
        if (currentlyActiveKeys.contains("LEFT") && pacman.canMoveLeft())
        {
            pacman.setOrientation("LEFT");
            pacman.updatePos();
            App.graphicsContext.drawImage(pacAnimatedLeft.getFrame(), pacman.getPosX(),pacman.getPosY());
            pacman.setActivity(true);
        } 
        else if (currentlyActiveKeys.contains("RIGHT") && pacman.canMoveRight())
        {
            pacman.setOrientation("RIGHT");
            pacman.updatePos();
            App.graphicsContext.drawImage(pacAnimatedRight.getFrame(), pacman.getPosX(), pacman.getPosY());
            pacman.setActivity(true);
        } 
        else if (currentlyActiveKeys.contains("UP") && pacman.canMoveUp()){
            pacman.setOrientation("UP");
            pacman.updatePos();
            App.graphicsContext.drawImage(pacAnimatedUp.getFrame(), pacman.getPosX(), pacman.getPosY());  
            pacman.setActivity(true);
        }
        else if (currentlyActiveKeys.contains("DOWN") && pacman.canMoveDown()){
            pacman.setOrientation("DOWN");
            pacman.updatePos();
            App.graphicsContext.drawImage(pacAnimatedDown.getFrame(), pacman.getPosX(), pacman.getPosY());
            pacman.setActivity(true);
        }
        //if pacman isn't receiving input
        else if (pacman.isMoving() && pacman.canMove())
        {
            switch (pacman.getOrientation()) {
                case 1:
                    App.graphicsContext.drawImage(pacAnimatedUp.getFrame(), pacman.getPosX(), pacman.getPosY());
                    pacman.updatePos();
                    break;
                case 2:
                    App.graphicsContext.drawImage(pacAnimatedRight.getFrame(), pacman.getPosX(), pacman.getPosY());
                    pacman.updatePos();
                    break;
                case 3:
                    App.graphicsContext.drawImage(pacAnimatedDown.getFrame(), pacman.getPosX(), pacman.getPosY());
                    pacman.updatePos();
                    break;
                case 4:
                    App.graphicsContext.drawImage(pacAnimatedLeft.getFrame(), pacman.getPosX(), pacman.getPosY());
                    pacman.updatePos();
                default:
                    break;
            }
        }
        //only occurs at the beginning and when hitting a wall/border
        else 
        {
            switch(pacman.getOrientation()){
                case 1:
                    App.graphicsContext.drawImage(pacUp, pacman.getPosX(), pacman.getPosY());
                    break;
                case 2:
                    App.graphicsContext.drawImage(pacRight, pacman.getPosX(), pacman.getPosY());
                    break;
                case 3:
                    App.graphicsContext.drawImage(pacDown, pacman.getPosX(), pacman.getPosY());
                    break;
                case 4:
                    App.graphicsContext.drawImage(pacLeft, pacman.getPosX(), pacman.getPosY());
                    break;
                default:
                    App.graphicsContext.drawImage(pacRight, pacman.getPosX(), pacman.getPosY());
                    break;
            }
        }
    }

    public void renderPills(){
        App.pillContext.clearRect(0, 0, App.WIDTH, App.HEIGHT);

        for(int i = 0; i < visualPills.length; i++){
            if(visualPills[i] != null){
            App.pillContext.drawImage(visualPills[i], board.pills[i].getPosX(), board.pills[i].getPosY());
            }
        }

    }
    public void renderBigPills(){
        for(int i = 0; i < visualBigPills.length; i++){
            if(visualBigPills[i] != null){
            App.pillContext.drawImage(visualBigPills[i], board.powerPills[i].getPosX(), board.powerPills[i].getPosY());
            } 
        }

    }

    public void collisionHandler(){
        Entity touch = pacman.playerCollision(board.pills);
        if (touch == null) {
            touch = pacman.playerCollision(board.powerPills);
            if (touch != null) {
                touch.vored();
                int index = 0;
                boolean found = false;
                while (!found) {
                    if (touch == board.powerPills[index]) {
                        found = true;
                    }
                    else {
                        index++;
                    }
                }
                visualBigPills[index] = null;
                this.score += touch.getScore() * mode.getScoreMultiplier();
                System.out.println(this.score);
                mode = Gamemodes.Power;
                

                

            }
        }
        
        else {
            touch.vored();
            int index = 0;
            boolean found = false;
            while (!found) {
                if (touch == board.pills[index]) {
                    found = true;
                }
                else {
                    index++;
                }
            }
            visualPills[index] = null;
            this.score += touch.getScore() * mode.getScoreMultiplier();
            System.out.println(this.score);
        }

    }
    public static Text headUpDisplay() {
        String points = Integer.toString(score);
        String lifeLeft = Integer.toString(lives);
        String currentGamemode = mode.toString();
        Text totalScore = new Text(80, 20, "Score: " + points + "\nLives: " + lifeLeft + "\nCurrent gamemode: " + currentGamemode);
        totalScore.setFont(new Font(20));
        totalScore.setWrappingWidth(250);
        totalScore.setTextAlignment(TextAlignment.JUSTIFY);
        totalScore.setFill(Color.WHITE);
        return totalScore;
    }
}