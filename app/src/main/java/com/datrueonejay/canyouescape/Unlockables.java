package com.datrueonejay.canyouescape;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Unlockables extends AppCompatActivity {

    Context cont;

    DisplayMetrics dimensions = new DisplayMetrics();

    HorizontalScrollView scroller;
    LinearLayout layout;
    String[] how_to;
    String[] descriptions;
    Button use;
    String[] skin = new String[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlockables);

        cont = this;

        this.getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        int width = dimensions.widthPixels;

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
            page.getLayoutParams().width = width;
            // finds description and sets it for current one
            TextView description = (TextView) page.findViewById(R.id.description);
            // sets the picture for the up button
            ImageView up = (ImageView) page.findViewById(R.id.upButton);
            up.setBackground(skins.GetUp());
            // sets the picture for the up button
            ImageView left = (ImageView) page.findViewById(R.id.leftButton);
            left.setBackground(skins.GetLeft());
            // sets the picture for the up button
            ImageView down = (ImageView) page.findViewById(R.id.downButton);
            down.setBackground(skins.GetDown());
            // sets the picture for the up button
            ImageView right = (ImageView) page.findViewById(R.id.rightButton);
            right.setBackground(skins.GetRight());
            // sets the picture for the up button
            ImageView correct = (ImageView) page.findViewById(R.id.correct);
            correct.setBackground(skins.GetCorrect());
            // sets the picture for the up button
            ImageView incorrect = (ImageView) page.findViewById(R.id.incorrect);
            incorrect.setBackground(skins.GetIncorrect());
            // sets it to proper one
            description.setText(descriptions[counter]);
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
}
