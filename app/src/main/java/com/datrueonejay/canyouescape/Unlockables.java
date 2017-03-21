package com.datrueonejay.canyouescape;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Unlockables extends AppCompatActivity {

    public static Context cont;

    LinearLayout layout;
    public static String[] howTo;
    public static String[] descriptions;
    public static String[] skin = new String[9];


    public static Boolean[] conditions = new Boolean[9];
    public static RelativeLayout[] pages = new RelativeLayout[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unlockables_view_pager);

        cont = this;

        // resumes music
        MainMenu.music.start();

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // finds all the scores to check conditions
        int a = MainMenu.sp.getInt("main", 0);
        int b = MainMenu.sp.getInt("half", 0);
        int c = MainMenu.sp.getInt("one_minute", 0);
        int d = MainMenu.sp.getInt("one_half", 0);
        int e = MainMenu.sp.getInt("two_minutes", 0);
        int f = MainMenu.sp.getInt("three_minutes", 0);
        int g = MainMenu.sp.getInt("five", 9999);
        int h = MainMenu.sp.getInt("seven", 9999);
        int i = MainMenu.sp.getInt("ten", 9999);
        int k = MainMenu.sp.getInt("fifteen", 9999);



        if (MainMenu.sp.getBoolean("dev", false)){
            for (int count = 0; count < 9; count++){
                conditions[count] = true;
            }
        }
        else{
            // sets condition to unlock
            conditions[0] = true;
            conditions[1] = a > 4 || b > 4 || c > 4 || d > 4 || e > 4 || f > 4;
            conditions[2] = a > 9 || b > 9 || c > 9 || d > 9 || e > 9 || f > 9;
            conditions[3] = a > 14;
            conditions[4] = a > 19;
            conditions[5] = b > 9 || c > 9 || d > 9 || e > 9 || f > 9;
            conditions[6] = b > 9 || c > 9 || d > 9 || e > 9 || f > 9;
            conditions[7] = g < 20;
            conditions[8] = h < 40;
        }


        // finds the strings for descriptions
        descriptions = getResources().getStringArray(R.array.arrows);
        // finds the strings for how to unlock
        howTo = getResources().getStringArray(R.array.howto);
        // the skins in the game
        skin[0] = "classic";
        skin[1] = "invert";
        skin[2] = "invisible";
        skin[3] = "simon";
        skin[4] = "pc";
        skin[5] = "xbox";
        skin[6] = "playstation";
        skin[7] = "cards";
        skin[8] = "ddr";

        MyPagerAdapter adapter = new MyPagerAdapter();
        ViewPager myPager = (ViewPager) findViewById(R.id.pages);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);

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
