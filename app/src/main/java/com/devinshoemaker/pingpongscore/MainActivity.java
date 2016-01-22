package com.devinshoemaker.pingpongscore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // todo move all access to UI elements to the player object

    private TextView tvLeftPlayerScore;
    private TextView tvRightPlayerScore;
    private EditText etLeftPlayerName;
    private EditText etRightPlayerName;
    private Button btnLeftPlayer;
    private Button btnRightPlayer;

    private Player playerOne, playerTwo, playerLeft, playerRight;

    private enum states {
        NEW_GAME,
        END_GAME,
        IN_PROGRESS,
        IN_PROGRESS_SWITCHED
    }

    private states currentState = states.END_GAME;

    @Override
    @SuppressWarnings({"NullableProblems"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvLeftPlayerScore = (TextView) findViewById(R.id.tvLeftPlayer);
        tvRightPlayerScore = (TextView) findViewById(R.id.tvRightPlayer);
        etLeftPlayerName = (EditText) findViewById(R.id.etLeftPlayer);
        etRightPlayerName = (EditText) findViewById(R.id.etRightPlayer);
        btnLeftPlayer = (Button) findViewById(R.id.btnLeftPlayer);
        btnRightPlayer = (Button) findViewById(R.id.btnRightPlayer);
        playerLeft = new Player();
        playerRight = new Player();
        playerOne = new Player();
        playerTwo = new Player();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        etLeftPlayerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                playerLeft.setName(etLeftPlayerName.getText().toString());
            }
        });

        etRightPlayerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                playerRight.setName(etRightPlayerName.getText().toString());
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

    private void resetGame() {
        etLeftPlayerName.setText(playerOne.getName());
        etRightPlayerName.setText(playerTwo.getName());
        playerOne = new Player();
        playerTwo = new Player();
        playerLeft = playerOne;
        playerRight = playerTwo;

        playerLeft.setButton(btnLeftPlayer);
        playerLeft.setTvScore(tvLeftPlayerScore);
        playerLeft.setEtName(etLeftPlayerName);
        playerRight.setButton(btnRightPlayer);
        playerRight.setTvScore(tvRightPlayerScore);
        playerRight.setEtName(etRightPlayerName);

        playerLeft.getButton().setText("Select Server");
        playerRight.getButton().setText("Select Server");

        playerLeft.getTvScore().setText("0");
        playerRight.getTvScore().setText("0");

        playerLeft.getEtName().setText("");
        playerRight.getEtName().setText("");

        currentState = states.NEW_GAME;
    }

    public void leftPlayerScore(View view) {
        if (isAllowed(states.IN_PROGRESS) | isAllowed(states.IN_PROGRESS_SWITCHED))
            updateScore(playerLeft, playerRight);
        else if (isAllowed(states.NEW_GAME)) {
            setStartingServer(playerLeft, playerRight);
        }
    }

    public void rightPlayerScore(View view) {
        if (isAllowed(states.IN_PROGRESS) | isAllowed(states.IN_PROGRESS_SWITCHED))
            updateScore(playerRight, playerLeft);
        else if (isAllowed(states.NEW_GAME)) {
            setStartingServer(playerRight, playerLeft);
        }
    }

    private void updateScore(Player scoringPlayer, Player opposingPlayer) {
        if (isAllowed(states.IN_PROGRESS) | isAllowed(states.IN_PROGRESS_SWITCHED)) {
            scoringPlayer.setScore(scoringPlayer.getScore() + 1);
            tvLeftPlayerScore.setText(playerLeft.getScore());
            tvRightPlayerScore.setText(playerRight.getScore());

            if (isMatchPoint(scoringPlayer.getScore(), opposingPlayer.getScore())) {
                scoringPlayer.setWinCount(scoringPlayer.getScore() + 1);

                if (isWinningPoint(scoringPlayer.getWinCount(), opposingPlayer.getWinCount())) {
                    currentState = states.END_GAME;
                }
                else {
                    switchSides();
                    scoringPlayer.setServer(false);
                    opposingPlayer.setServer(true);
                }
            }
            else if (!(((scoringPlayer.getScore() + opposingPlayer.getScore()) % 2) == 0)) {
                if (scoringPlayer.isServer())
                    setServer(opposingPlayer, scoringPlayer);
                else
                    setServer(scoringPlayer, opposingPlayer);
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
        return (requiredState.equals(currentState));
    }

    private void switchSides() {
        if (isAllowed(states.IN_PROGRESS)) {
            playerLeft.setScore(0);
            playerRight.setScore(0);
            playerOne = playerLeft;
            playerTwo = playerRight;
            playerLeft = playerTwo;
            playerRight = playerOne;
            tvLeftPlayerScore.setText(playerLeft.getScore());
            tvRightPlayerScore.setText(playerRight.getScore());
            etLeftPlayerName.setText(playerLeft.getName());
            etRightPlayerName.setText(playerRight.getName());
        } else if (isAllowed(states.IN_PROGRESS_SWITCHED)) {
            playerLeft.setScore(0);
            playerRight.setScore(0);
            playerOne = playerRight;
            playerTwo = playerLeft;
            playerLeft = playerOne;
            playerRight = playerTwo;
            tvLeftPlayerScore.setText(playerRight.getScore());
            tvRightPlayerScore.setText(playerLeft.getScore());
            etLeftPlayerName.setText(playerLeft.getName());
            etRightPlayerName.setText(playerRight.getName());
        }
    }

    private void setServer(Player server, Player nonServer) {
        server.setServer(true);
        server.getButton().setBackgroundColor(Color.GREEN);
        nonServer.setServer(false);
        nonServer.getButton().setBackgroundColor(Color.RED);
    }

    private void setStartingServer(Player server, Player nonServer) {
        btnLeftPlayer.setText("");
        btnRightPlayer.setText("");
        setServer(server, nonServer);
        currentState = states.IN_PROGRESS;
    }
}
