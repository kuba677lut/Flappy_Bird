package com.example.flappy_block;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    AnimationTimer gameLoop;

    @FXML
    private AnchorPane plane;

    @FXML
    private Rectangle bird;

    @FXML
    private Text score;

    @FXML
    private Rectangle gora;

    private double accelerationTime = 0;
    private int gameTime = 0;
    private int scoreCounter = 0;

    private Bird birdComponent;
    private ObstaclesHandler obstaclesHandler;

    ArrayList<Rectangle> obstacles = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int jumpHeight = 75;
        birdComponent = new Bird(bird, jumpHeight);
        double planeHeight = 600;
        double planeWidth = 400;
        obstaclesHandler = new ObstaclesHandler(plane, planeHeight, planeWidth);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        load();

        gameLoop.start();
    }

    @FXML
    void pressed(KeyEvent event) {
        if(event.getCode() == KeyCode.SPACE){
            birdComponent.fly();
            accelerationTime = 0;
        }
    }


    //Called every game frame
    private void update() {
        gameTime++;
        accelerationTime++;
        double yDelta = 0.02;
        birdComponent.moveBirdY(yDelta * accelerationTime);

        if(pointChecker(obstacles, bird)){
            scoreCounter++;
            score.setText(String.valueOf(scoreCounter));
            if(scoreCounter < 9){
                plane.setStyle("-fx-background-color:  #03BA15;");
                bird.setFill(Color.valueOf("#eeff00"));
                gora.setFill(Color.valueOf("#478a53"));
            }
            if(scoreCounter > 9 && scoreCounter < 24){
                plane.setStyle("-fx-background-color: #54BAB9;");
                bird.setFill(Color.valueOf("#9ED2C6"));
                gora.setFill(Color.valueOf("#E9DAC1"));
            }
            if(scoreCounter > 24) {
                plane.setStyle("-fx-background-color: #F47C7C;");
                bird.setFill(Color.valueOf("#f0b6b6"));
                gora.setFill(Color.valueOf("#FAD4D4"));

            }
            if(scoreCounter > 49) {
                plane.setStyle("-fx-background-color: #FF9463;");
                bird.setFill(Color.valueOf("#FF5342"));
                gora.setFill(Color.valueOf("#FE6D4E"));

            }
            if(scoreCounter > 99) {
                plane.setStyle("-fx-background-color: #4C7D8E;");
                bird.setFill(Color.valueOf("#1C284E"));
                gora.setFill(Color.valueOf("#174D7C"));

            }
        }

        obstaclesHandler.moveObstacles(obstacles);
        if(gameTime % 500 == 0){
            obstacles.addAll(obstaclesHandler.createObstacles());
        }

        if(birdComponent.isBirdDead(obstacles, plane)){
            resetGame();
        }
    }
    private void load(){
        obstacles.addAll(obstaclesHandler.createObstacles());
    }

    private void resetGame(){
        bird.setY(0);
        plane.getChildren().removeAll(obstacles);
        obstacles.clear();
        gameTime = 0;
        accelerationTime = 0;
        scoreCounter = 0;
        score.setText(String.valueOf(scoreCounter));
        plane.setStyle("-fx-background-color:  #03BA15;");
        bird.setFill(Color.valueOf("#eeff00"));
        gora.setFill(Color.valueOf("#478a53"));
    }



    private boolean pointChecker(ArrayList<Rectangle> obstacles, Rectangle bird){
        for (Rectangle obstacle: obstacles) {
            int birdPositionX = (int) (bird.getLayoutX() + bird.getX());
            if(((int)(obstacle.getLayoutX() + obstacle.getX()) == birdPositionX)){
                return true;
            }
        }
        return false;
    }
}