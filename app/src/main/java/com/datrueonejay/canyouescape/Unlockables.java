package com.datrueonejay.canyouescape;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class Unlockables extends AppCompatActivity {

    Context cont;

    HorizontalScrollView scroller;
    LinearLayout layout;
    String[] how_to;
    String[] descriptions;
    Button use;
    String[] skin = new String[6];
    Boolean[] conditions = new Boolean[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlockables);

        cont = this;


        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // finds all the scores to check conditions
//        int a = MainMenu.sp.getInt("main", 0);
//        int b = MainMenu.sp.getInt("half", 0);
//        int c = MainMenu.sp.getInt("one_minute", 0);
//        int d = MainMenu.sp.getInt("one_half", 0);
//        int e = MainMenu.sp.getInt("two_minutes", 0);
//        int f = MainMenu.sp.getInt("two_half", 0);
//        int g = MainMenu.sp.getInt("three_minutes", 0);
//
//        // sets condition to unlock
//        conditions[0] = true;
//        conditions[1] = a > 0 || b > 0 || c > 0 || d > 0 || e > 0 || f > 0 || g > 0;
//        conditions[2] =

        // finds the strings for descriptions
        descriptions = getResources().getStringArray(R.array.arrows);
        // finds the strings for how to unlock
        how_to = getResources().getStringArray(R.array.howto);
        // the skins in the game
        skin[0] = "classic";
        skin[1] = "invert";
        skin[2] = "pc";
        skin[3] = "xbox";
        skin[4] = "playstation";
        skin[5] = "ddr";

        scroller = (HorizontalScrollView) findViewById(R.id.activity_unlockables);
        layout = (LinearLayout) scroller.findViewById(R.id.linear_part);
        // create and set each page
        for (int counter = 0; counter < 6; counter++){
            // copies counter
            final int copy = counter;
            Skin skins = new Skin();
            skins.LoadSkin(skin[counter], cont);
            // find the current page
            RelativeLayout page = (RelativeLayout) layout.getChildAt(counter);
            page.getLayoutParams().width = MainMenu.screen_width;
            // finds description and sets it for current one
            final TextView description = (TextView) page.findViewById(R.id.description);
            description.getLayoutParams().height = MainMenu.screen_height/8;
            description.setText(descriptions[counter]);
            //correctHeight(description, MainMenu.screen_height/8);

            //int hi = MainMenu.screen_height/8;
            //description.setTextSize(hi*MainMenu.multiplier);
            // sets the picture for the up button
            ImageView up = (ImageView) page.findViewById(R.id.upButton);
            up.setBackground(skins.GetUp());
            up.getLayoutParams().width = MainMenu.screen_width/4;
            up.getLayoutParams().height = MainMenu.screen_width/4;
            // sets the picture for the up button
            ImageView left = (ImageView) page.findViewById(R.id.leftButton);
            left.setBackground(skins.GetLeft());
            left.getLayoutParams().width = MainMenu.screen_width/4;
            left.getLayoutParams().height = MainMenu.screen_width/4;
            // sets the picture for the up button
            ImageView down = (ImageView) page.findViewById(R.id.downButton);
            down.setBackground(skins.GetDown());
            down.getLayoutParams().width = MainMenu.screen_width/4;
            down.getLayoutParams().height = MainMenu.screen_width/4;
            // sets the picture for the up button
            ImageView right = (ImageView) page.findViewById(R.id.rightButton);
            right.setBackground(skins.GetRight());
            right.getLayoutParams().width = MainMenu.screen_width/4;
            right.getLayoutParams().height = MainMenu.screen_width/4;
            // sets the picture for the up button
            ImageView correct = (ImageView) page.findViewById(R.id.correct);
            correct.setBackground(skins.GetCorrect());
            correct.getLayoutParams().width = MainMenu.screen_width/6;
            correct.getLayoutParams().height = MainMenu.screen_width/6;
            // sets the picture for the up button
            ImageView incorrect = (ImageView) page.findViewById(R.id.incorrect);
            incorrect.setBackground(skins.GetIncorrect());
            incorrect.getLayoutParams().width = MainMenu.screen_width/6;
            incorrect.getLayoutParams().height = MainMenu.screen_width/6;

            // finds how to unlock and sets it for current one
            TextView how = (TextView) page.findViewById(R.id.how);
            how.setText(how_to[counter]);
            // find the use button for the current page
            use = (Button) page.findViewById(R.id.use);
            if (MainMenu.sp.getString("skin", "classic").equals(skin[copy])){
                use.setEnabled(false);
                use.setVisibility(View.INVISIBLE);
            }
            // set what happens when the button is clicked
            use.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // says the new skin to be used
                    MainMenu.editor.putString("skin", skin[copy]);
                    MainMenu.editor.commit();
                    // loop through all the pages, enabling and showing their button if it is not
                    // used, otherwise disable and hide the button
                    for (int counter = 0; counter <6; counter++){
                        RelativeLayout change = (RelativeLayout) layout.getChildAt(counter);
                        // find the use button
                        Button btn = (Button) change.findViewById(R.id.use);
                        // disables and hides the button if it is the one being used
                        if (MainMenu.sp.getString("skin", "classic").equals(skin[counter])){
                            btn.setEnabled(false);
                            btn.setVisibility(View.INVISIBLE);
                        }
                        // enables and shows the button
                        else{
                            btn.setEnabled(true);
                            btn.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

        }




    }

    public void correctHeight(final TextView textView, final int desiredHeight)
    {
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = textView.getHeight(); //height is ready
                while (height > desiredHeight){
                    float test = textView.getTextSize();
                    float ha = test - 1;
                    textView.setTextSize(COMPLEX_UNIT_PX, ha);
                    height = height - 10;
                }
            }
        });

    }
}
