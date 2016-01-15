package com.devinshoemaker.pingpongscore;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvLeftPlayer = (TextView) findViewById(R.id.tvLeftPlayer);
    private TextView tvRightPlayer = (TextView) findViewById(R.id.tvRightPlayer);

    private int playerOneScore, playerTwoScore = 0;

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
        tvLeftPlayer.setText("0");
        tvRightPlayer.setText("0");
    }

    public void leftPlayerScore(View view) {
        if (isWinningPoint(playerOneScore++, playerTwoScore)) {
            // win method
        }
        else
            tvLeftPlayer.setText(playerOneScore);
    }

    public void rightPlayerScore(View view) {
        if (isWinningPoint(playerOneScore++, playerTwoScore)) {
            // win method
        }
        else
            tvLeftPlayer.setText(playerOneScore);
    }

    private boolean isWinningPoint(int winnersScore, int losersScore) {
        if (winnersScore > losersScore && (winnersScore - losersScore) >= 2)
            return true;
        else
            return false;
    }
}
