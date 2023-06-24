package com.example.battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception { //start screen
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root,1920,720);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
       stage.setMaximized(true);
        stage.setOnCloseRequest(event -> {
            try {
                event.consume();
                onExit(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        stage.show();
    }
    public void onExit(Stage stage) throws Exception{ //on exit button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LogOut");
        alert.setHeaderText("You are about to LogOut");
        alert.setContentText("Do you want to save before exiting");

        if(alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Logged Out");
            stage.close();
        }
    }

}
