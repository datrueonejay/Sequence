package com.datrueonejay.canyouescape;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private int secretCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // resumes music
        MainMenu.music.start();

        Button back = (Button) findViewById(R.id.back);
        back.getLayoutParams().height = MainMenu.screenHeight/15;
        back.getLayoutParams().width = MainMenu.screenWidth/4;

        TextView backText = (TextView) findViewById(R.id.back_text);
        backText.getLayoutParams().height = MainMenu.screenHeight/15;
        backText.getLayoutParams().width = MainMenu.screenWidth/4;
        backText.setPadding(0, MainMenu.screenHeight/60, 0, MainMenu.screenHeight/60);
        backText.setText(R.string.back);

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

        Button help = (Button) findViewById(R.id.help);
        help.getLayoutParams().height = MainMenu.screenHeight/15;

        TextView helpText = (TextView) findViewById(R.id.help_text);
        helpText.setText(R.string.help);
        helpText.setPadding(0, MainMenu.screenHeight/60, 0, MainMenu.screenHeight/60);
        helpText.getLayoutParams().height = MainMenu.screenHeight/15;

        // opens instructions
        help.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Dialog dialog = new Dialog(Settings.this);
                    dialog.setContentView(R.layout.activity_settings_instructions);

                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    title.setText(R.string.help);

                    TextView info = (TextView) dialog.findViewById(R.id.info);
                    info.getLayoutParams().height = MainMenu.screenHeight/4;
                    info.setText(R.string.info);

                    ImageView pic = (ImageView) dialog.findViewById(R.id.pic);
                    pic.getLayoutParams().height = MainMenu.screenWidth/4;

                    TextView closing = (TextView) dialog.findViewById(R.id.closing);
                    closing.getLayoutParams().height = MainMenu.screenHeight/10;
                    closing.setText(R.string.next);

                    dialog.show();
                }
                super.onTouch(view, event);
                return false;
            }
        });

        Button me = (Button) findViewById(R.id.me);
        me.getLayoutParams().height = MainMenu.screenHeight/15;

        TextView meText = (TextView) findViewById(R.id.me_text);
        meText.setPadding(0, MainMenu.screenHeight/60, 0, MainMenu.screenHeight/60);
        meText.setText(R.string.me);
        meText.getLayoutParams().height = MainMenu.screenHeight/15;

        // opens screen about me
        me.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //startActivity(activity);
                    Dialog dialog = new Dialog(Settings.this);
                    dialog.setContentView(R.layout.activity_about_me);
                    // set the title
                    final TextView title = (TextView) dialog.findViewById(R.id.title);
                    title.setText(R.string.me);
                    // set the text
                    final TextView me = (TextView) dialog.findViewById(R.id.me);
                    me.getLayoutParams().height = MainMenu.screenHeight/2;
                    me.setText(getResources().getString(R.string.jayden));
                    // sets the counter for the secret button
                    secretCounter = 1;
                    // sets the button
                    Button secret = (Button) dialog.findViewById(R.id.secret);
                    secret.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            if (secretCounter != 14) {
                                MainMenu.editor.putBoolean("dev", false);
                                MainMenu.editor.commit();
                                me.setText(getResources().getString(R.string.jayden));
                            }
                            else {
                                MainMenu.editor.putBoolean("dev", true);
                                MainMenu.editor.commit();
                                me.setText(getResources().getString(R.string.jayden_dev));
                            }
                            secretCounter++;
                        }
                    });
                    dialog.show();
                }
                super.onTouch(view, event);
                return false;
            }
        });

        Button ownership = (Button) findViewById(R.id.ownership);
        ownership.getLayoutParams().height = MainMenu.screenHeight/15;

        TextView ownershipText = (TextView) findViewById(R.id.ownership_text);
        ownershipText.setPadding(0, MainMenu.screenHeight/60, 0, MainMenu.screenHeight/60);
        ownershipText.setText(R.string.copyright);
        ownershipText.getLayoutParams().height = MainMenu.screenHeight/15;

        // opens screen about me
        ownership.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Dialog dialog = new Dialog(Settings.this);
                    dialog.setContentView(R.layout.activity_ownership);

                    // set the title
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    title.setText(R.string.copyright);

                    // set the text
                    TextView ownership = (TextView) dialog.findViewById(R.id.ownership);
                    ownership.getLayoutParams().height = MainMenu.screenHeight/3;
                    ownership.setText(R.string.ownership);

                    dialog.show();
                }
                super.onTouch(view, event);
                return false;
            }
        });

        // sets the music toggle button
        Button musicToggle = (Button) findViewById(R.id.music);
        musicToggle.getLayoutParams().height = MainMenu.screenHeight/15;

        final TextView musicToggleText = (TextView) findViewById(R.id.music_text);
        musicToggleText.setPadding(0, MainMenu.screenHeight/60, 0, MainMenu.screenHeight/60);
        musicToggleText.getLayoutParams().height = MainMenu.screenHeight/15;

        // set the music text upon entering the settings menu
        if (MainMenu.musicOn){
            musicToggleText.setText(R.string.musicOn);
        }
        else if (!MainMenu.musicOn){
            musicToggleText.setText(R.string.music_off);
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
                        musicToggleText.setText(R.string.music_off);
                    } else if (!MainMenu.musicOn) {
                        MainMenu.music.setVolume(0.75f, 0.75f);
                        MainMenu.editor.putBoolean("musicOn", true);
                        MainMenu.editor.commit();
                        MainMenu.musicOn = MainMenu.sp.getBoolean("musicOn", true);
                        musicToggleText.setText(R.string.musicOn);
                    }
                }
                super.onTouch(view, event);
                return false;
            }
        });

        // sets the sound effects toggle button
        Button soundsToggle = (Button) findViewById(R.id.sounds);
        soundsToggle.getLayoutParams().height = MainMenu.screenHeight/15;

        final TextView soundsToggleText = (TextView) findViewById(R.id.sounds_text);
        soundsToggleText.setPadding(0, MainMenu.screenHeight/60, 0, MainMenu.screenHeight/60);
        soundsToggleText.getLayoutParams().height = MainMenu.screenHeight/15;

        // set the sounds text
        if (MainMenu.soundsOn){
            soundsToggleText.setText(R.string.soundsOn);
        }
        else if (!MainMenu.soundsOn) {
            soundsToggleText.setText(R.string.sounds_off);
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
                        soundsToggleText.setText(R.string.sounds_off);
                    } else if (!MainMenu.soundsOn) {
                        // turns the music on
                        MainMenu.editor.putBoolean("soundsOn", true);
                        MainMenu.editor.commit();
                        MainMenu.soundsOn = MainMenu.sp.getBoolean("soundsOn", true);
                        soundsToggleText.setText(R.string.soundsOn);
                    }
                }
                super.onTouch(view, event);
                return false;
            }
        });

        Button sizeToggle = (Button) findViewById(R.id.size);
        sizeToggle.getLayoutParams().height = MainMenu.screenHeight/15;

        final TextView sizeToggleText = (TextView) findViewById(R.id.size_text);
        sizeToggleText.setPadding(0, MainMenu.screenHeight/60, 0, MainMenu.screenHeight/60);
        sizeToggleText.getLayoutParams().height = MainMenu.screenHeight/15;

        // checks what the size is
        String size = MainMenu.sp.getString("size", "medium");
        switch (size){
            case "small": sizeToggleText.setText(R.string.indicator_small);
                break;
            case "medium": sizeToggleText.setText(R.string.indicator_medium);
                break;
            case "large": sizeToggleText.setText(R.string.indicator_large);
                break;
        }
        sizeToggle.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    String size = MainMenu.sp.getString("size", "medium");
                    switch (size) {
                        case "small":
                            sizeToggleText.setText(R.string.indicator_medium);
                            MainMenu.editor.putString("size", "medium");
                            MainMenu.editor.commit();
                            break;
                        case "medium":
                            sizeToggleText.setText(R.string.indicator_large);
                            MainMenu.editor.putString("size", "large");
                            MainMenu.editor.commit();
                            break;
                        case "large":
                            sizeToggleText.setText(R.string.indicator_small);
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
