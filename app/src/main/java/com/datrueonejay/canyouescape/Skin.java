package com.datrueonejay.canyouescape;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Skin {

    private static Drawable[] objects = new Drawable[6];

    // takes in the name of the skin, sets background resources, sounds and gets correct and
    // incorrect pictures
    Skin(){
    }

    public static void LoadSkin(String skin, Context cont){
        /**
         * Given a skin name, the function find the corresponding pictures used for the skin. The
         * element at [0] is the up arrow, [1] is left, [2] is down, [3] is right, [4] is correct
         * indicator, [5] is the incorrect indicator
         */
        switch(skin){
            case("classic"):
                objects[0] = cont.getResources().getDrawable(R.drawable.uparrow);
                objects[1] = cont.getResources().getDrawable(R.drawable.leftarrow);
                objects[2] = cont.getResources().getDrawable(R.drawable.downarrow);
                objects[3] = cont.getResources().getDrawable(R.drawable.rightarrow);
                objects[4] = cont.getResources().getDrawable(R.drawable.green);
                objects[5] = cont.getResources().getDrawable(R.drawable.red);
                break;
            case("invert"):
                objects[0] = cont.getResources().getDrawable(R.drawable.downarrow);
                objects[1] = cont.getResources().getDrawable(R.drawable.rightarrow);
                objects[2] = cont.getResources().getDrawable(R.drawable.uparrow);
                objects[3] = cont.getResources().getDrawable(R.drawable.leftarrow);
                objects[4] = cont.getResources().getDrawable(R.drawable.red);
                objects[5] = cont.getResources().getDrawable(R.drawable.green);
                break;
            case("pc"):
                objects[0] = cont.getResources().getDrawable(R.drawable.w);
                objects[1] = cont.getResources().getDrawable(R.drawable.a);
                objects[2] = cont.getResources().getDrawable(R.drawable.s);
                objects[3] = cont.getResources().getDrawable(R.drawable.d);
                objects[4] = cont.getResources().getDrawable(R.drawable.windows);
                objects[5] = cont.getResources().getDrawable(R.drawable.error);
                break;
            case("xbox"):
                objects[0] = cont.getResources().getDrawable(R.drawable.xboxy);
                objects[1] = cont.getResources().getDrawable(R.drawable.xboxx);
                objects[2] = cont.getResources().getDrawable(R.drawable.xboxa);
                objects[3] = cont.getResources().getDrawable(R.drawable.xboxb);
                objects[4] = cont.getResources().getDrawable(R.drawable.xboxcorrect);
                objects[5] = cont.getResources().getDrawable(R.drawable.xboxwrong);
                break;
            case("playstation"):
                objects[0] = cont.getResources().getDrawable(R.drawable.playstationtriangle);
                objects[1] = cont.getResources().getDrawable(R.drawable.playstationsquare);
                objects[2] = cont.getResources().getDrawable(R.drawable.playstationx);
                objects[3] = cont.getResources().getDrawable(R.drawable.playstationcircle);
                objects[4] = cont.getResources().getDrawable(R.drawable.playstationtrophy);
                objects[5] = cont.getResources().getDrawable(R.drawable.redx);
                break;
            case("ddr"):
                objects[0] = cont.getResources().getDrawable(R.drawable.ddrup);
                objects[1] = cont.getResources().getDrawable(R.drawable.ddrleft);
                objects[2] = cont.getResources().getDrawable(R.drawable.ddrdown);
                objects[3] = cont.getResources().getDrawable(R.drawable.ddrright);
                objects[4] = cont.getResources().getDrawable(R.drawable.greencircle);
                objects[5] = cont.getResources().getDrawable(R.drawable.redx);
                break;
            case("invisible"):
                objects[0] = cont.getResources().getDrawable(R.drawable.nothing);
                objects[1] = cont.getResources().getDrawable(R.drawable.nothing);
                objects[2] = cont.getResources().getDrawable(R.drawable.nothing);
                objects[3] = cont.getResources().getDrawable(R.drawable.nothing);
                objects[4] = cont.getResources().getDrawable(R.drawable.nothing);
                objects[5] = cont.getResources().getDrawable(R.drawable.nothing);
                break;
            case("cards"):
                objects[0] = cont.getResources().getDrawable(R.drawable.spade);
                objects[1] = cont.getResources().getDrawable(R.drawable.diamond);
                objects[2] = cont.getResources().getDrawable(R.drawable.heart);
                objects[3] = cont.getResources().getDrawable(R.drawable.club);
                objects[4] = cont.getResources().getDrawable(R.drawable.ace);
                objects[5] = cont.getResources().getDrawable(R.drawable.joker);
                break;
            case("simon"):
                objects[0] = cont.getResources().getDrawable(R.drawable.simongreen);
                objects[1] = cont.getResources().getDrawable(R.drawable.simonyellow);
                objects[2] = cont.getResources().getDrawable(R.drawable.simonblue);
                objects[3] = cont.getResources().getDrawable(R.drawable.simonred);
                objects[4] = cont.getResources().getDrawable(R.drawable.simongreen);
                objects[5] = cont.getResources().getDrawable(R.drawable.simonred);
                break;
        }

    }

    public static void SetSounds(String skin){
        switch (skin){
            case("classic"):
                MainActivity.correct_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.correct, 1);
                MainActivity.incorrect_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.incorrect, 1);
                break;
            case("invert"):
                MainActivity.correct_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.incorrect, 1);
                MainActivity.incorrect_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.correct, 1);
                break;
            case("pc"):
                MainActivity.correct_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.windows_correct, 1);
                MainActivity.incorrect_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.windows_incorrect, 1);
                break;
            case("xbox"):
                MainActivity.correct_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.xbox_correct, 1);
                MainActivity.incorrect_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.xbox_incorrect, 1);
                break;
            case("playstation"):
                MainActivity.correct_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.playstation_correct, 1);
                MainActivity.incorrect_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.xbox_incorrect, 1);
                break;
            case("ddr"):

                break;
            case("invisible"):
                MainActivity.correct_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.correct, 1);
                MainActivity.incorrect_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.incorrect, 1);
                break;
            case("simon"):
                MainActivity.correct_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.correct, 1);
                MainActivity.incorrect_sound = MainActivity.sounds.load(MainActivity.cont, R.raw.incorrect, 1);
                break;
        }
    }

    public static void SetSkin(ImageView up, ImageView left, ImageView down, ImageView right){
        up.setBackground(objects[0]);
        left.setBackground(objects[1]);
        down.setBackground(objects[2]);
        right.setBackground(objects[3]);
    }

    public static Drawable GetUp(){
        return objects[0];
    }

    public static Drawable GetLeft(){
        return objects[1];
    }

    public static Drawable GetDown(){
        return objects[2];
    }

    public static Drawable GetRight(){
        return objects[3];
    }

    public static Drawable GetCorrect(){
        return objects[4];
    }

    public static Drawable GetIncorrect(){
        return objects[5];
    }


}
