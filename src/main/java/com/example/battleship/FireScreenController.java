package com.example.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class FireScreenController extends BattleShipGameLogic {
    @FXML
    Pane player1board, player2board;
    @FXML
    Label player1Name,player2Name, player1output, player2output;
    @FXML
    public void initialize() { //initializing the panes
        for (int i = 0; i < 400; i += Cell.size) { //setting the player 2 pane
            for (int j = 0; j < 400; j += Cell.size) {
                Rectangle rect = new Rectangle(i, j, 40, 40);
                Cell[][] board = Board.getBoard2();
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                rect.setOnMousePressed(mouseEvent -> {
                    if (mouseEvent.isPrimaryButtonDown() && player1.hasHit() && !isGameOver) {
                        play(rect, board, player1, player2, player1output,player2output); //play player1 move
                        showWinner(player1,player1output,player2output);
                    }
                    if(!isIsVsHuman() && !player1.hasHit() && !isGameOver){
                        int x , y;
                        Rectangle rect1;
                        while(true) {
                            if(player1.hasHit()){
                                break;
                            }
                            int move = computerMove();
                            y = move % 10;//computer y move
                            System.out.println(y);
                            x = Math.abs(((move - y) % 100) / 10); // computer x move
                            System.out.println(x);
                            rect1 = playComputer(x, y, Board.getBoard1(), player2, player1, player2output, player1output); //computer plays
                            showWinner(player2, player2output,player1output);
                            player1board.getChildren().add(rect1); //adding the updated rectangle to the pane
                        }

                    }
                });
                player2board.getChildren().add(rect); //adding the updated rectangle to the pane
            }
        }
            for (int i = 0; i < 400; i += Cell.size) {// setting the player 1 pane
                for (int j = 0; j < 400; j += Cell.size) {
                    Rectangle rect = new Rectangle(i, j, 40, 40);
                    Cell[][] board = Board.getBoard1();
                    rect.setFill(Color.WHITE);
                    if (board[j / 40][i / 40].containsShip() && !isIsVsHuman()) { //if vs computer then player 1's ship position is shown
                        rect.setFill(Color.BLUE);
                    } else {
                        rect.setFill(Color.WHITE);
                    }
                    rect.setStroke(Color.BLACK);
                    if (isIsVsHuman() && !isGameOver) { //if vs human then lets player 2 move
                        rect.setOnMousePressed(mouseEvent -> {
                            if (mouseEvent.isSecondaryButtonDown() && player2.hasHit()) {
                                play(rect, board, player2, player1, player2output, player1output); //plays player 2 move
                                showWinner(player2, player2output,player1output);
                            }
                        });
                    }
                    player1board.getChildren().add(rect);
                }
            }
    }
    public void onReturn(ActionEvent e) throws Exception{ //on return to main menu button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Match");
        alert.setHeaderText("You are about to Exit the Match");
        alert.setContentText("Are you sure?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        }
    }

}
