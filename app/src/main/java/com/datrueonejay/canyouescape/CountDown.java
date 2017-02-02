package com.datrueonejay.canyouescape;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CountDown extends AppCompatActivity {

    CountDownTimer countdown;
    TextView time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);

        time = (TextView) findViewById(R.id.counter);

        countdown = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished/1000)==1){
                    Intent intent = new Intent(CountDown.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                    time.setText(String.valueOf((millisUntilFinished/1000)-1));
            }

            @Override
            public void onFinish() {
//                Intent intent = new Intent(CountDown.this, MainActivity.class);
//                startActivity(intent);
                CountDown.this.finish();
            }
        }.start();
    }

    @Override
    public void onBackPressed(){

    }
}
