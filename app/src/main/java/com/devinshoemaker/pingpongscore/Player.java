package com.devinshoemaker.pingpongscore;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Devin Shoemaker on 1/15/16.
 *
 * Used to managed each player and their score
 */
public class Player {
    private String name;
    private int score = 0;
    private int winCount = 0;
    private boolean server = false;
    private Button button;
    private TextView tvScore;
    private EditText etName;

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

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public boolean isServer() {
        return server;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public TextView getTvScore() {
        return tvScore;
    }

    public void setTvScore(TextView tvScore) {
        this.tvScore = tvScore;
    }

    public EditText getEtName() {
        return etName;
    }

    public void setEtName(EditText etName) {
        this.etName = etName;
    }
}
