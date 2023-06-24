package com.example.battleship;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class BattleShipGameLogic {
    private static boolean isVsHuman = false;
    public static Player player1 = new Player(true);
    public static Player player2 = new Player(true);
    private ArrayList<Integer> generatedNumbers = new ArrayList<>();
    public boolean isGameOver = false;

    public static boolean isIsVsHuman() {
        return isVsHuman;
    }

    public static void setIsVsHuman(boolean isVsHuman) {
        BattleShipGameLogic.isVsHuman = isVsHuman;
    }

    public boolean isPlacementValid(int x, int y, Ship ship, String checkType, Cell[][] grid) { //to check if the ship placement or rotation is valid
        boolean shipOrientation = ship.isVertical();
        int counter = 0;
        int length = ship.getType();
        if (checkType.equals("rotation")) {
            shipOrientation = !shipOrientation;
            length--;
            counter = 1;
        }
        boolean checker = true;
        for (int i = counter; i < length; i++) {
            if (y + i > 9 || x + i > 9) {
                return false;
            }
            if (shipOrientation && grid[y + i][x].containsShip()) {
                checker = false;
            } else if (grid[y][x + i].containsShip()) {
                checker = false;
            }
        }
        return checker;
    }


    public boolean isAllShipPlaced(Ship battleship, Ship destroyer, Ship cruiser, Ship submarine){ //to check if all ship are placed in the board
        if(battleship.isPlaced() && destroyer.isPlaced() && cruiser.isPlaced() && submarine.isPlaced()) {
            return true;
        }
        return false;
    }
    public boolean shoot(Cell[][] board, int x, int y){ //to shoot a cell
        if(board[y][x].containsShip()){
            return true;
        }
        else {
            return false;
        }
    }
    public void play( Rectangle rect, Cell[][] board , Player player1, Player player2, Label player1output, Label player2output){ //plays human's moves and shoots on board
        int x = (int) rect.getX() / 40;
        int y = (int) rect.getY() / 40;
        if (shoot(board, x, y) && player1.hasHit()) {
            rect.setFill(Color.RED);
            player1.setScore(player1.getScore() + 1);
            board[y][x].setHit(true);
            player1.setHasHit(true);
        } else if (board[y][x].isHit()) {
            player1.setHasHit(true);
        } else {
            rect.setFill(Color.GREEN);
            board[y][x].setHit(true);
            player1.setHasHit(false);
            player1output.setText("");
            player2output.setText("Your Turn!!");
            player2.setHasHit(true);
        }
    }
    public Rectangle playComputer( int x, int y, Cell[][] board , Player player1, Player player2, Label player1output, Label player2output){ //plays computer's move and shoots on board
        if (shoot(board, x, y) && player1.hasHit()) {
            board[y][x].getRectangle().setFill(Color.RED);
            player1.setScore(player1.getScore() + 1);
            board[y][x].setHit(true);
            player1.setHasHit(true);
        } else if (board[y][x].isHit()) {
            player1.setHasHit(true);
        } else {
            board[y][x].getRectangle().setFill(Color.GREEN);
            board[y][x].setHit(true);
            player1.setHasHit(false);
            player1output.setText("");
            player2output.setText("Your Turn!!");
            player2.setHasHit(true);
        }
        return board[y][x].getRectangle();
    }
    public void showWinner(Player player, Label label, Label label2){ //prints winner
        if(player.getScore() == 20){
            label.setText(player.getName() + " Wins!!");
            label2.setText(player.getName() + " Wins!!");
            isGameOver = true;
        }
    }
    public int computerMove(){ //generates unique computer move
        Random random = new Random();
        int randomNumber;
        while(true){
            randomNumber = random.nextInt(0,99);
            if(!generatedNumbers.contains(randomNumber)){
                generatedNumbers.add(randomNumber);
                return randomNumber;
            }
        }

    }
    public void setComputerBoard(){ //places ships on board for computer
        Random random = new Random();
        Ship battleship = new Ship(4,1);
        Ship destroyer = new Ship(3,2);
        Ship cruiser = new Ship(2,3);
        Ship submarine = new Ship(1,4);
        Cell[][] grid = new Cell[10][10];
        Pane pane = new Pane();
        pane.setPrefHeight(400);
        pane.setPrefWidth(400);
        for(int i = 0; i < 400; i+= Cell.size){
            for (int j = 0; j < 400; j +=Cell.size){
                Cell cell = new Cell(j,i, pane);
                cell.setContainsShip(false);
                grid[i/Cell.size][j/Cell.size] = cell;
                cell.setOnBoard();
            }
        }
        for(int i = 0; i < 10;) {
            int x = random.nextInt(0,9);
            int y = random.nextInt(0,9);
            if (i == 0 && isPlacementValid(x, y, battleship, "placement", grid)) {
                battleship.setVertical(random.nextBoolean());
                battleship.placeOnBoard(grid, x, y);
                battleship.setShipCount(battleship.getShipCount() + 1);
                i++;
                if (battleship.getShipCount() == battleship.getMaxShip()) battleship.setPlaced(true);
            } else if (i >=1 && i<=2 && isPlacementValid(x, y, destroyer, "placement", grid)) {
                destroyer.setVertical(random.nextBoolean());
                destroyer.placeOnBoard(grid, x, y);
                destroyer.setShipCount(destroyer.getShipCount() + 1);
                i++;
                if (destroyer.getShipCount() == destroyer.getMaxShip()) destroyer.setPlaced(true);

            } else if (i > 2 && i <=5 && isPlacementValid(x, y, cruiser, "placement", grid)) {
                cruiser.setVertical(random.nextBoolean());
                cruiser.placeOnBoard(grid, x, y);
                cruiser.setShipCount(cruiser.getShipCount() + 1);
                i++;
                if (cruiser.getShipCount() == cruiser.getMaxShip()) destroyer.setPlaced(true);
            } else if (i>5 && i <=9 && isPlacementValid(x, y, submarine, "placement", grid)) {
                submarine.setVertical(random.nextBoolean());
                submarine.placeOnBoard(grid, x, y);
                submarine.setShipCount(submarine.getShipCount() + 1);
                i++;
                if (submarine.getShipCount() == submarine.getMaxShip()) destroyer.setPlaced(true);
            } else {
                i = i + 0;
            }
        }

        Board.setBoard2(grid);
        }

    }
