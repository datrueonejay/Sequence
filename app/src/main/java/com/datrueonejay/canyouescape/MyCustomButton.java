package com.datrueonejay.canyouescape;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;



public class MyCustomButton extends Button {

    public MyCustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        try{
            if (!isInEditMode()) {

                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cornerstone.ttf");
                setTypeface(tf);
                this.setBackgroundColor(getResources().getColor(R.color.blue));
                this.setTextColor(getResources().getColor(R.color.white));

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
