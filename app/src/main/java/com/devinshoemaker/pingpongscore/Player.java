package com.devinshoemaker.pingpongscore;

/**
 * Created by Devin Shoemaker on 1/15/16.
 *
 * Used to managed each player and their score
 */
public class Player {
    private String name;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
