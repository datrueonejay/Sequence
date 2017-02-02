package com.datrueonejay.canyouescape;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;

public class SettingsInstructions extends AppCompatActivity {

    //WebView right;
    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_instructions);

        View view = findViewById(R.id.activity_settings_instructions);
        view.startAnimation(AnimationUtils.loadAnimation(SettingsInstructions.this, android.R.anim.fade_in));

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        close = (Button) findViewById(R.id.close);

        // button to close the instructions
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
