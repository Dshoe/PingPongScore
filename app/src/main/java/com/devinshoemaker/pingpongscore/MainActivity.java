package com.devinshoemaker.pingpongscore;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvLeftPlayer = (TextView) findViewById(R.id.tvLeftPlayer);
    private TextView tvRightPlayer = (TextView) findViewById(R.id.tvRightPlayer);
    private EditText etLeftPlayer = (EditText) findViewById(R.id.etLeftPlayer);
    private EditText etRightPlayer = (EditText) findViewById(R.id.etRightPlayer);

    private Player playerOne, playerTwo, playerLeft, playerRight;

    private enum states {
        END_GAME,
        IN_PROGRESS,
        IN_PROGRESS_SWITCHED
    }

    private String currentState = states.END_GAME.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetMatch();
            }
        });

        etLeftPlayer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                playerLeft.setName(etLeftPlayer.getText().toString());
            }
        });

        etRightPlayer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                playerRight.setName(etRightPlayer.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetMatch() {
        etLeftPlayer.setText(playerOne.getName());
        etRightPlayer.setText(playerTwo.getName());
        playerOne = new Player();
        playerTwo = new Player();
        playerLeft = playerOne;
        playerRight = playerTwo;
        tvLeftPlayer.setText(playerRight.getScore());
        tvRightPlayer.setText(playerLeft.getScore());
    }

    public void leftPlayerScore(View view) {
        updateScore(playerLeft, playerRight);
    }

    public void rightPlayerScore(View view) {
        updateScore(playerRight, playerLeft);
    }

    private void updateScore(Player scoringPlayer, Player opposingPlayer) {
        if (isAllowed(states.IN_PROGRESS) | isAllowed(states.IN_PROGRESS_SWITCHED)) {
            scoringPlayer.setScore(scoringPlayer.getScore() + 1);
            tvLeftPlayer.setText(playerLeft.getScore());
            tvRightPlayer.setText(playerRight.getScore());

            if (isMatchPoint(scoringPlayer.getScore(), opposingPlayer.getScore())) {
                scoringPlayer.setWinCount(scoringPlayer.getScore() + 1);

                if (isWinningPoint(scoringPlayer.getWinCount(), opposingPlayer.getWinCount())) {
                    currentState = states.END_GAME.toString();
                }
                else
                    switchSides();
            }


        }
    }

    private boolean isMatchPoint(int winnersScore, int losersScore) {
        return (winnersScore > losersScore && (winnersScore - losersScore) >= 2);
    }

    private boolean isWinningPoint(int winnersScore, int losersScore) {
        return ((winnersScore - losersScore) >= 2);
    }

    private boolean isAllowed(states requiredState) {
        return (requiredState.toString().equals(currentState));
    }

    private void switchSides() {
        if (isAllowed(states.IN_PROGRESS)) {
            playerLeft.setScore(0);
            playerRight.setScore(0);
            playerOne = playerLeft;
            playerTwo = playerRight;
            playerLeft = playerTwo;
            playerRight = playerOne;
            tvLeftPlayer.setText(playerLeft.getScore());
            tvRightPlayer.setText(playerRight.getScore());
            etLeftPlayer.setText(playerLeft.getName());
            etRightPlayer.setText(playerRight.getName());
        } else if (isAllowed(states.IN_PROGRESS_SWITCHED)) {
            playerLeft.setScore(0);
            playerRight.setScore(0);
            playerOne = playerRight;
            playerTwo = playerLeft;
            playerLeft = playerOne;
            playerRight = playerTwo;
            tvLeftPlayer.setText(playerRight.getScore());
            tvRightPlayer.setText(playerLeft.getScore());
            etLeftPlayer.setText(playerLeft.getName());
            etRightPlayer.setText(playerRight.getName());
        }
    }
}
