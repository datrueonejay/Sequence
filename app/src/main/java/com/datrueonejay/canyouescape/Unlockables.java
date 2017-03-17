package com.datrueonejay.canyouescape;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
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

    Context cont;

    ExampleScrollView scroller;
    LinearLayout layout;
    String[] howTo;
    String[] descriptions;
    Button use;
    String[] skin = new String[9];
    public static Boolean[] conditions = new Boolean[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlockables);

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
            conditions[1] = a > 0 || b > 0 || c > 0 || d > 0 || e > 0 || f > 0;
            conditions[2] = a > 4 || b > 4 || c > 4 || d > 4 || e > 4 || f > 4;
            conditions[3] = a > 9;
            conditions[4] = a > 14;
            conditions[5] = b > 9 || c > 9 || d > 9 || e > 9 || f > 9;
            conditions[6] = b > 9 || c > 9 || d > 9 || e > 9 || f > 9;
            conditions[7] = g <= 20;
            conditions[8] = h <= 40;
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

        scroller = (ExampleScrollView) findViewById(R.id.activity_unlockables);
        layout = (LinearLayout) scroller.findViewById(R.id.linear_part);

        scroller.setOnScrollViewListener(new ExampleScrollView.OnScrollViewListener() {
            public void onScrollChanged( ExampleScrollView v, int l, int t, int oldl, int oldt ) {
                Rect scrollBounds = new Rect();
                scroller.getHitRect(scrollBounds);
                layout.getChildAt(0).setVisibility(View.INVISIBLE);
                layout.getChildAt(1).setVisibility(View.INVISIBLE);
                layout.getChildAt(2).setVisibility(View.INVISIBLE);
                layout.getChildAt(3).setVisibility(View.INVISIBLE);
                layout.getChildAt(4).setVisibility(View.INVISIBLE);
                layout.getChildAt(5).setVisibility(View.INVISIBLE);
                layout.getChildAt(6).setVisibility(View.INVISIBLE);
                layout.getChildAt(7).setVisibility(View.INVISIBLE);
                layout.getChildAt(8).setVisibility(View.INVISIBLE);
                if (layout.getChildAt(0).getLocalVisibleRect(scrollBounds))
                    layout.getChildAt(0).setVisibility(View.VISIBLE);
                if (layout.getChildAt(1).getLocalVisibleRect(scrollBounds))
                    layout.getChildAt(1).setVisibility(View.VISIBLE);
                if (layout.getChildAt(2).getLocalVisibleRect(scrollBounds))
                    layout.getChildAt(2).setVisibility(View.VISIBLE);
                if (layout.getChildAt(3).getLocalVisibleRect(scrollBounds))
                    layout.getChildAt(3).setVisibility(View.VISIBLE);
                if (layout.getChildAt(4).getLocalVisibleRect(scrollBounds))
                    layout.getChildAt(4).setVisibility(View.VISIBLE);
                if (layout.getChildAt(5).getLocalVisibleRect(scrollBounds))
                    layout.getChildAt(5).setVisibility(View.VISIBLE);
                if (layout.getChildAt(6).getLocalVisibleRect(scrollBounds))
                    layout.getChildAt(6).setVisibility(View.VISIBLE);
                if (layout.getChildAt(7).getLocalVisibleRect(scrollBounds))
                    layout.getChildAt(7).setVisibility(View.VISIBLE);
                if (layout.getChildAt(8).getLocalVisibleRect(scrollBounds))
                    layout.getChildAt(8).setVisibility(View.VISIBLE);
        }});


        // create and set each page
        for (int counter = 0; counter < 9; counter++){


            // copies counter
            final int copy = counter;
            Skin skins = new Skin();
            skins.LoadSkin(skin[counter], cont);
            // find the current page
            final RelativeLayout page = (RelativeLayout) layout.getChildAt(counter);
            page.getLayoutParams().width = MainMenu.screenWidth;
            TextView title = (TextView) page.findViewById(R.id.title);
            title.setText(this.getString(R.string.unlockables));

            // finds how to unlock and sets it for current one
            TextView how = (TextView) page.findViewById(R.id.how);
            how.setText(howTo[counter]);

            // sets the locked button if it is locked
            if (!conditions[counter]){
                ImageView lock = (ImageView) page.findViewById(R.id.lock);
                lock.setBackground(this.getResources().getDrawable(R.drawable.locked));
            }
            else {
                // sets the picture for the up button
                ImageView up = (ImageView) page.findViewById(R.id.upButton);
                up.setBackground(skins.GetUp());
                up.getLayoutParams().width = MainMenu.screenWidth / 4;
                up.getLayoutParams().height = MainMenu.screenWidth / 4;
                // sets the picture for the up button
                ImageView left = (ImageView) page.findViewById(R.id.leftButton);
                left.setBackground(skins.GetLeft());
                left.getLayoutParams().width = MainMenu.screenWidth / 4;
                left.getLayoutParams().height = MainMenu.screenWidth / 4;
                // sets the picture for the up button
                ImageView down = (ImageView) page.findViewById(R.id.downButton);
                down.setBackground(skins.GetDown());
                down.getLayoutParams().width = MainMenu.screenWidth / 4;
                down.getLayoutParams().height = MainMenu.screenWidth / 4;
                // sets the picture for the up button
                ImageView right = (ImageView) page.findViewById(R.id.rightButton);
                right.setBackground(skins.GetRight());
                right.getLayoutParams().width = MainMenu.screenWidth / 4;
                right.getLayoutParams().height = MainMenu.screenWidth / 4;
                // sets the picture for the up button
                ImageView correct = (ImageView) page.findViewById(R.id.correct);
                correct.setBackground(skins.GetCorrect());
                correct.getLayoutParams().width = MainMenu.screenWidth / 6;
                correct.getLayoutParams().height = MainMenu.screenWidth / 6;
                // sets the picture for the up button
                ImageView incorrect = (ImageView) page.findViewById(R.id.incorrect);
                incorrect.setBackground(skins.GetIncorrect());
                incorrect.getLayoutParams().width = MainMenu.screenWidth / 6;
                incorrect.getLayoutParams().height = MainMenu.screenWidth / 6;

                // find the use button for the current page
                use = (Button) page.findViewById(R.id.use);
                use.setText(getString(R.string.use));
                use.setVisibility(View.VISIBLE);
                if (MainMenu.sp.getString("skin", "classic").equals(skin[copy])){
                    use.setEnabled(false);
                    use.setVisibility(View.INVISIBLE);
                }

                // finds description and sets it for current one
                final TextView description = (TextView) page.findViewById(R.id.description);
                description.getLayoutParams().height = MainMenu.screenHeight/8;
                description.setText(descriptions[counter]);

                // set what happens when the button is clicked
                use.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN){
                            // says the new skin to be used
                            MainMenu.editor.putString("skin", skin[copy]);
                            MainMenu.editor.commit();
                            // loop through all the pages, enabling and showing their button if it is not
                            // used, otherwise disable and hide the button
                            for (int counter = 0; counter < 9; counter++) {
                                RelativeLayout change = (RelativeLayout) layout.getChildAt(counter);
                                // find the use button
                                Button btn = (Button) change.findViewById(R.id.use);
                                // disables and hides the button if it is the one being used
                                if (MainMenu.sp.getString("skin", "classic").equals(skin[counter])) {
                                    btn.setEnabled(false);
                                    btn.setVisibility(View.INVISIBLE);
                                }
                                // enables and shows the button
                                else {
                                    btn.setEnabled(true);
                                    btn.setVisibility(View.VISIBLE);
                                }
                                // check if the condition is false and if so hide the button
                                if (conditions[counter] != true) {
                                    btn.setEnabled(false);
                                    btn.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        return false;
                    }
                });
            }


        }

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
