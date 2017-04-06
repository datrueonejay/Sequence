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
    public static TextView nextLevelText;

    public static ImageView fill;

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
        // timer for up counter
        currTime = 0;
        time = (TextView) findViewById(R.id.timer);
        time.getLayoutParams().height = MainMenu.screenHeight/21;
        time.setPadding(MainMenu.screenWidth/81, 0, MainMenu.screenWidth/81, MainMenu.screenWidth/81);
        // create the next level button
        nextLevel = (Button) findViewById(R.id.nextLevel);
        nextLevel.getLayoutParams().height = MainMenu.screenHeight/15;
        nextLevel.getLayoutParams().width = MainMenu.screenWidth/2;
        RelativeLayout.LayoutParams nextLevelParams = (RelativeLayout.LayoutParams) nextLevel.getLayoutParams();
        nextLevelParams.setMargins(0, 0, 0, MainMenu.screenHeight/27);
        nextLevelText = (TextView) findViewById(R.id.nextLevelText);
        nextLevelText.getLayoutParams().height = MainMenu.screenHeight/15;
        nextLevelText.getLayoutParams().width = MainMenu.screenWidth/2;
        nextLevelText.setPadding(MainMenu.screenWidth/36, MainMenu.screenHeight/60, MainMenu.screenWidth/36, MainMenu.screenHeight/60);
        RelativeLayout.LayoutParams nextLevelTextParams = (RelativeLayout.LayoutParams) nextLevelText.getLayoutParams();
        nextLevelTextParams.setMargins(0, 0, 0, MainMenu.screenHeight/27);
        nextLevelText.setText(getString(R.string.next_level));
        // disables the next level button at first
        nextLevel.setEnabled(false);
        // checks if it is timeAttackMode mode
        if (MainMenu.timeAttackMode){
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
                        nextLevel.setVisibility(View.VISIBLE);
                        nextLevel.getLayoutParams().width = 2*MainMenu.screenWidth/3;
                        RelativeLayout.LayoutParams nextLevelParams = (RelativeLayout.LayoutParams) nextLevel.getLayoutParams();
                        nextLevelParams.setMargins(MainMenu.screenHeight/27, 0, MainMenu.screenHeight/27, MainMenu.screenHeight/27);
                        nextLevelText.setVisibility(View.VISIBLE);
                        nextLevelText.getLayoutParams().width = 2*MainMenu.screenWidth/3;
                        nextLevelText.setPadding(MainMenu.screenWidth/36, MainMenu.screenHeight/60, MainMenu.screenWidth/36, MainMenu.screenHeight/60);
                        nextLevel.setEnabled(true);
                        nextLevelText.setText(R.string.return_menu);
                        nextLevel.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
                            @Override
                            public boolean onTouch(View view, MotionEvent event){
                                if (event.getAction() == MotionEvent.ACTION_UP){
                                    onBackPressed();
                                }
                                super.onTouch(view, event);
                                return false;
                            }
                        });
                        // stop the timer
                        upTimer.cancel();
                        time.setVisibility(View.VISIBLE);
                        moveCounter.setText("FINISHED");
                        time.setText("You took " + Integer.toString(MainActivity.currTime) + " seconds");
                        // set highest level reached if this is new highscore
                        if (levelNumber - 1 > MainMenu.sp.getInt(MainMenu.gameMode, 0)){
                            MainMenu.editor.putInt(MainMenu.gameMode, MainActivity.levelNumber - 1);
                            MainMenu.editor.commit();
                            // set the new highscore
                            long highScore = MainMenu.sp.getInt(MainMenu.gameMode, 0);
                            highscore.setText("Highest Level: " + Long.toString(highScore));
                        }
                        // sets the last level you beat
                        time.setText("Your Level: " + (levelNumber - 1));
                    }
                }
                @Override
                public void onFinish() {
                }
            }.start();
        }
        // create the timer
        upTimer = new Timer(true);
        // used to set the time elapsed for the beatTheClockMode
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                time.setText("Time elapsed: " + Integer.toString(currTime) + " seconds");
            }
        };
        // checks if it is beat the clock mode
        if (MainMenu.beatTheClockMode){
            // set the countdown as visible
            time.setVisibility(View.VISIBLE);
            // gets the maxlevel it should be
            Buttons.setMaxLevel(MainMenu.level);
            upTimer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    currTime += 1; //increase every sec
                    mHandler.obtainMessage(1).sendToTarget();
                }
            }, 0, 1000);
        }
        else {
            // no max level if it is not beat the cloc mode
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
        moveCounter.setPadding(0, 0, MainMenu.screenWidth/27, 0);
        RelativeLayout.LayoutParams moveCounterParams = (RelativeLayout.LayoutParams) moveCounter.getLayoutParams();
        moveCounterParams.setMargins(0, MainMenu.screenWidth/54, 0, MainMenu.screenWidth/54);
        // create the buttons upon opening the app
        moveUp = (ImageButton) findViewById(R.id.upButton);
        moveLeft = (ImageButton) findViewById(R.id.leftButton);
        moveDown = (ImageButton) findViewById(R.id.downButton);
        RelativeLayout.LayoutParams moveDownParams = (RelativeLayout.LayoutParams) moveDown.getLayoutParams();
        moveDownParams.setMargins(MainMenu.screenHeight/27, 0, MainMenu.screenHeight/27, MainMenu.screenHeight/27);
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
        // empty space between buttons
        mid = (MyCustomTextView) findViewById(R.id.mid);
        mid.getLayoutParams().width = MainMenu.screenWidth/4;
        mid.getLayoutParams().height = MainMenu.screenWidth/4;
        // create skin object
        String skin_name = MainMenu.sp.getString("skin", "classic");
        skin = new Skin();
        // find the skin
        Skin.LoadSkin(skin_name, cont);
        // set it as the current skin to the buttons and right or wrong
        Skin.SetSkin(moves[0], moves[1], moves[2], moves[3]);
        // set the appropriate sounds
        skin.SetSounds(skin_name);
        // create the four buttons
        for (int button_counter = 1; button_counter < 5; button_counter++){
            Buttons.CreateButton(button_counter);
        }
        // create settings button
        settings = (ImageButton) findViewById(R.id.settings);
        settings.getLayoutParams().height = MainMenu.screenHeight/20;
        settings.getLayoutParams().width = MainMenu.screenHeight/20;
        // set the picture of settings button and the activity launched
        settings.setBackgroundResource(R.drawable.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(MainActivity.this, Settings.class);
                startActivity(activity);
            }
        });
        // create the level 1 sequence
        currentSequence = new LevelSequence(levelNumber);
        // sets the move counter
        moveCounter.setText("Move  " + Integer.toString(currentSequence.moveCounter() + 1));
        //  sets the height of the move counter
        moveCounter.getLayoutParams().height = MainMenu.screenHeight/25;
        // create the text for the highscore
        highscore = (TextView) findViewById(R.id.highscore);
        highscore.setPadding(0, MainMenu.screenWidth/81, 0, MainMenu.screenWidth/81);
        highscore.getLayoutParams().height = MainMenu.screenHeight/18;
        RelativeLayout.LayoutParams highscoreParams = (RelativeLayout.LayoutParams) highscore.getLayoutParams();
        highscoreParams.setMargins(0, 0, 0, MainMenu.screenWidth/54);
        // finds the current high score if beat the clock mode
        if (MainMenu.beatTheClockMode) {
            long highScore = MainMenu.sp.getInt(MainMenu.gameMode, 9999);
            highscore.setText("Best Time: " + Long.toString(highScore) + " seconds");
        }
        // for classic and time attack mode
        else{
            long highScore = MainMenu.sp.getInt(MainMenu.gameMode, 0);
            highscore.setText("Highest Level: " + Long.toString(highScore));
        }
        // create the background behind top part
        fill = (ImageView) findViewById(R.id.fill);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) fill.getLayoutParams();
        // if timed put it to match with timer
        if (!MainMenu.gameMode.equals("main")){
            params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.timer);
        }
        // align with the highscore
        else {
            params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.highscore);
        }
        fill.setLayoutParams(params);
        fill.setBackgroundColor(getResources().getColor(R.color.blue));
        // create the right or wrong image (green or red rectangle)
        rightOrWrong = (ImageView) findViewById(R.id.rightOrWrong);
        RelativeLayout.LayoutParams rightOrWrongParams = (RelativeLayout.LayoutParams) rightOrWrong.getLayoutParams();
        rightOrWrongParams.setMargins(0, 0, 0, MainMenu.screenHeight/30);
        // create level text
        level = (TextView) findViewById(R.id.level);
        level.getLayoutParams().width = MainMenu.screenWidth/2 - 75;
        level.getLayoutParams().height = MainMenu.screenHeight/25;
        level.setPadding(MainMenu.screenWidth/27, 0, 0, 0);
        RelativeLayout.LayoutParams levelParams = (RelativeLayout.LayoutParams) level.getLayoutParams();
        levelParams.setMargins(0, MainMenu.screenWidth/54, 0, MainMenu.screenWidth/54);
        level.setText(("Level " + Integer.toString(levelNumber)));
        // create the next level button
        nextLevel.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
            @Override
            public boolean onTouch(View view, MotionEvent e){
                // ensures that the users sequence matches the level sequence
                if (e.getAction() == MotionEvent.ACTION_UP){
                    // set the nextLevel button as not visible
                    nextLevel.setVisibility(View.INVISIBLE);
                    nextLevelText.setVisibility(View.INVISIBLE);
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
        if (MainMenu.timeAttackMode){
            downTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        // dialog to return to main menu
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.back_window);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.getLayoutParams().height = MainMenu.screenHeight/25;
        title.setPadding(0, MainMenu.screenWidth/81, 0, MainMenu.screenWidth/81);
        RelativeLayout.LayoutParams titleParams = (RelativeLayout.LayoutParams) title.getLayoutParams();
        titleParams.setMargins(0, 0, 0, MainMenu.screenWidth/27);
        title.setText(R.string.back_title);
        TextView confirm = (TextView) dialog.findViewById(R.id.confirmation);
        confirm.getLayoutParams().height = MainMenu.screenHeight/20;
        RelativeLayout.LayoutParams confirmParams = (RelativeLayout.LayoutParams) confirm.getLayoutParams();
        confirmParams.setMargins(0, 0, 0, MainMenu.screenWidth/27);
        confirm.setText(getString(R.string.confirm));
        // create the yes button
        Button yes = (Button) dialog.findViewById(R.id.yes);
        yes.getLayoutParams().height = MainMenu.screenHeight/15;
        RelativeLayout.LayoutParams yesParams = (RelativeLayout.LayoutParams) yes.getLayoutParams();
        yesParams.setMargins(MainMenu.screenWidth/22, 0, MainMenu.screenWidth/22, MainMenu.screenWidth/27);
        TextView yesText = (TextView) dialog.findViewById(R.id.yes_text);
        yesText.getLayoutParams().height = MainMenu.screenHeight/15;
        yesText.setPadding(0, MainMenu.screenHeight/45, 0, MainMenu.screenHeight/45);
        RelativeLayout.LayoutParams yesTextParams = (RelativeLayout.LayoutParams) yesText.getLayoutParams();
        yesTextParams.setMargins(MainMenu.screenWidth/22, 0, MainMenu.screenWidth/22, MainMenu.screenWidth/27);
        yesText.setText(getString(R.string.yes));
        yes.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    dialog.dismiss();
                    MainActivity.this.finish();
                    MainActivity.levelNumber = 1;
                    if (MainMenu.timeAttackMode) {
                        downTimer.cancel();
                    }
                    if (MainMenu.beatTheClockMode) {
                        upTimer.cancel();
                        upTimer = null;
                    }
                }
                super.onTouch(v, event);
                return false;
            }

        });
        // create the no button
        Button no = (Button) dialog.findViewById(R.id.no);
        no.getLayoutParams().height = MainMenu.screenHeight/15;
        RelativeLayout.LayoutParams noParams = (RelativeLayout.LayoutParams) no.getLayoutParams();
        noParams.setMargins(MainMenu.screenWidth/22, 0, MainMenu.screenWidth/22, MainMenu.screenWidth/27);
        TextView noText = (TextView) dialog.findViewById(R.id.no_text);
        noText.getLayoutParams().height = MainMenu.screenHeight/15;
        noText.setPadding(0, MainMenu.screenHeight/45, 0, MainMenu.screenHeight/45);
        RelativeLayout.LayoutParams noTextParams = (RelativeLayout.LayoutParams) noText.getLayoutParams();
        noTextParams.setMargins(MainMenu.screenWidth/22, 0, MainMenu.screenWidth/22, MainMenu.screenWidth/27);
        noText.setText(getString(R.string.no));
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
        dialog.show();
    }
}
