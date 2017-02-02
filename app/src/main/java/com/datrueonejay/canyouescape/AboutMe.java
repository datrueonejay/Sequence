package com.datrueonejay.canyouescape;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutMe extends AppCompatActivity {

    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        close = (Button) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            // create the listener
            public void onClick(View view) {
                onBackPressed();
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
