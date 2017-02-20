package com.datrueonejay.canyouescape;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutMe extends AppCompatActivity {

    private int secretCounter;
    private static TextView me;
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
        secretCounter = 0;
        // sets the button
        Button secret = (Button) findViewById(R.id.secret);
        secret.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (secretCounter != 5) {
                    secretCounter++;
                }
                else {
                    Boolean change = MainMenu.sp.getBoolean("dev", false);
                    MainMenu.editor.putBoolean("dev", !change);
                    MainMenu.editor.commit();
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
