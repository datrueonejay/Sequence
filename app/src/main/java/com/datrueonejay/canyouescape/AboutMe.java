package com.datrueonejay.canyouescape;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutMe extends AppCompatActivity {

    private int secretCounter;
    private TextView me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // set the text
        me = (TextView) findViewById(R.id.me);
        me.getLayoutParams().height = MainMenu.screen_height/3;
        me.setText(getResources().getString(R.string.jayden));
        // sets the counter for the secret button
        secretCounter = 1;
        // sets the button
        Button secret = (Button) findViewById(R.id.secret);
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
