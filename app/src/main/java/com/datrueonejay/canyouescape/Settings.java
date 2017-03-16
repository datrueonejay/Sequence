package com.datrueonejay.canyouescape;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    Button back;
    Button help;
    Button me;
    Button ownership;
    Button musicToggle;
    Button soundsToggle;
    Button sizeToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // resumes music
        MainMenu.music.start();

        back = (Button) findViewById(R.id.back);
        back.setText(R.string.back);
        back.getLayoutParams().height = MainMenu.screenHeight/15;

        // go back to the game
        back.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    onBackPressed();
                }
                super.onTouch(view, event);
                return false;
            }
        });

        help = (Button) findViewById(R.id.help);
        help.setText(R.string.help);
        help.getLayoutParams().height = MainMenu.screenHeight/15;
        // opens instructions
        help.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Intent activity = new Intent(Settings.this, SettingsInstructions.class);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startActivity(activity);
                }
                super.onTouch(view, event);
                return false;
            }
        });

        me = (Button) findViewById(R.id.me);
        me.setText(R.string.me);
        me.getLayoutParams().height = MainMenu.screenHeight/15;
        // opens screen about me
        me.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Intent activity = new Intent(Settings.this, AboutMe.class);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startActivity(activity);
                }
                super.onTouch(view, event);
                return false;
            }
        });

        ownership = (Button) findViewById(R.id.ownership);
        ownership.setText(R.string.copyright);
        ownership.getLayoutParams().height = MainMenu.screenHeight/15;
        // opens screen about me
        ownership.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Intent activity = new Intent(Settings.this, Ownership.class);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startActivity(activity);
                }
                super.onTouch(view, event);
                return false;
            }
        });

        // sets the music toggle button
        musicToggle = (Button) findViewById(R.id.music);
        musicToggle.getLayoutParams().height = MainMenu.screenHeight/15;

        // set the music text upon entering the settings menu
        if (MainMenu.musicOn){
            musicToggle.setText(R.string.musicOn);
        }
        else if (!MainMenu.musicOn){
            musicToggle.setText(R.string.music_off);
        }
        // turns music volume to 0
        musicToggle.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (MainMenu.musicOn) {
                        MainMenu.music.setVolume(0, 0);
                        MainMenu.editor.putBoolean("musicOn", false);
                        MainMenu.editor.commit();
                        MainMenu.musicOn = MainMenu.sp.getBoolean("musicOn", false);
                        musicToggle.setText(R.string.music_off);
                    } else if (!MainMenu.musicOn) {
                        MainMenu.music.setVolume(0.75f, 0.75f);
                        MainMenu.editor.putBoolean("musicOn", true);
                        MainMenu.editor.commit();
                        MainMenu.musicOn = MainMenu.sp.getBoolean("musicOn", true);
                        musicToggle.setText(R.string.musicOn);
                    }
                }
                super.onTouch(view, event);
                return false;
            }
        });

        // sets the sound effects toggle button
        soundsToggle = (Button) findViewById(R.id.sounds);
        soundsToggle.getLayoutParams().height = MainMenu.screenHeight/15;

        // set the sounds text
        if (MainMenu.soundsOn){
            soundsToggle.setText(R.string.soundsOn);
        }
        else if (!MainMenu.soundsOn) {
            soundsToggle.setText(R.string.sounds_off);
        }
        // sets if sounds volume is on or off
        soundsToggle.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (MainMenu.soundsOn) {
                        // turns the music off
                        MainMenu.editor.putBoolean("soundsOn", false);
                        MainMenu.editor.commit();
                        MainMenu.soundsOn = MainMenu.sp.getBoolean("soundsOn", true);
                        soundsToggle.setText(R.string.sounds_off);
                    } else if (!MainMenu.soundsOn) {
                        // turns the music on
                        MainMenu.editor.putBoolean("soundsOn", true);
                        MainMenu.editor.commit();
                        MainMenu.soundsOn = MainMenu.sp.getBoolean("soundsOn", true);
                        soundsToggle.setText(R.string.soundsOn);
                    }
                }
                super.onTouch(view, event);
                return false;
            }
        });

        sizeToggle = (Button) findViewById(R.id.size);
        sizeToggle.getLayoutParams().height = MainMenu.screenHeight/15;

        // checks what the size is
        String size = MainMenu.sp.getString("size", "medium");
        switch (size){
            case "small": sizeToggle.setText(R.string.indicator_small);
                break;
            case "medium": sizeToggle.setText(R.string.indicator_medium);
                break;
            case "large": sizeToggle.setText(R.string.indicator_large);
                break;
        }
        sizeToggle.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    String size = MainMenu.sp.getString("size", "medium");
                    switch (size) {
                        case "small":
                            sizeToggle.setText(R.string.indicator_medium);
                            MainMenu.editor.putString("size", "medium");
                            MainMenu.editor.commit();
                            break;
                        case "medium":
                            sizeToggle.setText(R.string.indicator_large);
                            MainMenu.editor.putString("size", "large");
                            MainMenu.editor.commit();
                            break;
                        case "large":
                            sizeToggle.setText(R.string.indicator_small);
                            MainMenu.editor.putString("size", "small");
                            MainMenu.editor.commit();
                            break;
                    }
                }
                super.onTouch(view, event);
                return false;
            }
        });

    }

    @Override
    protected void onPause(){
        super.onPause();
        SoundManager.stopPlayingDelayed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.continueMusic();
    }
}
