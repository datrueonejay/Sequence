package com.datrueonejay.canyouescape;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    /**
     * use 1 as up
     * 2 as left
     * 3 as down
     * 4 as right
     */
    public static Context cont;
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

    public static CountDownTimer downTimer;
    public static Timer upTimer;
    public static int curr_time;
    public Handler mHandler;
    public static RelativeLayout layout;

    public static SoundPool sounds;
    public static int correct_sound;
    public static int incorrect_sound;

    static long time_left;

    public static Skin skin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = this;

        curr_time = 0;

        time = (TextView) findViewById(R.id.timer);
        time.getLayoutParams().height = MainMenu.screen_height/20;


        // checks if it is timed mode
        if (MainMenu.timed_game){
            // set the countdown as visible
            time.setVisibility(View.VISIBLE);
            downTimer = new CountDownTimer((long)(MainMenu.time*60000) + 2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    time.setText("Seconds remaining: " + ((millisUntilFinished/1000)-1));
                    if ((millisUntilFinished/1000)==1){
                        time_left = millisUntilFinished;
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

        upTimer = new Timer(true);


        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                time.setText("Time elapsed: " + Integer.toString(curr_time) + " seconds");
            }
        };

        // checks if it is timed up mode
        if (MainMenu.timed_up_game){
            // set the countdown as visible
            time.setVisibility(View.VISIBLE);
            Buttons.setMaxLevel(MainMenu.level);
            upTimer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    curr_time += 1; //increase every sec
                    mHandler.obtainMessage(1).sendToTarget();
                }
            }, 0, 1000);
        }
        else {
            Buttons.setMaxLevel(-1);
        }

        // create the correct and incorrect sounds of the game
        sounds = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        correct_sound = sounds.load(this, R.raw.correct, 1);
        incorrect_sound = sounds.load(this, R.raw.incorrect, 1);

        // checks if the sounds are on
        MainMenu.sounds_on = MainMenu.sp.getBoolean("sounds_on", true);

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // create the layout
        layout = (RelativeLayout) findViewById(R.id.activity_main);

        // create the text for the move counter
        move_counter = (TextView) findViewById(R.id.moveCounter);

        // create the buttons upon opening the app
        move_up = (ImageButton) findViewById(R.id.upButton);
        move_left = (ImageButton) findViewById(R.id.leftButton);
        move_down = (ImageButton) findViewById(R.id.downButton);
        move_right = (ImageButton) findViewById(R.id.rightButton);

        // create an array holding the buttons
        moves[0] = move_up;
        moves[1] = move_left;
        moves[2] = move_down;
        moves[3] = move_right;

        // set the height and width of each button
        for (int counter = 0; counter < 4; counter++){
            moves[counter].getLayoutParams().width = MainMenu.screen_width/4;
            moves[counter].getLayoutParams().height = MainMenu.screen_width/4;
        }

        String skin_name = MainMenu.sp.getString("skin", "classic");
        skin = new Skin();
        // find the skin
        Skin.LoadSkin(skin_name, cont);
        // set it as the current skin to the buttons and right or wrong
        Skin.SetSkin(moves[0], moves[1], moves[2], moves[3]);
        // set the appropriate sounds
        skin.SetSounds(skin_name);

        // create the four buttons from
        for (int button_counter = 1; button_counter < 5; button_counter++){
            Buttons.CreateButton(button_counter);
        }

        // create settings button
        settings = (ImageButton) findViewById(R.id.settings);
        // set the picture
        settings.setBackgroundResource(R.drawable.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(MainActivity.this, Settings.class);
                startActivity(activity);
            }
        });

        // create the next level button
        next_level = (Button) findViewById(R.id.nextLevel);
        // disables the next level button at first
        next_level.setEnabled(false);

        // create the level 1 sequence
        current_sequence = new LevelSequence(level_number);

        // sets the move counter
        move_counter.setText("Move  " + Integer.toString(current_sequence.move_counter() + 1));

        //  sets the height of the move coutner
        move_counter.getLayoutParams().height = MainMenu.screen_height/25;

        // create the text for the highscore
        highscore = (TextView) findViewById(R.id.highscore);
        highscore.getLayoutParams().height = MainMenu.screen_height/20;

        // finds the current high score
        if (MainMenu.timed_up_game) {
            long highScore = MainMenu.sp.getInt(MainMenu.game_mode, 9999);
            highscore.setText("Highscore: " + Long.toString(highScore) + " seconds");
        }
        else{
            long highScore = MainMenu.sp.getInt(MainMenu.game_mode, 0);
            highscore.setText("Highscore: " + Long.toString(highScore));
        }

        // create the right or wrong image (green or red rectangle)
        rightOrWrong = (ImageView) findViewById(R.id.rightOrWrong);

        // create level text
        level = (TextView) findViewById(R.id.level);
        level.getLayoutParams().width = MainMenu.screen_width/2 - 75;
        level.getLayoutParams().height = MainMenu.screen_height/25;
        level.setText(("Level " + Integer.toString(level_number)));


        // create the next level button
        next_level.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // ensures that the users sequence matches the level sequence
                if (current_sequence.check_sequence()){
                    // set the next_level button as not visible
                    next_level.setVisibility(View.INVISIBLE);
                    // set the new level text
                    level.setText(("Level " + Integer.toString(level_number)));
                    // create a new level sequence
                    current_sequence = new LevelSequence(level_number);
                    // create the text for the move counter
                    move_counter = (TextView) findViewById(R.id.moveCounter);
                    move_counter.setText("Move  " + Integer.toString(current_sequence.move_counter() + 1));
                    // disables the next level button for new level
                    next_level.setEnabled(false);
                    // enables the direction buttons again
                    Buttons.EnableButtons();
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
            downTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.back_window);
        dialog.show();
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(R.string.back_title);

        TextView confirm = (TextView) dialog.findViewById(R.id.confirmation);
        confirm.setText(getString(R.string.confirm));

        // create the yes button
        Button yes;
        yes = (Button) dialog.findViewById(R.id.yes);
        yes.setText(getString(R.string.yes));
        yes.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    dialog.dismiss();
                    MainActivity.this.finish();
                    MainActivity.level_number = 1;
                    if (MainMenu.timed_game) {
                        downTimer.cancel();
                    }
                    if (MainMenu.timed_up_game) {
                        upTimer.cancel();
                        upTimer = null;
                    }
                }
                super.onTouch(v, event);
                return false;
            }

        });

        // create the no button
        Button no;
        no = (Button) dialog.findViewById(R.id.no);
        no.setText(getString(R.string.no));
        no.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    dialog.dismiss();
                }
                super.onTouch(v,event);
                return false;
            }
        });

    }

}
