package com.example.snake_ladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeLadder extends Application {
    public static final int titleSize = 40, width=10,height=10;
    public static final int buttonLine = height*titleSize + 50 , infoLine = buttonLine - 30;
    private Dice dice = new Dice();
    private Player playerOne,playerTwo;
    private boolean gameStarted = false, playerOneTurn = false, playerTwoTurn = false;
    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width*titleSize,height*titleSize + 100);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(titleSize);
                tile.setTranslateX(j*titleSize);
                tile.setTranslateY(i*titleSize);
                root.getChildren().add(tile);
            }
        }
        Image img = new Image("file:\\C:\\Users\\mdisl\\IdeaProjects\\Snake_Ladder\\src\\main\\img.png");
        ImageView board = new ImageView();
        board.setImage(img);
        board.setFitHeight(height*titleSize);
        board.setFitWidth(width*titleSize);

        // button
        Button plyarOneButton = new Button("Plyar One ");
        Button plyarTwoButton = new Button("Plyar Two ");
        Button startButton = new Button("Start ");

        plyarOneButton.setTranslateY(buttonLine);
        plyarOneButton.setTranslateX(20);
        plyarOneButton.setDisable(true);

        plyarTwoButton.setTranslateY(buttonLine);
        plyarTwoButton.setTranslateX(300);
        plyarTwoButton.setDisable(true);

        startButton.setTranslateY(buttonLine);
        startButton.setTranslateX(160);

        // infolabel
        Label plyarOneLabel = new Label("");
        Label plyarTwoLabel = new Label("");
        Label diceLabel = new Label("Start the Game !");
        plyarOneLabel.setTranslateY(infoLine);
        plyarOneLabel.setTranslateX(20);
        plyarTwoLabel.setTranslateY(infoLine);
        plyarTwoLabel.setTranslateX(300);
        diceLabel.setTranslateY(infoLine);
        diceLabel.setTranslateX(160);

        playerOne = new Player(titleSize, Color.BLACK,"Islam");
        playerTwo = new Player(titleSize-5,Color.WHITE,"Amit");

        // PLYAR ACTION
        plyarOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted) {
                    if (playerOneTurn) {
                        int diceValue = dice.getRollDiceValue();
                        diceLabel.setText("Dice Value : " + diceValue);
                        playerOne.movePlayer(diceValue);
                        // wining Condition
                        if(playerOne.isWinner()){
                            diceLabel.setText("Winner is " + playerOne.getName());
                            playerOneTurn =false;
                            plyarOneButton.setDisable(true);
                            plyarOneLabel.setText("");
                            playerTwoTurn=true;
                            plyarTwoButton.setDisable(false);
                            plyarTwoLabel.setText("");
                            startButton.setDisable(true);
                            startButton.setText("Restart");
                            gameStarted=false;
                        }else {
                            playerOneTurn = false;
                            plyarOneButton.setDisable(true);
                            plyarOneLabel.setText("");
                            playerTwoTurn = true;
                            plyarTwoButton.setDisable(false);
                            plyarTwoLabel.setText("Your Turn : " + playerTwo.getName());
                        }
                    }
                }
            }
        });
        plyarTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted){
                    if(playerTwoTurn){
                        int diceValue = dice.getRollDiceValue();
                        diceLabel.setText("Dice Value : " + diceValue);
                        playerTwo.movePlayer(diceValue);
                        // wining condition
                        if(playerTwo.isWinner()){
                            diceLabel.setText("Winner is " + playerTwo.getName());
                            playerOneTurn =false;
                            plyarOneButton.setDisable(true);
                            plyarOneLabel.setText("");
                            playerTwoTurn=true;
                            plyarTwoButton.setDisable(true);
                            plyarTwoLabel.setText("");
                            startButton.setDisable(false);
                            startButton.setText("Restart");
                            //gameStarted=false;
                        }else{
                            playerOneTurn=true;
                            plyarOneButton.setDisable(false);
                            plyarOneLabel.setText("Your Turn : " + playerOne.getName());
                            playerTwoTurn=false;
                            plyarTwoButton.setDisable(true);
                            plyarTwoLabel.setText("");

                        }


                    }
                }
            }
        });

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted = true;
                diceLabel.setText("Game Started");
                startButton.setDisable(true);
                playerOneTurn = true;
                plyarOneLabel.setText("Your Turn " + playerOne.getName());
                plyarOneButton.setDisable(false);
                playerOne.startingPosition();
                playerTwoTurn = false;
                plyarTwoLabel.setText("");
                plyarTwoButton.setDisable(true);
                playerTwo.startingPosition();
            }
        });



        root.getChildren().addAll(board,plyarOneButton,plyarTwoButton,startButton,plyarOneLabel,plyarTwoLabel,diceLabel,playerOne.getCoin(),playerTwo.getCoin());



        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(createContent());
        stage.setTitle("Snake & Ladder ! ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}