package com.datrueonejay.canyouescape;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    public static Context context;
    Boolean first_time;

    public final static DisplayMetrics dimensions = new DisplayMetrics();
    public static int screen_width;
    public static int screen_height;
    public static MediaPlayer music;

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;

    public static boolean music_on;
    public static boolean sounds_on;
    public static boolean timed_game;
    public static boolean timed_up_game;

    public static String game_mode;

    public static double time;
    public static int level;

    TextView title;

    Button main;
    Button time_attack;
    Button levels;
    Button settings;
    Button highscores;
    Button unlockables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        context = this;

        // find the dimensions of the screen
        this.getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        // find the width of the screen
        screen_width = dimensions.widthPixels;
        // find the length of the screen
        screen_height = dimensions.heightPixels;

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // create a shared preferences to save data
        sp = MainMenu.this.getPreferences(Context.MODE_PRIVATE);

        // create the editor for shared preferences
        editor = sp.edit();

        // find the background music track
        music = MediaPlayer.create(getApplicationContext(), R.raw.airport_lounge_4);

        // checks if volume is on
        music_on = sp.getBoolean("music_on", true);
        if (!music_on ){
            music.setVolume(0, 0);
        }
        else {
            music.setVolume(0.75f, 0.75f);
        }

        // loops the music
        music.setLooping(true);
        music.start();

        // check if the in game sounds are on
        sounds_on = sp.getBoolean("sounds_on", true);

        // checks if this is the first time the app has been opened up
        first_time = sp.getBoolean("first_time", true);

        // shows instructions if first time
        if (first_time){
            // set that it is no longer the first time
            editor.putBoolean("first_time", false);
            editor.commit();
            Intent activity = new Intent(MainMenu.this, Instructions.class);
            startActivity(activity);
        }

        // sets the title
        title = (TextView) findViewById(R.id.title);
        title.setText(getResources().getString(R.string.title));

        // finds and sets the text buttons of the menu
        main = (Button) findViewById(R.id.main);
        main.setText(getResources().getString(R.string.main));
        main.getLayoutParams().height = screen_height/15;

        time_attack = (Button) findViewById(R.id.time);
        time_attack.setText(getResources().getString(R.string.time));
        time_attack.getLayoutParams().height = screen_height/15;

        levels = (Button) findViewById(R.id.levels);
        levels.setText(getString(R.string.levels));
        levels.getLayoutParams().height = screen_height/15;

        settings = (Button) findViewById(R.id.settings);
        settings.setText(getResources().getString(R.string.settings));
        settings.getLayoutParams().height = screen_height/15;

        highscores = (Button) findViewById(R.id.highscores);
        highscores.setText(getResources().getString(R.string.highscores));
        highscores.getLayoutParams().height = screen_height/15;

        unlockables = (Button) findViewById(R.id.unlockables);
        unlockables.setText(getResources().getString(R.string.unlockables));
        unlockables.getLayoutParams().height = screen_height/15;

        // button to start non timed game mode with colour change
        main.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                // makes the game a regular game
                timed_game = false;
                timed_up_game= false;
                game_mode = "main";
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startActivity(intent);
                }
                super.onTouch(v, event);
                return false;
            }
        }) ;

        // button to start the timed game mode
        time_attack.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // create a pop up box to choose how long they would like to play
                final Dialog dialog = new Dialog(MainMenu.this);
                dialog.setContentView(R.layout.pick_time);
                TextView instruction = (TextView) dialog.findViewById(R.id.instructions);
                instruction.setText(getString(R.string.time_instructions));
                Button half = (Button) dialog.findViewById(R.id.half);
                half.setText(getString(R.string.half));
                Button one = (Button) dialog.findViewById(R.id.one);
                one.setText(getString(R.string.one));
                Button one_half = (Button) dialog.findViewById(R.id.one_half);
                one_half.setText(getString(R.string.one_half));
                Button two = (Button) dialog.findViewById(R.id.two);
                two.setText(getString(R.string.two));

                // makes the game a timed game mode
                timed_game = true;
                timed_up_game= false;

                half.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time = 0.5;
                        game_mode = "half";
                        // create the timed game
                        Intent intent = new Intent(MainMenu.this, CountDown.class);
                        startActivity(intent);

                    }
                });

                one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time = 1;
                        game_mode = "one_minute";
                        // create the timed game
                        Intent intent = new Intent(MainMenu.this, CountDown.class);
                        startActivity(intent);

                    }
                });

                one_half.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time = 1.5;
                        game_mode = "one_half";
                        // create the timed game
                        Intent intent = new Intent(MainMenu.this, CountDown.class);
                        startActivity(intent);

                    }
                });

                two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        time = 2;
                        game_mode = "two_minutes";
                        // create the timed game
                        Intent intent = new Intent(MainMenu.this, CountDown.class);
                        startActivity(intent);

                    }

                });

                if (event.getAction() == MotionEvent.ACTION_UP){
                    dialog.show();
                }
                super.onTouch(v, event);
                return false;
            }
        });

        levels.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    final Dialog dialog = new Dialog(MainMenu.this);
                    dialog.setContentView(R.layout.pick_time);
                    TextView instruction = (TextView) dialog.findViewById(R.id.instructions);
                    instruction.setText(getString(R.string.level_instructions));
                    Button five = (Button) dialog.findViewById(R.id.half);
                    five.setText(getString(R.string.five));
                    Button seven = (Button) dialog.findViewById(R.id.one);
                    seven.setText(getString(R.string.seven));
                    Button ten = (Button) dialog.findViewById(R.id.one_half);
                    ten.setText(getString(R.string.ten));
                    Button fifteen = (Button) dialog.findViewById(R.id.two);
                    fifteen.setText(getString(R.string.fifteen));

                    dialog.show();
                    timed_up_game = true;
                    timed_game = false;

                    five.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            game_mode = "five";
                            level = 5;
                            Intent intent = new Intent(MainMenu.this, CountDown.class);
                            startActivity(intent);

                        }
                    });

                    seven.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            game_mode = "seven";
                            level = 7;
                            Intent intent = new Intent(MainMenu.this, CountDown.class);
                            startActivity(intent);

                        }
                    });

                    ten.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            game_mode = "ten";
                            level = 10;
                            Intent intent = new Intent(MainMenu.this, CountDown.class);
                            startActivity(intent);

                        }
                    });

                    fifteen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            game_mode = "fifteen";
                            level = 15;
                            Intent intent = new Intent(MainMenu.this, CountDown.class);
                            startActivity(intent);

                        }
                    });
                }
                super.onTouch(v, event);
                return false;
            }
        });

        // set the settings button
        settings.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // create the settings activity
                Intent intent = new Intent(MainMenu.this, Settings.class);
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startActivity(intent);
                }
                super.onTouch(v, event);
                return false;
            }
        });

        // set the highscores button to show a pop up box of the scores
        highscores.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                // create the highscore screen
                Dialog dialog = new Dialog(MainMenu.this);
                dialog.setContentView(R.layout.activity_highscores);

                TextView title = (TextView) dialog.findViewById(R.id.title);
                TextView main_score = (TextView) dialog.findViewById(R.id.main_score);
                TextView timed = (TextView) dialog.findViewById(R.id.timed);
                TextView timed_half_score = (TextView) dialog.findViewById(R.id.timed_half_score);
                TextView timed_one_score = (TextView) dialog.findViewById(R.id.timed_one_score);
                TextView timed_one_half_score = (TextView) dialog.findViewById(R.id.timed_one_half_score);
                TextView timed_two_score = (TextView) dialog.findViewById(R.id.timed_two_score);
                TextView levels = (TextView) dialog.findViewById(R.id.levels);
                TextView five_score = (TextView) dialog.findViewById(R.id.five_score);
                TextView seven_score = (TextView) dialog.findViewById(R.id.seven_score);
                TextView ten_score = (TextView) dialog.findViewById(R.id.ten_score);
                TextView fifteen_score = (TextView) dialog.findViewById(R.id.fifteen_score);

                title.setText(getString(R.string.highscores));
                main_score.setText(getString(R.string.main_score) + " " + (Integer.toString(MainMenu.sp.getInt("main", 0))));
                timed.setText(getString(R.string.timed));
                timed_half_score.setText(getString(R.string.half) +  ": " + (Integer.toString(MainMenu.sp.getInt("half", 0))));
                timed_one_score.setText(getString(R.string.one) +  ": " + (Integer.toString(MainMenu.sp.getInt("one_minute", 0))));
                timed_one_half_score.setText(getString(R.string.one_half) +  ": " + (Integer.toString(MainMenu.sp.getInt("one_half", 0))));
                timed_two_score.setText(getString(R.string.two) +  ": " + (Integer.toString(MainMenu.sp.getInt("two_minutes", 0))));
                levels.setText(getString(R.string.levels));
                five_score.setText(getString(R.string.five) + ": " + Integer.toString(MainMenu.sp.getInt("five", 9999)) + " seconds");
                seven_score.setText(getString(R.string.seven) + ": " + Integer.toString(MainMenu.sp.getInt("seven", 9999)) + " seconds");
                ten_score.setText(getString(R.string.ten) + ": " + Integer.toString(MainMenu.sp.getInt("ten", 9999)) + " seconds");
                fifteen_score.setText(getString(R.string.fifteen) + ": " + Integer.toString(MainMenu.sp.getInt("fifteen", 9999)) + " seconds");

                if (event.getAction() == MotionEvent.ACTION_UP){
                    dialog.show();
                }
                super.onTouch(v, event);
                return false;
            }

        });

        // set the unlockables button
        unlockables.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(MainMenu.this, Unlockables.class);
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startActivity(intent);
                }
                super.onTouch(v, event);
                return false;
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
        MainMenu.music.stop();
        MainMenu.music.release();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
