package com.datrueonejay.canyouescape;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    Button back;
    Button help;
    Button me;
    Button music_toggle;
    Button sounds_toggle;
    Button size_toggle;

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
        // go back to the game
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        help = (Button) findViewById(R.id.help);
        help.setText(R.string.help);
        // opens instructions
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(Settings.this, SettingsInstructions.class);
                startActivity(activity);
            }
        });

        me = (Button) findViewById(R.id.me);
        me.setText(R.string.me);
        // opens screen about me
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(Settings.this, AboutMe.class);
                startActivity(activity);
            }
        });

        music_toggle = (Button) findViewById(R.id.music);
        // set the music text upon entering the settings menu
        if (MainMenu.music_on){
            music_toggle.setText(R.string.music_on);
        }
        else if (!MainMenu.music_on){
            music_toggle.setText(R.string.music_off);
        }
        // turns music volume to 0
        music_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainMenu.music_on){
                    MainMenu.music.setVolume(0, 0);
                    MainMenu.editor.putBoolean("music_on", false);
                    MainMenu.editor.commit();
                    MainMenu.music_on = MainMenu.sp.getBoolean("music_on", false);
                    music_toggle.setText(R.string.music_off);
                }
                else if (!MainMenu.music_on){
                    MainMenu.music.setVolume(0.75f, 0.75f);
                    MainMenu.editor.putBoolean("music_on", true);
                    MainMenu.editor.commit();
                    MainMenu.music_on = MainMenu.sp.getBoolean("music_on", true);
                    music_toggle.setText(R.string.music_on);
                }
            }
        });

        sounds_toggle = (Button) findViewById(R.id.sounds);
        // set the sounds text
        if (MainMenu.sounds_on){
            sounds_toggle.setText(R.string.sounds_on);
        }
        else if (!MainMenu.sounds_on) {
            sounds_toggle.setText(R.string.sounds_off);
        }
        // sets if sounds volume is on or off
        sounds_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainMenu.sounds_on){
                    // turns the music off
                    MainMenu.editor.putBoolean("sounds_on", false);
                    MainMenu.editor.commit();
                    MainMenu.sounds_on = MainMenu.sp.getBoolean("sounds_on", true);
                    sounds_toggle.setText(R.string.sounds_off);
                }
                else if (!MainMenu.sounds_on){
                    // turns the music on
                    MainMenu.editor.putBoolean("sounds_on", true);
                    MainMenu.editor.commit();
                    MainMenu.sounds_on = MainMenu.sp.getBoolean("sounds_on", true);
                    sounds_toggle.setText(R.string.sounds_on);
                }
            }
        });

        size_toggle = (Button) findViewById(R.id.size);
        // checks what the size is
        String size = MainMenu.sp.getString("size", "medium");
        switch (size){
            case "small": size_toggle.setText(R.string.indicator_small);
                break;
            case "medium": size_toggle.setText(R.string.indicator_medium);
                break;
            case "large": size_toggle.setText(R.string.indicator_large);
                break;
        }
        size_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String size = MainMenu.sp.getString("size", "medium");
                switch (size) {
                    case "small": size_toggle.setText(R.string.indicator_medium);
                        MainMenu.editor.putString("size", "medium");
                        MainMenu.editor.commit();
                        break;
                    case "medium": size_toggle.setText(R.string.indicator_large);
                        MainMenu.editor.putString("size", "large");
                        MainMenu.editor.commit();
                        break;
                    case "large": size_toggle.setText(R.string.indicator_small);
                        MainMenu.editor.putString("size", "small");
                        MainMenu.editor.commit();
                        break;
                }
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
