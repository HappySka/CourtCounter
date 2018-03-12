package com.example.android.courtcounter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int scoreTeamA = 0;
    private int scoreTeamB = 0;
    private int foulsTeamA = 0;
    private int foulsTeamB = 0;
    private boolean reset = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            scoreTeamA = savedInstanceState.getInt("SAVED_SCORE_A");
            scoreTeamB = savedInstanceState.getInt("SAVED_SCORE_B");
            foulsTeamA = savedInstanceState.getInt("SAVED_FOULS_A");
            foulsTeamB = savedInstanceState.getInt("SAVED_FOULS_B");
            displayScoreForTeamA(scoreTeamA);
            displayScoreForTeamB(scoreTeamB);
            displayFoulsForTeamA(foulsTeamA);
            displayFoulsForTeamB(foulsTeamB);
        }

        Button threeA = findViewById(R.id.team_a_three);
        Button twoA = findViewById(R.id.team_a_two);
        Button freeA = findViewById(R.id.team_a_free);
        Button threeB = findViewById(R.id.team_b_three);
        Button twoB = findViewById(R.id.team_b_two);
        Button freeB = findViewById(R.id.team_b_free);

        threeA.setOnClickListener(this);
        twoA.setOnClickListener(this);
        freeA.setOnClickListener(this);
        threeB.setOnClickListener(this);
        twoB.setOnClickListener(this);
        freeB.setOnClickListener(this);
    }

    /**
     * Save variables
     */
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("SAVED_SCORE_A",scoreTeamA);
        savedInstanceState.putInt("SAVED_SCORE_B",scoreTeamB);
        savedInstanceState.putInt("SAVED_FOULS_A",foulsTeamA);
        savedInstanceState.putInt("SAVED_FOULS_B",foulsTeamB);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Displays the given score for Team A/B.
     */
    private void displayScoreForTeamA(int score) {
        TextView scoreView = findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    private void displayScoreForTeamB(int score) {
        TextView scoreView = findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given fouls for Team A/B.
     */
    private void displayFoulsForTeamA(int fouls) {
        TextView foulView = findViewById(R.id.team_a_fouls);
        foulView.setText(getString(R.string.from_five, fouls));
    }

    private void displayFoulsForTeamB(int fouls) {
        TextView foulView = findViewById(R.id.team_b_fouls);
        foulView.setText(getString(R.string.from_five, fouls));
    }

    /**
     * Increases foul counter by 1. If the counter is now 5, the Foul Button will become
     * unclickable while the foul counter color will be set to red
     */
    public void foulForTeamA(View view) {
        noReset();
        foulsTeamA = foulsTeamA +1;
        if (foulsTeamA == 5){
            Button foulButton = findViewById(R.id.team_a_foul_button);
            foulButton.setEnabled(false);
            TextView foulView = findViewById(R.id.team_a_fouls);
            foulView.setTextColor(Color.RED);
        }
        displayFoulsForTeamA(foulsTeamA);
    }

    public void foulForTeamB(View view) {
        noReset();
        foulsTeamB = foulsTeamB +1;
        if (foulsTeamB == 5){
            Button foulButton = findViewById(R.id.team_b_foul_button);
            foulButton.setEnabled(false);
            TextView foulView = findViewById(R.id.team_b_fouls);
            foulView.setTextColor(Color.RED);
        }
        displayFoulsForTeamB(foulsTeamB);
    }

    /**
     * Sets the fouls back to 0 for a new quarter, enables the foul buttons and changes the
     * counter color back to black
     */
    public void newQuarter(View view) {
        noReset();
        foulsTeamA = 0;
        foulsTeamB = 0;
        Button foulButton = findViewById(R.id.team_a_foul_button);
        foulButton.setEnabled(true);
        foulButton = findViewById(R.id.team_b_foul_button);
        foulButton.setEnabled(true);
        TextView foulView = findViewById(R.id.team_a_fouls);
        foulView.setTextColor(Color.BLACK);
        foulView = findViewById(R.id.team_b_fouls);
        foulView.setTextColor(Color.BLACK);

        displayFoulsForTeamA(foulsTeamA);
        displayFoulsForTeamB(foulsTeamB);

    }

    /**
     * Resets all counters and buttons. If pressed once, the Reset Button turns red and asks "Really?"
     * if pressed again, the actual reset happens. All other button presses instead will reset the
     * reset button (see function noReset())
     */
    public void resetAll(View view) {
        if (reset) {
            scoreTeamA = 0;
            scoreTeamB = 0;
            displayScoreForTeamA(scoreTeamA);
            displayScoreForTeamB(scoreTeamB);
            newQuarter(view);
        } else {
            reset = true;
            Button resetButton = findViewById(R.id.reset_button);
            resetButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red))); /* red */
            resetButton.setText(getResources().getText(R.string.really));
        }
    }

    /**
     * Called when other button is pressed after the reset button is only pressed once. Puts the reset button
     * back into its original state
     */
    private void noReset(){
        if (reset) {
            reset = false;
            Button resetButton = findViewById(R.id.reset_button);
            resetButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray))); /* red */
            resetButton.setText(getResources().getText(R.string.reset));
        }
    }

    /**
     * Changes the corresponding team score if one of the score buttons is pressed
     */
    @Override
    public void onClick(View view) {
        noReset();
        switch (view.getId()){
            case R.id.team_a_three:
                scoreTeamA = scoreTeamA + 3;
                break;
            case R.id.team_a_two:
                scoreTeamA = scoreTeamA + 2;
                break;
            case R.id.team_a_free:
                scoreTeamA = scoreTeamA + 1;
                break;
            case R.id.team_b_three:
                scoreTeamB = scoreTeamB + 3;
                break;
            case R.id.team_b_two:
                scoreTeamB = scoreTeamB + 2;
                break;
            case R.id.team_b_free:
                scoreTeamB = scoreTeamB + 1;
                break;
        }
        displayScoreForTeamA(scoreTeamA);
        displayScoreForTeamB(scoreTeamB);

    }
}
