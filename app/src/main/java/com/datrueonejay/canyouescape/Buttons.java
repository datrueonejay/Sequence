package com.datrueonejay.canyouescape;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Buttons {

    public static void create_button(final int button_counter) {
        final int direction = button_counter - 1;
        // finds the key set
        String key_set = MainMenu.sp.getString("skin", "classic");
        // set the picture of the buttons
        setPic("pc", direction);
        // finds the pics for right and wrong
        DoubleDrawable pics = FindRightPics("pc");
        // finds drawables for correct and incorrect
        final Drawable yup = pics.GetFirst();
        final Drawable nope = pics.GetSecond();

        // set the listener when a button is pressed and held
        MainActivity.moves[button_counter - 1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // sets the size of the indicator box
                String size = MainMenu.sp.getString("size", "medium");
                // find the size of the background
                switch (size){
                    case "small":
                        MainActivity.rightOrWrong.setMaxHeight(1);
                        MainActivity.rightOrWrong.setMaxWidth(1);
                        break;
                    case "medium":
                        MainActivity.rightOrWrong.setMaxHeight(150);
                        MainActivity.rightOrWrong.setMaxWidth(150);
                        break;
                    case "large":
                        MainActivity.rightOrWrong.setMaxHeight(200);
                        MainActivity.rightOrWrong.setMaxWidth(200);
                        break;
                }

                // sets right or wrong when a button is pressed
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // tries to input the move
                    MainActivity.current_sequence.input_move(button_counter);
                    // tries to check if the move is correct or wrong
                    boolean correct = MainActivity.current_sequence.check_move();
                    // disables all the other buttons
                    for (int a_counter = 0; a_counter < 4; a_counter++) {
                        if (a_counter != (button_counter - 1))
                            MainActivity.moves[a_counter].setEnabled(false);
                    }
                    // disables next level button if it shows
                    MainActivity.next_level.setEnabled(false);
                    if (correct){
                        // checks if the sound should be played
                        if (MainMenu.sounds_on) {
                            // creates a new thread to play the sound
                            MainActivity.sounds.play(MainActivity.correct_sound, 1, 1, 0, 0, 1);
                        }
                        // sets the background as green
                        MainActivity.rightOrWrong.setBackground(yup);
                        // increases the move
                        MainActivity.current_sequence.increase_move();
                        // checks if the user sequence matches the level sequence
                        if (MainActivity.current_sequence.check_sequence()) {
                            // create the text for the move counter
                            MainActivity.move_counter.setText("PROCEED");
                            // sets the next level button as visible
                            MainActivity.next_level.setVisibility(View.VISIBLE);
                            // disables the other arrow buttons
                            for (int a_counter = 0; a_counter < 4; a_counter++) {
                                if (a_counter != (button_counter - 1))
                                    MainActivity.moves[a_counter].setEnabled(false);
                            }
                            // disables next level button if it shows
                            MainActivity.next_level.setEnabled(false);

                            // finds the current high score
                            long score = MainMenu.sp.getInt(MainMenu.game_mode, 0);
                            // checks if the new score is higher than the old high score
                            if (MainActivity.level_number > score) {
                                // save the new highscore
                                SharedPreferences.Editor editor = MainMenu.sp.edit();
                                editor.putInt(MainMenu.game_mode, MainActivity.level_number);
                                editor.commit();
                                // set the new highscore
                                long highScore = MainMenu.sp.getInt(MainMenu.game_mode, 0);
                                MainActivity.highscore.setText("High Score: " + Long.toString(highScore));
                            }
                        } else if (!MainActivity.current_sequence.check_sequence()) {
                            // create the text for the move counter
                            MainActivity.move_counter.setText("Move " + Integer.toString(MainActivity.current_sequence.move_counter() + 1));
                        }
                    }
                    else {
                        // checks if sound should be played
                        if (MainMenu.sounds_on) {
                            // create a new thread to play the incorrect sound
                            MainActivity.sounds.play(MainActivity.incorrect_sound, 1, 1, 0, 0, 1);
                        }
                        // set red
                        MainActivity.rightOrWrong.setBackground(nope);
                        // resets the users inputs
                        MainActivity.current_sequence.reset();
                        // create the text for the move counter
                        MainActivity.move_counter.setText("Move " + Integer.toString(MainActivity.current_sequence.move_counter() + 1));
                    }
                }

                // sets when button is released
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    // sets box back to white
                    MainActivity.rightOrWrong.setBackgroundColor(MainMenu.context.getResources().getColor(R.color.white));
                    // re enables all the buttons
                    for (int b_counter = 0; b_counter < 4; b_counter++)
                        MainActivity.moves[b_counter].setEnabled(true);
                    // enables the next level button
                    MainActivity.next_level.setEnabled(true);
                }


                return false;
            }
        });


        /**
        // creates the button for current direction
        MainActivity.moves[button_counter - 1].setOnClickListener(new View.OnClickListener() {
            @Override
            // create the listener
            public void onClick(View view) {
                // sets the size of the indicator box
                String size = MainMenu.sp.getString("size", "medium");
                // find the size of the background
                switch (size){
                    case "small":
                        MainActivity.rightOrWrong.setMaxHeight(1);
                        MainActivity.rightOrWrong.setMaxWidth(1);
                        break;
                    case "medium":
                        MainActivity.rightOrWrong.setMaxHeight(150);
                        MainActivity.rightOrWrong.setMaxWidth(150);
                        break;
                    case "large":
                        MainActivity.rightOrWrong.setMaxHeight(200);
                        MainActivity.rightOrWrong.setMaxWidth(200);
                        break;
                }
                // tries to input the move
                MainActivity.current_sequence.input_move(button_counter);
                try {
                    // tries to check if the move is correct or wrong
                    boolean correct = MainActivity.current_sequence.check_move();
                    // if correct
                    if (correct && MainActivity.current_sequence.can_move()) {
                        // disables the four buttons
                        DisableButtons();
                        // checks if the sound should be played
                        if (MainMenu.sounds_on) {
                            // creates a new thread to play the sound
                            MainActivity.sounds.play(MainActivity.correct_sound, 1, 1, 0, 0, 1);
                        }
                        // sets the background as green
                        MainActivity.rightOrWrong.setBackground(yup);
                        // waits 0.01 seconds
                        MainActivity.handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // sets box back to white
                                MainActivity.rightOrWrong.setBackgroundColor(MainMenu.context.getResources().getColor(R.color.white));
                                // re enables all the buttons
                                for (int b_counter = 0; b_counter < 4; b_counter++)
                                    MainActivity.moves[b_counter].setEnabled(true);
                            }
                        }, 100);
                        // increases the move
                        MainActivity.current_sequence.increase_move();
                        // checks if the user sequence matches the level sequence
                        if (MainActivity.current_sequence.check_sequence()) {
                            // create the text for the move counter
                            MainActivity.move_counter.setText("PROCEED");
                            // sets the next level button as visible
                            MainActivity.next_level.setVisibility(View.VISIBLE);
                            // disables the arrow buttons
                            DisableButtons();
                            // enables the next level button
                            MainActivity.next_level.setEnabled(true);
                            // finds the current high score
                            long score = MainMenu.sp.getInt(MainMenu.game_mode, 0);
                            // checks if the new score is higher than the old high score
                            if (MainActivity.level_number > score) {
                                // save the new highscore
                                SharedPreferences.Editor editor = MainMenu.sp.edit();
                                editor.putInt(MainMenu.game_mode, MainActivity.level_number);
                                editor.commit();
                                // set the new highscore
                                long highScore = MainMenu.sp.getInt(MainMenu.game_mode, 0);
                                MainActivity.highscore.setText("High Score: " + Long.toString(highScore));
                            }
                        } else if (!MainActivity.current_sequence.check_sequence()) {
                            // create the text for the move counter
                            MainActivity.move_counter.setText("Move " + Integer.toString(MainActivity.current_sequence.move_counter() + 1));
                        }

                    // if the move is incorrect
                    } else if (!correct && MainActivity.current_sequence.can_move()) {
                        for (int c_counter = 0; c_counter < 4; c_counter++) {
                            MainActivity.moves[c_counter].setEnabled(false);
                        }
                        if (MainMenu.sounds_on) {
                            // create a new thread to play the incorrect sound
                            MainActivity.sounds.play(MainActivity.incorrect_sound, 1, 1, 0, 0, 1);
                        }
                        // set red
                        MainActivity.rightOrWrong.setBackground(nope);

                        MainActivity.current_sequence.incorrect();
                        // resets the square to white
                        MainActivity.handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // sets it white
                                MainActivity.rightOrWrong.setBackgroundColor(MainMenu.context.getResources().getColor(R.color.white));
                                // re enables all the arrow buttons
                                for (int d_counter = 0; d_counter < 4; d_counter++)
                                    MainActivity.moves[d_counter].setEnabled(true);

                            }
                        }, 100);
                        // resets the users inputs
                        MainActivity.current_sequence.reset();
                        // create the text for the move counter
                        MainActivity.move_counter.setText("Move " + Integer.toString(MainActivity.current_sequence.move_counter() + 1));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }

            }
        });
         **/
    }


    // method to disable buttons
    public static void DisableButtons(){
        for (int a_counter = 0; a_counter < 4; a_counter++) {
            MainActivity.moves[a_counter].setEnabled(false);
            MainActivity.next_level.setEnabled(false);
        }
    }


    // method to set the pictures of the keys being used
    private static void setPic(String key_set, int direction){
        // sets classic keys
        if (key_set == "classic") {
            switch (direction) {
                case 0: MainActivity.moves[direction].setBackgroundResource(R.drawable.uparrow);
                        break;
                case 1: MainActivity.moves[direction].setBackgroundResource(R.drawable.leftarrow);
                        break;
                case 2: MainActivity.moves[direction].setBackgroundResource(R.drawable.downarrow);
                        break;
                case 3: MainActivity.moves[direction].setBackgroundResource(R.drawable.rightarrow);
            }
        }
        else if (key_set == "pc") {
            switch (direction) {
                case 0: MainActivity.moves[direction].setBackgroundResource(R.drawable.w);
                    break;
                case 1: MainActivity.moves[direction].setBackgroundResource(R.drawable.a);
                    break;
                case 2: MainActivity.moves[direction].setBackgroundResource(R.drawable.s);
                    break;
                case 3: MainActivity.moves[direction].setBackgroundResource(R.drawable.d);

            }
        }
        else if (key_set == "xbox") {
            switch (direction) {
                case 0: MainActivity.moves[direction].setBackgroundResource(R.drawable.xboxy);
                    break;
                case 1: MainActivity.moves[direction].setBackgroundResource(R.drawable.xboxx);
                    break;
                case 2: MainActivity.moves[direction].setBackgroundResource(R.drawable.xboxa);
                    break;
                case 3: MainActivity.moves[direction].setBackgroundResource(R.drawable.xboxb);

            }
        }
    }

    private static DoubleDrawable FindRightPics(String key_set){
        // create a DoubleDrawable to hold the right and wrong picture
        DoubleDrawable pics;
        pics = new DoubleDrawable(MainActivity.cont.getResources().getDrawable(R.drawable.green), MainActivity.cont.getResources().getDrawable(R.drawable.red));

        switch (key_set){
            case "classic":
                pics = new DoubleDrawable(MainActivity.cont.getResources().getDrawable(R.drawable.green), MainActivity.cont.getResources().getDrawable(R.drawable.red));
                break;
            case "pc":
                pics = new DoubleDrawable(MainActivity.cont.getResources().getDrawable(R.drawable.greencircle), MainActivity.cont.getResources().getDrawable(R.drawable.redx));
                break;
        }
        return pics;

    }
}
