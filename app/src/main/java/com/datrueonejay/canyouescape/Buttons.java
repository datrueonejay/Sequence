package com.datrueonejay.canyouescape;


import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Buttons {

    private static int maxLevel = 2;

    public static void CreateButton(final int button_counter) {
        final Drawable yup = MainActivity.skin.GetCorrect();
        final Drawable nope = MainActivity.skin.GetIncorrect();


//        final Drawable yup = MainActivity.cont.getResources().getDrawable(R.drawable.green);
//        final Drawable nope = MainActivity.cont.getResources().getDrawable(R.drawable.red);
        //finds the max dimensions the picture can be to avoid overlap
        final int length = MainMenu.screen_height;
        // set the listener when a button is pressed and held
        MainActivity.moves[button_counter - 1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // sets the size of the indicator box
                String size = MainMenu.sp.getString("size", "medium");

                // find the size of the background
                switch (size){
                    case "small":
                        MainActivity.rightOrWrong.getLayoutParams().height = length/8;
                        MainActivity.rightOrWrong.getLayoutParams().width = length/8;
                        MainActivity.rightOrWrong.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        break;
                    case "medium":
                        MainActivity.rightOrWrong.getLayoutParams().height = length/6;
                        MainActivity.rightOrWrong.getLayoutParams().width = length/6;
                        MainActivity.rightOrWrong.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                        break;
                    case "large":
                        MainActivity.rightOrWrong.getLayoutParams().height = length/4;
                        MainActivity.rightOrWrong.getLayoutParams().width = length/4;
                        MainActivity.rightOrWrong.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                        break;
                }

                //updateButton(MainActivity.rightOrWrong);
                // sets right or wrong when a button is pressed
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // tries to input the move
                    MainActivity.current_sequence.input_move(button_counter);
                    // tries to check if the move is correct or wrong
                    boolean correct = MainActivity.current_sequence.check_move();
                    // sets the indicator as visible
                    MainActivity.rightOrWrong.setVisibility(View.VISIBLE);
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
                            // checks if the new score is higher than the old high score and not the
                            // beat the clock game mode
                            if (MainActivity.level_number > score && !MainMenu.timed_up_game) {
                                // save the new highscore
                                SharedPreferences.Editor editor = MainMenu.sp.edit();
                                editor.putInt(MainMenu.game_mode, MainActivity.level_number);
                                editor.commit();
                                // set the new highscore
                                long highScore = MainMenu.sp.getInt(MainMenu.game_mode, 0);
                                MainActivity.highscore.setText("High Score: " + Long.toString(highScore));
                            }
                            checkMaxLevel(MainActivity.level_number);

                            // increase the level
                            MainActivity.level_number++;

                        } else if (!MainActivity.current_sequence.check_sequence()) {
                            // create the text for the move counter
                            MainActivity.move_counter.setText("Move  " + Integer.toString(MainActivity.current_sequence.move_counter() + 1));
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
                        MainActivity.move_counter.setText("Move  " + Integer.toString(MainActivity.current_sequence.move_counter() + 1));
                    }
                }

                // sets when button is released
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    // sets box back to invisible
                    MainActivity.rightOrWrong.setVisibility(View.INVISIBLE);
                    // re enables all the buttons if sequence is not finished yet
                    if (!MainActivity.current_sequence.check_sequence()){
                       EnableButtons();
                    }
                    else{
                        DisableButtons();
                        // enables the next level button
                        MainActivity.next_level.setEnabled(true);
                    }
                }
                return false;
            }
        });
    }

    public static void setMaxLevel(int level){
        maxLevel = level;
    }

    private static void checkMaxLevel(int level) {
        if (level == maxLevel) {
            MainActivity.upTimer.cancel();
            MainActivity.next_level.setEnabled(false);
            MainActivity.next_level.setVisibility(View.GONE);
            MainActivity.upTimer.cancel();
            MainActivity.time.setVisibility(View.VISIBLE);
            MainActivity.move_counter.setText("FINISHED");
            MainActivity.time.setText("You took " + Integer.toString(MainActivity.curr_time) + " seconds.");
            if (MainActivity.curr_time < MainMenu.sp.getInt(MainMenu.game_mode, 1000)){
                MainMenu.editor.putInt(MainMenu.game_mode, MainActivity.curr_time);
                MainMenu.editor.commit();
            }
        }
    }

    // method to disable buttons
    public static void DisableButtons(){
        for (int a_counter = 0; a_counter < 4; a_counter++) {
            MainActivity.moves[a_counter].setEnabled(false);
            MainActivity.next_level.setEnabled(false);
        }
    }

    public static void EnableButtons(){
        for (int a_counter = 0; a_counter < 4; a_counter++) {
            MainActivity.moves[a_counter].setEnabled(true);
        }
    }

}
