package com.datrueonejay.canyouescape;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CountDown extends AppCompatActivity {

    CountDownTimer countdown;
    TextView begins;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // sets the title
        begins = (TextView) findViewById(R.id.title);
        begins.setText(getString(R.string.begins));

        time = (TextView) findViewById(R.id.counter);

        // sets the timer for 5 seconds
        countdown = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished/1000)==1){
                    Intent intent = new Intent(CountDown.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    time.setText(String.valueOf((millisUntilFinished/1000)-1));
                }
            }

            @Override
            public void onFinish() {
                CountDown.this.finish();
            }
        }.start();
    }

    @Override
    public void onBackPressed(){

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
