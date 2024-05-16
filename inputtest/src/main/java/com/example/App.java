package com.example;

import umlclasses.*;

import javafx.animation.*;
import javafx.animation.Timeline;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    public static Scene mainScene;
    public static GraphicsContext graphicsContext;
    public static GraphicsContext levelContext;
    public static GraphicsContext pillContext;
    public static GraphicsContext bigPillContext;
    public static int WIDTH = 741;
    public static int HEIGHT = 975;

    
    //Initialising pacman sprite attributes
    /* MOVED TO GAMEBOARD
    static Image pacLeft;
    static AnimatedImage pacAnimatedLeft;

    static Image pacRight;
    static AnimatedImage pacAnimatedRight;

    static Image pacUp;
    static AnimatedImage pacAnimatedUp;

    static Image pacDown;
    static AnimatedImage pacAnimatedDown;
    */

    //Initialising background/map attributes
    /* MOVED TO GAMEBOARD
    static Image background;
    */
    static GameBoard gameboard = new GameBoard();
    static Image[][] visualboard;
    /* MOVED TO GAMEBOARD
    static HashSet<String> currentlyActiveKeys;
    */

    @Override
    public void start(Stage mainStage)
    {
        mainStage.setTitle("Event Handling");

        Group root = new Group();
        mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        Canvas entityCanvas = new Canvas(WIDTH, HEIGHT);
        Canvas backgroundCanvas = new Canvas(WIDTH, HEIGHT);
        Canvas pillCanvas = new Canvas(WIDTH, HEIGHT);
        Canvas bigPillCanvas = new Canvas(WIDTH, HEIGHT);
        Pane headUpDisplay = new Pane(); 
        root.getChildren().add(backgroundCanvas);
        root.getChildren().add(entityCanvas);
        root.getChildren().add(pillCanvas);
        root.getChildren().add(bigPillCanvas);
        root.getChildren().add(headUpDisplay);

        gameboard.prepareActionHandlers();

        levelContext = backgroundCanvas.getGraphicsContext2D();
        graphicsContext = entityCanvas.getGraphicsContext2D();
        pillContext = pillCanvas.getGraphicsContext2D();
        bigPillContext = bigPillCanvas.getGraphicsContext2D();

        gameboard.loadGraphics();
        /**
         * Main "game" loop
         */

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE); 

        gameboard.renderBackground();


        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.017), 
            new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent ae)
                {
                    gameboard.renderPacman();
                    gameboard.collisionHandler();
                    gameboard.renderPills();
                    gameboard.renderBigPills();
                    headUpDisplay.getChildren().clear();
                    headUpDisplay.getChildren().add(GameBoard.headUpDisplay());

                    

                    /*
                    int[] coordinates = gameboard.pacman.getLocation().getCoordinates();
                    System.out.println("Player node location: " + coordinates[0] + ", " + coordinates[1]);
                    */
                }
            });
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

        mainStage.show();
    }

    /* MOVED TO GAMEBOARD
    private static void prepareActionHandlers()
    {
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.add(event.getCode().toString());
            }
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }

    private static void loadGraphics()
    {
        //background
        background = new Image(getResource("pacmanboard.jpg"));
        
        //board
        visualboard = new Image[19][25];
        for(int i = 0; i < visualboard.length; i++){
            for(int j = 0; j < visualboard[i].length; j++){
                if (gameboard.board[i][j].getType() == Ruleset.Wall){
                    visualboard[i][j] = new Image(getResource("wall.png"));
                }
                else if (gameboard.board[i][j].getType() == Ruleset.Border)
                {
                    visualboard[i][j] = new Image(getResource("border.png"));
                }
                else if (gameboard.board[i][j].getType() == Ruleset.Path)
                {
                    visualboard[i][j] = new Image(getResource("path.png"));
                }
            }
        }
        //Pacman moving left sprites
        pacLeft = new Image(getResource("pacmanleft1.png"));
        pacAnimatedLeft = new AnimatedImage("pacmanleft", 2, 12, ".png");

        //Pacman moving right sprites
        pacRight = new Image(getResource("pacmanright1.png"));
        pacAnimatedRight = new AnimatedImage("pacmanright", 2, 12, ".png");

        //Pacman up sprites
        pacUp = new Image(getResource("pacmanup1.png"));
        pacAnimatedUp = new AnimatedImage("pacmanup", 2, 12, ".png");

        //Pacman down sprites
        pacDown = new Image(getResource("pacmandown1.png"));
        pacAnimatedDown = new AnimatedImage("pacmandown", 2, 12, ".png");
    }
    
    private static String getResource(String filename)
    {
        return App.class.getResource(filename).toString();
    }

    private static void renderBackground()
    {
        levelContext.drawImage(background,0,0);
        for(int i = 0; i < visualboard.length; i++){
            for(int j = 0; j < visualboard[i].length; j++){
                levelContext.drawImage(visualboard[i][j],i*39,j*39);
            }
        }
    }

    private static void renderPacman(Pacman pacman)
    {
        // clear canvas
        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);

        //when input is given
        if (currentlyActiveKeys.contains("LEFT") && pacman.canMoveLeft())
        {
            graphicsContext.drawImage(pacAnimatedLeft.getFrame(), pacman.getPosX()-1 ,pacman.getPosY());
            pacman.updatePos(-1, 0);
            pacman.setActivity(true);
            pacman.setOrientation("LEFT");
        } 
        else if (currentlyActiveKeys.contains("RIGHT") && pacman.canMoveRight())
        {
            graphicsContext.drawImage(pacAnimatedRight.getFrame(), pacman.getPosX()+1, pacman.getPosY());
            pacman.updatePos(1, 0);
            pacman.setActivity(true);
            pacman.setOrientation("RIGHT");
        } 
        else if (currentlyActiveKeys.contains("UP") && pacman.canMoveUp()){
            graphicsContext.drawImage(pacAnimatedUp.getFrame(), pacman.getPosX(), pacman.getPosY()-1);  
            pacman.updatePos(0, -1);
            pacman.setActivity(true);
            pacman.setOrientation("UP");
        }
        else if (currentlyActiveKeys.contains("DOWN") && pacman.canMoveDown()){
            graphicsContext.drawImage(pacAnimatedDown.getFrame(), pacman.getPosX(), pacman.getPosY()+1);
            pacman.setActivity(true);
            pacman.updatePos(0, 1);
            pacman.setOrientation("DOWN");
        }
        //if pacman can't move
        else if (pacman.isMoving() && pacman.canMove())
        {
            switch (pacman.getOrientation()) {
                case 1:
                    graphicsContext.drawImage(pacAnimatedUp.getFrame(), pacman.getPosX(), pacman.getPosY());
                    pacman.updatePos(0, -1);
                    break;
                case 2:
                    graphicsContext.drawImage(pacAnimatedRight.getFrame(), pacman.getPosX(), pacman.getPosY());
                    pacman.updatePos(1, 0);
                    break;
                case 3:
                    graphicsContext.drawImage(pacAnimatedDown.getFrame(), pacman.getPosX(), pacman.getPosY());
                    pacman.updatePos(0, 1);
                    break;
                case 4:
                    graphicsContext.drawImage(pacAnimatedLeft.getFrame(), pacman.getPosX(), pacman.getPosY());
                    pacman.updatePos(-1, 0);
                default:
                    break;
            }
        }
        //only occurs at the beginning and when hitting a wall/border
        else 
        {
            switch(pacman.getOrientation()){
                case 1:
                    graphicsContext.drawImage(pacUp, pacman.getPosX(), pacman.getPosY());
                    break;
                case 2:
                    graphicsContext.drawImage(pacRight, pacman.getPosX(), pacman.getPosY());
                    break;
                case 3:
                    graphicsContext.drawImage(pacDown, pacman.getPosX(), pacman.getPosY());
                    break;
                case 4:
                    graphicsContext.drawImage(pacLeft, pacman.getPosX(), pacman.getPosY());
                    break;
                default:
                    graphicsContext.drawImage(pacRight, pacman.getPosX(), pacman.getPosY());
                    break;
            }
        }
    }
    */ 

}
