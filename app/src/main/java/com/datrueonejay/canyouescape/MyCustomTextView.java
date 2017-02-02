package com.datrueonejay.canyouescape;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;



public class MyCustomTextView extends TextView {

    public MyCustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        try{
            if (!isInEditMode()) {

                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cornerstone.ttf");
                setTypeface(tf);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
