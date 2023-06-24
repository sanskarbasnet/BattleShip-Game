package com.example.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MenuController extends BattleShipGameLogic {
    @FXML
    Button button;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private FlowPane ScenePane;
    @FXML
    TextField player1Field;
    @FXML
    private TextField player2Field;


    public void onExit(ActionEvent e) throws Exception{ //when exit button is clicked
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LogOut");
        alert.setHeaderText("You are about to LogOut");
        alert.setContentText("Do you want to save before exiting");

        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) ScenePane.getScene().getWindow();
            System.out.println("Logged Out");
            stage.close();
        }
    }
    public void onStart(ActionEvent e) throws Exception{ //when start button is clicked
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewGame.fxml"));
        root = loader.load();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("board.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.sizeToScene();
        //stage.setMaximized(true);
        stage.show();
    }
    public void onBack(ActionEvent e)throws Exception{ // when back button is clicked
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = loader.load();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        //stage.setMaximized(true);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }
    public void onHelp(ActionEvent e)throws Exception{ // when back button is clicked
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Help.fxml"));
        root = loader.load();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("board.css").toExternalForm();
        scene.getStylesheets().add(css);
        //stage.setMaximized(true);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }
    public void onVsHuman(ActionEvent e) throws Exception{ //when VS human mode is chosen
        BattleShipGameLogic.setIsVsHuman(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NameScreen.fxml"));
        root = loader.load();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("board.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setMaximized(true);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }
    public void onVsComputer(ActionEvent e) throws Exception{ // when vs Computer mode is chosen
        BattleShipGameLogic.setIsVsHuman(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NameScreen2.fxml"));
        root = loader.load();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("board.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setMaximized(true);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }
    public void onContinue(ActionEvent e) throws Exception{ //when continue button is clicked
        if(!player1Field.getText().isBlank()) {
            player1.setName(player1Field.getText());
        } else {
            player1.setName("Player 1");
        }
        if(isIsVsHuman()){
            if(!player2Field.getText().isBlank()) {
            player2.setName(player2Field.getText());
           } else {
                player2.setName("Player 2");
            }
        } else {
            player2.setName("Computer");
            setComputerBoard();
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Board1.fxml"));
        root = loader.load();

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);

        BoardGUI boardGUI = loader.getController();
        boardGUI.displayName(player1.getName());
        String css = this.getClass().getResource("board.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setMaximized(true);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }
}
