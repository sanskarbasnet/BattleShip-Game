package com.example.battleship;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Ship {

    private boolean isVertical = false; //setting each ship to horizontal
    private int type;
    private int shipCount = 0; //keeps track of number of ship on board
    private int maxShip; // maximum number of a type of ship that can be placed
    private boolean isPlaced; //checking if a type of ship is successfully placed
    private ArrayList<Cell> posOnBoard = new ArrayList<>(); //arraylist to store the position of places ships
    private static Object object;

    public static Object getObject() {
        return object;
    }

    public int getMaxShip() {
        return maxShip;
    }

    public void setMaxShip(int maxShip) {
        this.maxShip = maxShip;
    }

    public static void setObject(Object object) {
        Ship.object = object;
    }

    public Ship(int type, int maxShip){
        this.type = type;
        this.maxShip = maxShip;

    }

    public int getShipCount() {
        return shipCount;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

     public void placeOnBoard(Cell[][] board, int x, int y){ //placing ships on the board
        if(isVertical) {

            for (int i = 0; i < this.type; i++){
               board[y + i][x].getRectangle().setFill(Color.BLUE);
                posOnBoard.add(board[y+i][x]);
                board[y + i][x].setContainsShip(true);

            }
        } else {
            for (int i = 0; i < this.type; i++){
                board[y][x + i].getRectangle().setFill(Color.BLUE);
                posOnBoard.add(board[y][x + i]);
                board[y][x + i].setContainsShip(true);
            }
        }
     }
     public void deleteFromBoard(Cell[][] board, int x, int y){ //deletes ships from the board
            for (int i = 0; i < this.type; i ++){
                int modulo = posOnBoard.indexOf(board[y][x]) % this.type;
                int cellIndex = posOnBoard.indexOf(board[y][x]);
               if( modulo == 0){
                   posOnBoard.get(cellIndex + i).getRectangle().setFill(Color.WHITE);
                   posOnBoard.get(cellIndex + i).setContainsShip(false);
               } else {
                       posOnBoard.get((cellIndex - modulo) + i).getRectangle().setFill(Color.WHITE);
                       posOnBoard.get((cellIndex - modulo) + i).setContainsShip(false);
               }

            }
         this.shipCount--;
            }
            public void rotateShip(Cell[][] board, int x, int y){ //rotates ship
                if(isVertical) {
                    for(int i = 0; i< this.type; i++){
                        board[y + i][x].getRectangle().setFill(Color.WHITE);
                        board[y + i][x].setContainsShip(false);
                    }
                        this.isVertical = false;
                        placeOnBoard(board,x,y);

                } else {
                        for (int i = 0; i < this.type; i++) {
                            board[y][x + i].getRectangle().setFill(Color.WHITE);
                            board[y][x + i].setContainsShip(false);
                        }
                        this.isVertical = true;
                        placeOnBoard(board, x, y);
                }
            }


    public ArrayList<Cell> getPosOnBoard() {
        return posOnBoard;
    }

    public void setPosOnBoard(ArrayList<Cell> posOnBoard) {
        this.posOnBoard = posOnBoard;
    }


    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
