package com.datrueonejay.canyouescape;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class SettingsInstructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_instructions);

        View view = findViewById(R.id.activity_settings_instructions);
        view.startAnimation(AnimationUtils.loadAnimation(SettingsInstructions.this, android.R.anim.fade_in));
        // finds the instructions
        TextView instruction = (TextView) findViewById(R.id.instructions);
        // sets it to an eighth of the screen
        instruction.getLayoutParams().height = MainMenu.screenHeight/7;
        instruction.setText(this.getResources().getString(R.string.instruct));

        // finds the instructions
        TextView right = (TextView) findViewById(R.id.right);
        // sets it to an eighth of the screen
        right.getLayoutParams().height = MainMenu.screenHeight/7;
        right.setText(this.getResources().getString(R.string.right));

        // finds the instructions
        TextView wrong = (TextView) findViewById(R.id.wrong);
        // sets it to an eighth of the screen
        wrong.getLayoutParams().height = MainMenu.screenHeight/7;
        wrong.setText(this.getResources().getString(R.string.wrong));

        // finds the instructions
        TextView next = (TextView) findViewById(R.id.next);
        // sets it to an eighth of the screen
        next.getLayoutParams().height = MainMenu.screenHeight/10;
        next.setText(this.getResources().getString(R.string.next));

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
