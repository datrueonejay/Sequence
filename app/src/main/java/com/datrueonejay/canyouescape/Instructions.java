package com.datrueonejay.canyouescape;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Instructions extends AppCompatActivity {

    TextView welcome;
    TextView instructions;
    TextView right;
    TextView wrong;
    ImageView pic;
    ImageView pic_two;
    TextView next;
    Button screen;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        // ensures that the music plays
        SoundManager.continueMusic();

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText(getString(R.string.welcome));

        instructions = (TextView) findViewById(R.id.instructions);
        right = (TextView) findViewById(R.id.right);
        wrong = (TextView) findViewById(R.id.wrong);
        pic = (ImageView) findViewById(R.id.pic);
        pic_two = (ImageView) findViewById(R.id.pic_two);
        next = (TextView) findViewById(R.id.next);
        screen = (Button) findViewById(R.id.screen);


        // wait 3 seconds before setting instructions
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                instructions.setText(getString(R.string.instruct));
                instructions.setVisibility(View.VISIBLE);
            }}, 3000);

        // wait 6 seconds before setting background, right, and wrong text, and pictures
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                right.setText(getString(R.string.right));
                right.setVisibility(View.VISIBLE);
                wrong.setText(getString(R.string.wrong));
                wrong.setVisibility(View.VISIBLE);
                pic.setVisibility(View.VISIBLE);
                pic_two.setVisibility(View.VISIBLE);
            }}, 6000);

//        // wait 12 seconds before setting next
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                next.setVisibility(View.VISIBLE);
//            }}, 9000);


        // wait 12 seconds before setting buttons
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                next.setText(getString(R.string.next_more));
                next.setVisibility(View.VISIBLE);
                screen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // makes sure you can not go back to this screen
                        finish();
                        onBackPressed();
                    }
                });
            }}, 9000);

    }

    @Override
    protected void onPause(){
        super.onPause();
        SoundManager.stopPlayingDelayed();
    }

    @Override
    protected void onResume(){
        super.onResume();
        SoundManager.continueMusic();
    }

    @Override
    public void onBackPressed(){
    }
}
