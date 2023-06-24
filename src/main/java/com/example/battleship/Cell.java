package com.example.battleship;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
    public static final int size = 40; // cell size
    private Rectangle rectangle;
    private Pane gameBoard;
    private boolean isHit = false; //setting if the cell is already shot or not

    private int x;// x coordinate of cell
    private int y;//y coordinate of cell
    private boolean containsShip ; //if the cell contains ship

    public Cell() {
    }

    public Cell(int x, int y, Pane gameBoard){
      this.x = x;
      this.y = y;
      this.gameBoard = gameBoard;
    }
    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public boolean containsShip() {
        return containsShip;
    }

    public void setContainsShip(boolean containsShip) {
        this.containsShip = containsShip;
    }
    public void setOnBoard(){ //sets cell on board
        rectangle =  new Rectangle(x,y,Cell.size,Cell.size);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        gameBoard.getChildren().add(rectangle);
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
}
