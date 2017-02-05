package com.datrueonejay.canyouescape;

import android.graphics.drawable.Drawable;

public class DoubleDrawable {

    Drawable first_one;
    Drawable second_one;

    DoubleDrawable(Drawable first, Drawable second){
        first_one = first;
        second_one = second;
    }

    public Drawable GetFirst(){
        return first_one;
    }

    public Drawable GetSecond(){
        return second_one;
    }
}
