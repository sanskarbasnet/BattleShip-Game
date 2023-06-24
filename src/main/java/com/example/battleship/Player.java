package com.example.battleship;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Player {
    private  String name; //Player name
    private  boolean hasHit; //boolean to store if the player shot the ship
    private int Score = 0; //setting player score to 0

    public Player(boolean hasHit) {
        this.hasHit = hasHit;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }

    public  boolean hasHit() {
        return hasHit;
    }

    public  void setHasHit(boolean hasHit) {
        this.hasHit = hasHit;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
