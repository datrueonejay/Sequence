package com.datrueonejay.canyouescape;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    public static Context context;
    Boolean first_time;


    public static MediaPlayer music;

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;

    public static boolean music_on;
    public static boolean sounds_on;
    public static boolean timed_game;

    public static String game_mode;

    public static double time;

    TextView title;

    Button main;
    Button time_attack;
    Button settings;
    Button highscores;
    Button unlockables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        context = this;

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // create a shared preferences to save data
        sp = MainMenu.this.getPreferences(Context.MODE_PRIVATE);

        // create the editor for shared preferences
        editor = sp.edit();

        // find the background music track
        music = MediaPlayer.create(getApplicationContext(), R.raw.airport_lounge);

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

        time_attack = (Button) findViewById(R.id.time);
        time_attack.setText(getResources().getString(R.string.time));

        settings = (Button) findViewById(R.id.settings);
        settings.setText(getResources().getString(R.string.settings));

        highscores = (Button) findViewById(R.id.highscores);
        highscores.setText(getResources().getString(R.string.highscores));

        unlockables = (Button) findViewById(R.id.unlockables);
        unlockables.setText(getResources().getString(R.string.unlockables));

        //

        // button to start the non timed game mode
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                // makes the game a regular game
                timed_game = false;
                game_mode = "main";
                startActivity(intent);
            }
        });

        // button to start the timed game mode
        time_attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a pop up box to choose how long they would like to play
                final Dialog dialog = new Dialog(MainMenu.this);
                dialog.setContentView(R.layout.pick_time);
                Button half = (Button) dialog.findViewById(R.id.half);
                Button one = (Button) dialog.findViewById(R.id.one);
                Button one_half = (Button) dialog.findViewById(R.id.one_half);
                Button two = (Button) dialog.findViewById(R.id.two);
                Button two_half = (Button) dialog.findViewById(R.id.two_half);
                Button three = (Button) dialog.findViewById(R.id.three);
                dialog.show();

                half.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time = 0.5;
                        game_mode = "half";
                        // create the timed game
                        Intent intent = new Intent(MainMenu.this, CountDown.class);
                        // makes the game a timed game mode
                        timed_game = true;
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
                        // makes the game a timed game mode
                        timed_game = true;
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
                        // makes the game a timed game mode
                        timed_game = true;
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
                        // makes the game a timed game mode
                        timed_game = true;
                        startActivity(intent);

                    }
                });
                three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time = 3;
                        game_mode = "three_minutes";
                        // create the timed game
                        Intent intent = new Intent(MainMenu.this, CountDown.class);
                        // makes the game a timed game mode
                        timed_game = true;
                        startActivity(intent);

                    }
                });

                two_half.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        time = 2.5;
                        game_mode = "two_half";
                        // create the timed game
                        Intent intent = new Intent(MainMenu.this, CountDown.class);
                        // makes the game a timed game mode
                        timed_game = true;
                        startActivity(intent);

                    }
                });

            }
        });

        // set the settings button
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Settings.class);
                startActivity(intent);
            }
        });

        // set the highscores button to show a pop up box of the scores
        highscores.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Dialog dialog = new Dialog(MainMenu.this);
                dialog.setContentView(R.layout.activity_highscores);

                TextView main_score = (TextView) dialog.findViewById(R.id.main_score);
                TextView timed_half_score = (TextView) dialog.findViewById(R.id.timed_half_score);
                TextView timed_one_score = (TextView) dialog.findViewById(R.id.timed_one_score);
                TextView timed_one_half_score = (TextView) dialog.findViewById(R.id.timed_one_half_score);
                TextView timed_two_score = (TextView) dialog.findViewById(R.id.timed_two_score);
                TextView timed_two_half_score = (TextView) dialog.findViewById(R.id.timed_two_half_score);
                TextView timed_three_score = (TextView) dialog.findViewById(R.id.timed_three_score);

                main_score.setText(getString(R.string.main_score) + " " + (Integer.toString(MainMenu.sp.getInt("main", 0))));
                timed_half_score.setText(getString(R.string.timed_half_score) +  " " + (Integer.toString(MainMenu.sp.getInt("half", 0))));
                timed_one_score.setText(getString(R.string.timed_one_score) +  " " + (Integer.toString(MainMenu.sp.getInt("one_minute", 0))));
                timed_one_half_score.setText(getString(R.string.timed_one_half_score) +  " " + (Integer.toString(MainMenu.sp.getInt("one_half", 0))));
                timed_two_score.setText(getString(R.string.timed_two_score) +  " " + (Integer.toString(MainMenu.sp.getInt("two_minutes", 0))));
                timed_two_half_score.setText(getString(R.string.timed_two_half_score) +  " " + (Integer.toString(MainMenu.sp.getInt("two_half", 0))));
                timed_three_score.setText(getString(R.string.timed_three_score) +  " " + (Integer.toString(MainMenu.sp.getInt("three_minutes", 0))));

                dialog.show();
            }

        });

        // set the unlockables button
        unlockables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Unlockables.class);
                startActivity(intent);
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
