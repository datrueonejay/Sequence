package com.datrueonejay.canyouescape;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyCustomTextView extends AutoResizeTextView {

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

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
    }

    private void init() {
        try{
            if (!isInEditMode()) {
                // set font
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cornerstone.ttf");
                setTypeface(tf);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
