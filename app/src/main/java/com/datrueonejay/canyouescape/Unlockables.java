package com.datrueonejay.canyouescape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Unlockables extends AppCompatActivity {

    HorizontalScrollView scroller;
    LinearLayout layout;
    String[] how_to;
    String[] descriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlockables);

        // finds the strings
        how_to = getResources().getStringArray(R.array.arrows);

        scroller = (HorizontalScrollView) findViewById(R.id.activity_unlockables);
        layout = (LinearLayout) scroller.findViewById(R.id.linear_part);
        // create and set each page
        for (int counter = 0; counter < 4; counter++){
            // find the current page number
            String page_num = "page" + String.valueOf(counter);
            // find the resource id of the page
            //int id = layout.getResources().getIdentifier("page1", "layout", Unlockables.this.getPackageName());
            // finds the current page
            RelativeLayout page = (RelativeLayout) layout.getChildAt(counter);

            //RelativeLayout page = (RelativeLayout) layout.findViewById(id);


            // finds description and sets it for current one
            TextView description = (TextView) page.findViewById(R.id.description);
            // sets it to proper one
            description.setText(how_to[counter]);

        }




    }
}
