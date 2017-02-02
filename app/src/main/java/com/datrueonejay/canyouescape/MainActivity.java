package com.datrueonejay.canyouescape;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * use 1 as up
     * 2 as left
     * 3 as down
     * 4 as right
     */

    public static ImageButton move_up;
    public static ImageButton move_left;
    public static ImageButton move_down;
    public static ImageButton move_right;
    public static ImageButton settings;
    public static ImageButton[] moves = new ImageButton[4];

    public static ImageView rightOrWrong;
    public static Button next_level;

    public static TextView level;
    public static TextView move_counter;
    public static TextView highscore;
    public static TextView time;

    public static LevelSequence current_sequence;
    public static int level_number = 1;


    public static CountDownTimer timer;

    public static final Handler handler = new Handler();

    public static RelativeLayout layout;

    public static SoundPool sounds;
    public static int correct_sound;
    public static int incorrect_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        time = (TextView) findViewById(R.id.timer);

        // checks if it is timed mode
            if (MainMenu.timed_game){
                // set the countdown as visible
                time.setVisibility(View.VISIBLE);
                timer = new CountDownTimer((long)(MainMenu.time*60000) + 2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        time.setText("Seconds remaining: " + ((millisUntilFinished/1000)-1));
                        if ((millisUntilFinished/1000)==1){
                            Buttons.DisableButtons();
                            time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            if (current_sequence.check_sequence()){
                                time.setText("YOUR SCORE: " + level_number);
                            }
                            else {
                                time.setText("YOUR SCORE: " + (level_number - 1));
                            }
                        }
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }

        // create the correct and incorrect sounds of the game
        sounds = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        correct_sound = sounds.load(this, R.raw.correct, 1);
        incorrect_sound = sounds.load(this, R.raw.incorrect, 1);

        // checks if the sounds are on
        MainMenu.sounds_on = MainMenu.sp.getBoolean("sounds_on", true);

        // checks if the indicator should be set to a box
        MainMenu.indicator_box = MainMenu.sp.getBoolean("indicator_box", true);

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // timer to time between green flash and reset
        //timer = new Timer();

        // create the buttons upon opening the app
        move_up = (ImageButton) findViewById(R.id.upButton);
        move_left = (ImageButton) findViewById(R.id.leftButton);
        move_down = (ImageButton) findViewById(R.id.downButton);
        move_right = (ImageButton) findViewById(R.id.rightButton);

        // create the layout
        layout = (RelativeLayout) findViewById(R.id.activity_main);

        // create settings button
        settings = (ImageButton) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(MainActivity.this, Settings.class);
                startActivity(activity);
            }
        });

        // create an array holding the buttons
        moves[0] = move_up;
        moves[1] = move_left;
        moves[2] = move_down;
        moves[3] = move_right;

        // create the next level button
        next_level = (Button) findViewById(R.id.nextLevel);
        // disables the next level button at first
        next_level.setEnabled(false);

        // create the level 1 sequence
        current_sequence = new LevelSequence(level_number);
        //current_sequence = new LevelSequence(100000);


        // create the text for the move counter
        move_counter = (TextView) findViewById(R.id.moveCounter);
        move_counter.setText("Move " + Integer.toString(current_sequence.move_counter() + 1));

        // create the text for the highscore
        highscore = (TextView) findViewById(R.id.highscore);
        // finds the current high score
        long highScore = MainMenu.sp.getInt(MainMenu.game_mode, 0);
        highscore.setText("High Score: " + Long.toString(highScore));

        // create the right or wrong image (green or red rectangle)
        rightOrWrong = (ImageView) findViewById(R.id.rightOrWrong);

        // create level text
        level = (TextView) findViewById(R.id.level);
        level.setText(("Level " + Integer.toString(level_number)));

        // create the four buttons from
        for (int button_counter = 1; button_counter < 5; button_counter++){
            Buttons.create_button(button_counter);
        }

        // create the next level button
        next_level.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // ensures that the users sequence matches the level sequence
                if (current_sequence.check_sequence()){
                    // set the next_level button as not visible
                    next_level.setVisibility(View.INVISIBLE);
                    // increase the level
                    level_number++;
                    // set the new level text
                    level.setText(("Level " + Integer.toString(level_number)));
                    // create a new level sequence
                    current_sequence = new LevelSequence(level_number);
                    // create the text for the move counter
                    move_counter = (TextView) findViewById(R.id.moveCounter);
                    move_counter.setText("Move " + Integer.toString(current_sequence.move_counter() + 1));
                    // disables the next level button for new level
                    next_level.setEnabled(false);
                }
            }
        });


    }

    @Override
    protected void onStop(){
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        SoundManager.stopPlayingDelayed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.continueMusic();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sounds.release();
        if (MainMenu.timed_game){
            timer.cancel();

        }
    }

    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.back_window);
        dialog.show();

        // create the yes button
        Button yes;
        yes = (Button) dialog.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                MainActivity.this.finish();
                MainActivity.level_number = 1;
                if (MainMenu.timed_game) {
                    timer.cancel();
                }
            }

        });

        // create the no button
        Button no;
        no = (Button) dialog.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}
