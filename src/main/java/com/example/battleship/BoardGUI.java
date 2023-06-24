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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Random;

public class BoardGUI extends BattleShipGameLogic {
    @FXML
    public Label name, name2;
    private Parent root;
    private Scene scene;
    private Stage stage;
    private int size = 400;
    private int squareNumbers = 10 ;

    protected static Cell[][] grid;

    private boolean isNextValid;

    @FXML
    private Pane gameBoard;
    @FXML
    private  Button battleshipButton, destroyerButton, cruiserButton, submarineButton ;


    public void displayName(String UserName) {
        name.setText(UserName);

    }
    public void onExitGame(ActionEvent e) throws Exception{ // on exitgame button
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // confirming with user if he wants to exit
        alert.setTitle("Exit Match");
        alert.setHeaderText("You are about to Exit the Match");
        alert.setContentText("Are you sure?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewGame.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            String css = this.getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        }
    }

   @FXML
    public void initialize() { // initializing the pane with board
       Ship battleship = new Ship(4,1);
       Ship destroyer = new Ship(3,2);
       Ship cruiser = new Ship(2,3);
       Ship submarine = new Ship(1,4);
       grid = new Cell[squareNumbers][squareNumbers];
       for (int i = 0; i < size; i += Cell.size) {
           for (int j = 0; j < size; j += Cell.size) {
               Cell cell = new Cell(j, i, gameBoard);
               cell.setContainsShip(false);
               grid[i / Cell.size][j / Cell.size] = cell;
               cell.setOnBoard();

               battleshipButton.setOnAction(actionEvent -> Ship.setObject(battleship)); //if battleship button clicked then the ship is battleship
               destroyerButton.setOnAction(actionEvent -> Ship.setObject(destroyer));//if destroyer button clicked then the ship is destroyer
               cruiserButton.setOnAction(actionEvent -> Ship.setObject(cruiser));//if cruiser button clicked then the ship is cruiser
               submarineButton.setOnAction(actionEvent -> Ship.setObject(submarine));//if submarine button clicked then the ship is submarine

               cell.getRectangle().setOnMousePressed(mouseEvent -> {
                   if(mouseEvent.isPrimaryButtonDown()) { // if left click is pressed placing the ship on mouse click position
                       Random random = new Random();
                       int x = (int) mouseEvent.getX() / Cell.size;
                       int y = (int) mouseEvent.getY() / Cell.size;

                       if (((Ship)Ship.getObject()).getShipCount() < ((Ship)Ship.getObject()).getMaxShip() && isPlacementValid(x, y, (Ship) Ship.getObject(), "placement",grid)) {
                           ((Ship) Ship.getObject()).setVertical(random.nextBoolean());
                           ((Ship) Ship.getObject()).placeOnBoard(grid, x, y);
                           ((Ship) Ship.getObject()).setShipCount(((Ship) Ship.getObject()).getShipCount() + 1);
                           battleshipButton.setText("Battleship x " + (battleship.getMaxShip() - battleship.getShipCount()));
                           destroyerButton.setText("Destroyer x " + (destroyer.getMaxShip() - destroyer.getShipCount()));
                           cruiserButton.setText("Cruiser x " + (cruiser.getMaxShip() - cruiser.getShipCount()));
                           submarineButton.setText("Submarine x " + (submarine.getMaxShip() - submarine.getShipCount()));
                           if(((Ship) Ship.getObject()).getShipCount() == ((Ship) Ship.getObject()).getMaxShip()){
                               ((Ship) Ship.getObject()).setPlaced(true);
                           }
                       }
                   }
                   else if(mouseEvent.isSecondaryButtonDown()) { //if right click is pressed removing the ship on the position
                       int x = (int) mouseEvent.getX() / Cell.size;
                       int y = (int) mouseEvent.getY() / Cell.size;
                       if(battleship.getPosOnBoard().contains(grid[y][x])){
                           System.out.println("hh");
                           battleship.deleteFromBoard(grid,x,y);
                       } else if (destroyer.getPosOnBoard().contains(grid[y][x])) {
                           destroyer.deleteFromBoard(grid,x,y);
                       }
                       else if (cruiser.getPosOnBoard().contains(grid[y][x])) {
                           cruiser.deleteFromBoard(grid,x,y);

                       }
                       else if (submarine.getPosOnBoard().contains(grid[y][x])) {
                           submarine.deleteFromBoard(grid,x,y);

                       }
                   }
                   if(mouseEvent.isPrimaryButtonDown() && (isNextValid = isAllShipPlaced(battleship,destroyer,cruiser,submarine))){ //if all ships are places and a ship is clicked then rotating it
                       int x = (int) mouseEvent.getX() / Cell.size;
                       int y = (int) mouseEvent.getY() / Cell.size;
                       if(battleship.getPosOnBoard().contains(grid[y][x]) && isPlacementValid(x,y,battleship,"rotation",grid)){
                           battleship.rotateShip(grid,x,y);
                       } else if (destroyer.getPosOnBoard().contains(grid[y][x])&& isPlacementValid(x,y,destroyer,"rotation",grid)) {
                           destroyer.rotateShip(grid,x,y);
                       }
                       else if (cruiser.getPosOnBoard().contains(grid[y][x])&& isPlacementValid(x,y,cruiser,"rotation",grid)) {
                           cruiser.rotateShip(grid,x,y);

                       }
                   }

               });

           }
       }
   }

    public void onNext(ActionEvent e)throws Exception{ //on next button
        if (isNextValid && isIsVsHuman()) { // implementing this buttons action is only all ships are placed in the board
                Board.setBoard1(grid);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Board2.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                BoardGUI bg = loader.getController();
                bg.name2.setText(player2.getName());
                String css = this.getClass().getResource("board.css").toExternalForm();
                scene.getStylesheets().add(css);
                //stage.setMaximized(true);
                stage.sizeToScene();
                stage.setScene(scene);
                stage.show();
        } else if (isNextValid) { //if gamemode is vs computer then directly jumping to the shooting screen
            Board.setBoard1(grid);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FireScreen.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            FireScreenController fireScreenController = loader.getController();
            fireScreenController.player1Name.setText(player1.getName());
            fireScreenController.player2Name.setText(player2.getName());
            String css = this.getClass().getResource("board.css").toExternalForm();
            scene.getStylesheets().add(css);
            //stage.setMaximized(true);
            stage.sizeToScene();
            stage.setScene(scene);
            stage.show();

        }
    }
    public void onFinalNext(ActionEvent e)throws Exception { //on player 2 next button
        if (isNextValid) {
            Board.setBoard2(grid);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FireScreen.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            FireScreenController fireScreenController = loader.getController();
            fireScreenController.player1Name.setText(player1.getName());
            fireScreenController.player2Name.setText(player2.getName());
            String css = this.getClass().getResource("board.css").toExternalForm();
            scene.getStylesheets().add(css);
            //stage.setMaximized(true);
            stage.sizeToScene();
            stage.setScene(scene);
            stage.show();
        }
    }
    //use to check bugs in program
//    public static void print(){
//        for (int i = 0; i < 10; i++){
//            for (int j = 0; j< 10; j++){
//                if (grid[i][j].containsShip()){
//                    System.out.print("X ");
//                } else {
//                    System.out.print(". ");
//                }
//            }
//            System.out.println();
//        }
//    }

}
