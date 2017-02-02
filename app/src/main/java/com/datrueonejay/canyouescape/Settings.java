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
    Button indicator_toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back = (Button) findViewById(R.id.back);
        help = (Button) findViewById(R.id.help);
        me = (Button) findViewById(R.id.me);
        music_toggle = (Button) findViewById(R.id.music);
        sounds_toggle = (Button) findViewById(R.id.sounds);
        indicator_toggle = (Button) findViewById(R.id.indicator);

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // set the music text upon entering the settings menu
        if (MainMenu.music_on){
            music_toggle.setText(R.string.music_on);
        }
        else if (!MainMenu.music_on){
            music_toggle.setText(R.string.music_off);
        }

        // set the sounds text
        if (MainMenu.sounds_on){
            sounds_toggle.setText(R.string.sounds_on);
        }
        else if (!MainMenu.sounds_on) {
            sounds_toggle.setText(R.string.sounds_off);
        }

        // set the indicator text
        if (MainMenu.indicator_box){
            indicator_toggle.setText(R.string.indicator_box);
        }
        else if (!MainMenu.indicator_box) {
            indicator_toggle.setText(R.string.indicator_background);
        }


        // resumes music
        MainMenu.music.start();

        // go back to the game
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // opens instructions
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(Settings.this, SettingsInstructions.class);
                startActivity(activity);
            }
        });

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

        // sets if sounds volume is on or off
        sounds_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainMenu.sounds_on){
                    MainMenu.editor.putBoolean("sounds_on", false);
                    MainMenu.editor.commit();
                    MainMenu.sounds_on = MainMenu.sp.getBoolean("sounds_on", true);
                    sounds_toggle.setText(R.string.sounds_off);
                }
                else if (!MainMenu.sounds_on){
                    MainMenu.editor.putBoolean("sounds_on", true);
                    MainMenu.editor.commit();
                    MainMenu.sounds_on = MainMenu.sp.getBoolean("sounds_on", true);
                    sounds_toggle.setText(R.string.sounds_on);
                }
            }
        });

        // sets if indicator is a box or the background
        indicator_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainMenu.indicator_box){
                    MainMenu.editor.putBoolean("indicator_box", false);
                    MainMenu.editor.commit();
                    MainMenu.indicator_box = MainMenu.sp.getBoolean("indicator_box", false);
                    Dialog dialog = new Dialog(Settings.this);
                    dialog.setContentView(R.layout.warning);
                    dialog.show();
                    indicator_toggle.setText(R.string.indicator_background);
                }
                else if (!MainMenu.indicator_box){
                    MainMenu.editor.putBoolean("indicator_box", true);
                    MainMenu.editor.commit();
                    MainMenu.indicator_box = MainMenu.sp.getBoolean("indicator_box", true);
                    indicator_toggle.setText(R.string.indicator_box);
                }
            }
        });

        // opens screen about me
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(Settings.this, AboutMe.class);
                startActivity(activity);
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
