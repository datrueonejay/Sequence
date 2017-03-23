package com.datrueonejay.canyouescape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Buttons {

    private static int maxLevel;

    public static void CreateButton(final int button_counter) {
        final Drawable yup = MainActivity.skin.GetCorrect();
        final Drawable nope = MainActivity.skin.GetIncorrect();


        //finds the max dimensions the picture can be to avoid overlap
        final int length = MainMenu.screenHeight;
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

                // sets right or wrong when a button is pressed
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    // tries to input the move
                    MainActivity.currentSequence.input_move(button_counter);
                    // tries to check if the move is correct or wrong
                    boolean correct = MainActivity.currentSequence.check_move();
                    // sets the indicator as visible
                    if (!MainActivity.currentSequence.check_sequence()){
                        MainActivity.rightOrWrong.setVisibility(View.VISIBLE);
                    }
                    // disables all the other buttons
                    for (int a_counter = 0; a_counter < 4; a_counter++) {
                        if (a_counter != (button_counter - 1))
                            MainActivity.moves[a_counter].setEnabled(false);
                    }
                    // disables next level button if it shows
                    MainActivity.nextLevel.setEnabled(false);
                    if (correct){
                        // checks if the sound should be played
                        if (MainMenu.soundsOn) {
                            // creates a new thread to play the sound
                            MainActivity.sounds.play(MainActivity.correctSound, 1, 1, 0, 0, 1);
                        }
                        // sets the background as green
                        if (!MainActivity.currentSequence.check_sequence()){
                            MainActivity.rightOrWrong.setBackground(yup);
                        }
                        // increases the move
                        MainActivity.currentSequence.increase_move();

                        // checks if the user sequence matches the level sequence
                        if (MainActivity.currentSequence.check_sequence()) {
                            // create the text for the move counter
                            MainActivity.moveCounter.setText("PROCEED");
                            // sets the next level button as visible
                            MainActivity.nextLevel.setVisibility(View.VISIBLE);
                            // disables the other arrow buttons
                            for (int a_counter = 0; a_counter < 4; a_counter++) {
                                if (a_counter != (button_counter - 1))
                                    MainActivity.moves[a_counter].setEnabled(false);
                            }
                            // disables next level button if it shows
                            MainActivity.nextLevel.setEnabled(false);

                            // finds the current high score
                            long score = MainMenu.sp.getInt(MainMenu.gameMode, 0);
                            // checks if the new score is higher than the old high score and not the
                            // beat the clock game mode
                            if (MainActivity.levelNumber > score && !MainMenu.timedUpGame) {
                                // save the new highscore
                                SharedPreferences.Editor editor = MainMenu.sp.edit();
                                editor.putInt(MainMenu.gameMode, MainActivity.levelNumber);
                                editor.commit();
                                // set the new highscore
                                long highScore = MainMenu.sp.getInt(MainMenu.gameMode, 0);
                                if (MainMenu.timedGame){
                                    MainActivity.highscore.setText("Highest Level: " + Long.toString(highScore));
                                }
                                else {
                                    MainActivity.highscore.setText("Highscore: " + Long.toString(highScore));
                                }
                            }
                            checkMaxLevel(MainActivity.levelNumber);

                            // increase the level
                            MainActivity.levelNumber++;

                        } else if (!MainActivity.currentSequence.check_sequence()) {
                            // create the text for the move counter
                            MainActivity.moveCounter.setText("Move  " + Integer.toString(MainActivity.currentSequence.moveCounter() + 1));
                        }
                    }
                    else {
                        // checks if sound should be played
                        if (MainMenu.soundsOn) {
                            // create a new thread to play the incorrect sound
                            MainActivity.sounds.play(MainActivity.incorrectSound, 1, 1, 0, 0, 1);
                        }
                        // set red
                        MainActivity.rightOrWrong.setBackground(nope);
                        // resets the users inputs
                        MainActivity.currentSequence.reset();
                        // create the text for the move counter
                        MainActivity.moveCounter.setText("Move  " + Integer.toString(MainActivity.currentSequence.moveCounter() + 1));
                    }
                }

                // sets when button is released
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    // sets box back to invisible
                    MainActivity.rightOrWrong.setVisibility(View.INVISIBLE);
                    // re enables all the buttons if sequence is not finished yet
                    if (!MainActivity.currentSequence.check_sequence()){
                       EnableButtons();
                    }
                    else{
                        DisableButtons();
                        // enables the next level button
                        MainActivity.nextLevel.setEnabled(true);
                    }
                }

                // if dev mode show the correct move
                if (MainMenu.sp.getBoolean("dev", false) && MainActivity.currentSequence.does_not_exceed_length()){
                    MainActivity.mid.setText(Integer.toString(MainActivity.currentSequence.getMove()));
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
            MainActivity.nextLevel.setEnabled(false);
            MainActivity.nextLevel.setVisibility(View.GONE);
            MainActivity.upTimer.cancel();
            MainActivity.time.setVisibility(View.VISIBLE);
            MainActivity.moveCounter.setText("FINISHED");
            MainActivity.time.setText("You took " + Integer.toString(MainActivity.currTime) + " seconds");
            if (MainActivity.currTime < MainMenu.sp.getInt(MainMenu.gameMode, 9999)){
                MainMenu.editor.putInt(MainMenu.gameMode, MainActivity.currTime);
                MainMenu.editor.commit();
                // set the new highscore
                long highScore = MainMenu.sp.getInt(MainMenu.gameMode, 0);
                MainActivity.highscore.setText("Best Time: " + Long.toString(highScore) + " seconds");
            }
        }
    }

    // method to disable buttons
    public static void DisableButtons(){
        for (int a_counter = 0; a_counter < 4; a_counter++) {
            MainActivity.moves[a_counter].setEnabled(false);
            MainActivity.nextLevel.setEnabled(false);
        }
    }

    public static void EnableButtons(){
        for (int a_counter = 0; a_counter < 4; a_counter++) {
            MainActivity.moves[a_counter].setEnabled(true);
        }
    }

}
