package com.datrueonejay.canyouescape;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    public static ImageButton moveUp;
    public static ImageButton moveLeft;
    public static ImageButton moveDown;
    public static ImageButton moveRight;
    public static ImageButton settings;
    public static MyCustomTextView mid;
    public static ImageButton[] moves = new ImageButton[4];

    public static ImageView rightOrWrong;
    public static Button nextLevel;

    public static TextView level;
    public static TextView moveCounter;
    public static TextView highscore;
    public static TextView time;

    public static LevelSequence currentSequence;
    public static int levelNumber = 1;

    public static CountDownTimer downTimer;
    public static Timer upTimer;
    public static int currTime;
    public Handler mHandler;
    public static RelativeLayout layout;

    public static SoundPool sounds;
    public static int correctSound;
    public static int incorrectSound;

    static long timeLeft;

    public static Skin skin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = this;

        currTime = 0;

        time = (TextView) findViewById(R.id.timer);
        time.getLayoutParams().height = MainMenu.screenHeight/20;


        // checks if it is timed mode
        if (MainMenu.timedGame){
            // set the countdown as visible
            time.setVisibility(View.VISIBLE);
            downTimer = new CountDownTimer((long)(MainMenu.time*60000) + 2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    time.setText("Seconds remaining: " + ((millisUntilFinished/1000)-1));
                    if ((millisUntilFinished/1000)==1){
                        timeLeft = millisUntilFinished;
                        Buttons.DisableButtons();
                        time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                        if (currentSequence.check_sequence()){
                            time.setText("YOUR SCORE: " + levelNumber);
                        }
                        else {
                            time.setText("YOUR SCORE: " + (levelNumber - 1));
                        }
                    }
                }
                @Override
                public void onFinish() {
                }
            }.start();
        }

        // create the timer
        upTimer = new Timer(true);

        // used to set the time elapsed for the timedUpGame
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                time.setText("Time elapsed: " + Integer.toString(currTime) + " seconds");
            }
        };

        // checks if it is timed up mode
        if (MainMenu.timedUpGame){
            // set the countdown as visible
            time.setVisibility(View.VISIBLE);
            Buttons.setMaxLevel(MainMenu.level);
            upTimer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    currTime += 1; //increase every sec
                    mHandler.obtainMessage(1).sendToTarget();
                }
            }, 0, 1000);
        }
        else {
            Buttons.setMaxLevel(-1);
        }

        // create the correct and incorrect sounds of the game
        sounds = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        correctSound = sounds.load(this, R.raw.correct, 1);
        incorrectSound = sounds.load(this, R.raw.incorrect, 1);

        // checks if the sounds are on
        MainMenu.soundsOn = MainMenu.sp.getBoolean("soundsOn", true);

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // create the layout
        layout = (RelativeLayout) findViewById(R.id.activity_main);

        // create the text for the move counter
        moveCounter = (TextView) findViewById(R.id.moveCounter);

        // create the buttons upon opening the app
        moveUp = (ImageButton) findViewById(R.id.upButton);
        moveLeft = (ImageButton) findViewById(R.id.leftButton);
        moveDown = (ImageButton) findViewById(R.id.downButton);
        moveRight = (ImageButton) findViewById(R.id.rightButton);

        // create an array holding the buttons
        moves[0] = moveUp;
        moves[1] = moveLeft;
        moves[2] = moveDown;
        moves[3] = moveRight;

        // set the height and width of each button
        for (int counter = 0; counter < 4; counter++){
            moves[counter].getLayoutParams().width = MainMenu.screenWidth/4;
            moves[counter].getLayoutParams().height = MainMenu.screenWidth/4;
        }

        mid = (MyCustomTextView) findViewById(R.id.mid);
        mid.getLayoutParams().width = MainMenu.screenWidth/4;
        mid.getLayoutParams().height = MainMenu.screenWidth/4;

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
        settings.getLayoutParams().height = MainMenu.screenHeight/20;
        settings.getLayoutParams().width = MainMenu.screenHeight/20;
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
        nextLevel = (Button) findViewById(R.id.nextLevel);
        nextLevel.setText(getString(R.string.next_level));
        // disables the next level button at first
        nextLevel.setEnabled(false);

        // create the level 1 sequence
        currentSequence = new LevelSequence(levelNumber);

        // sets the move counter
        moveCounter.setText("Move  " + Integer.toString(currentSequence.moveCounter() + 1));

        //  sets the height of the move coutner
        moveCounter.getLayoutParams().height = MainMenu.screenHeight/25;

        // create the text for the highscore
        highscore = (TextView) findViewById(R.id.highscore);
        highscore.getLayoutParams().height = MainMenu.screenHeight/20;

        // finds the current high score
        if (MainMenu.timedUpGame) {
            long highScore = MainMenu.sp.getInt(MainMenu.gameMode, 9999);
            highscore.setText("Best Time: " + Long.toString(highScore) + " seconds");
        }
        else if (MainMenu.timedGame){
            long highScore = MainMenu.sp.getInt(MainMenu.gameMode, 0);
            highscore.setText("Highest Level: " + Long.toString(highScore));
        }
        else{
            long highScore = MainMenu.sp.getInt(MainMenu.gameMode, 0);
            highscore.setText("Highscore: " + Long.toString(highScore));
        }

        // create the right or wrong image (green or red rectangle)
        rightOrWrong = (ImageView) findViewById(R.id.rightOrWrong);

        // create level text
        level = (TextView) findViewById(R.id.level);
        level.getLayoutParams().width = MainMenu.screenWidth/2 - 75;
        level.getLayoutParams().height = MainMenu.screenHeight/25;
        level.setText(("Level " + Integer.toString(levelNumber)));


        // create the next level button
        nextLevel.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
            @Override
            public boolean onTouch(View view, MotionEvent e){
                // ensures that the users sequence matches the level sequence
                if (e.getAction() == MotionEvent.ACTION_UP){
                    // set the nextLevel button as not visible
                    nextLevel.setVisibility(View.INVISIBLE);
                    // set the new level text
                    level.setText(("Level " + Integer.toString(levelNumber)));
                    // create a new level sequence
                    currentSequence = new LevelSequence(levelNumber);
                    // create the text for the move counter
                    moveCounter = (TextView) findViewById(R.id.moveCounter);
                    moveCounter.setText("Move  " + Integer.toString(currentSequence.moveCounter() + 1));
                    // disables the next level button for new level
                    nextLevel.setEnabled(false);
                    // enables the direction buttons again
                    Buttons.EnableButtons();
                    // if dev mode show the new correct move
                    if (MainMenu.sp.getBoolean("dev", false)){
                        mid.setText(Integer.toString(currentSequence.getMove()));
                    }
                }
                super.onTouch(view, e);
                return false;
            }
        });

        // if dev mode show the correct move
        if (MainMenu.sp.getBoolean("dev", false)){
            mid.setText(Integer.toString(currentSequence.getMove()));
        }


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
        if (MainMenu.timedGame){
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
                    MainActivity.levelNumber = 1;
                    if (MainMenu.timedGame) {
                        downTimer.cancel();
                    }
                    if (MainMenu.timedUpGame) {
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
