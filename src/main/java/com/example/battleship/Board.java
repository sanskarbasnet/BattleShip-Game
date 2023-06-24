package com.example.battleship;

import javafx.scene.Node;

public class Board {
    private static final int height = 10;//height of board
    private static final int width = 10; //width of board
    private static Cell[][] board1 = new Cell[height][width]; //Board 1 for player 1
    private static Cell[][] board2 = new Cell[height][width]; //Board 2 for player 2

    public static Cell[][] getBoard1() {
        return board1;
    }

    public static void setBoard1(Cell[][] board1) {
        Board.board1 = board1;
    }

    public static Cell[][] getBoard2() {
        return board2;
    }

    public static void setBoard2(Cell[][] board2) {
        Board.board2 = board2;
    }
}
