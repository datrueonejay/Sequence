package com.datrueonejay.canyouescape;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Buttons {

    private static int maxLevel;

    public static void CreateButton(final int button_counter) {
        final Drawable correctPic = MainActivity.skin.GetCorrect();
        final Drawable incorrectPic = MainActivity.skin.GetIncorrect();


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
                        MainActivity.rightOrWrong.getLayoutParams().height = length/9;
                        MainActivity.rightOrWrong.getLayoutParams().width = length/9;
                        MainActivity.rightOrWrong.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        break;
                    case "medium":
                        MainActivity.rightOrWrong.getLayoutParams().height = length/7;
                        MainActivity.rightOrWrong.getLayoutParams().width = length/7;
                        MainActivity.rightOrWrong.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        break;
                    case "large":
                        MainActivity.rightOrWrong.getLayoutParams().height = length/5;
                        MainActivity.rightOrWrong.getLayoutParams().width = length/5;
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
                            MainActivity.rightOrWrong.setBackground(correctPic);
                        }
                        // increases the move
                        MainActivity.currentSequence.increase_move();
                        // checks if the user sequence matches the level sequence
                        if (MainActivity.currentSequence.check_sequence()) {
                            // create the text for the move counter
                            MainActivity.moveCounter.setText("PROCEED");
                            // sets the next level button as visible
                            MainActivity.nextLevelText.setVisibility(View.VISIBLE);
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
                            if (MainActivity.levelNumber > score && !MainMenu.beatTheClockMode) {
                                // save the new highscore
                                SharedPreferences.Editor editor = MainMenu.sp.edit();
                                editor.putInt(MainMenu.gameMode, MainActivity.levelNumber);
                                editor.commit();
                                // set the new highscore
                                long highScore = MainMenu.sp.getInt(MainMenu.gameMode, 0);
                                MainActivity.highscore.setText("Highest Level: " + Long.toString(highScore));
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
                        // set incorrect
                        MainActivity.rightOrWrong.setBackground(incorrectPic);
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
        // stops game if max level is reached
        if (level == maxLevel) {
            MainActivity.upTimer.cancel();
            // set button to return to menu
            MainActivity.nextLevelText.setText(R.string.return_menu);
            MainActivity.nextLevel.getLayoutParams().width = 2*MainMenu.screenWidth/3;
            MainActivity.nextLevelText.getLayoutParams().width = 2*MainMenu.screenWidth/3;
            MainActivity.nextLevelText.setPadding(MainMenu.screenWidth/36, MainMenu.screenHeight/60, MainMenu.screenWidth/36, MainMenu.screenHeight/60);
            MainActivity.nextLevel.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
                @Override
                public boolean onTouch(View view, MotionEvent event){
                    if (event.getAction() == MotionEvent.ACTION_UP){
                        // dialog to return to main menu
                        final Dialog dialog = new Dialog(MainActivity.cont);
                        dialog.setContentView(R.layout.back_window);
                        TextView title = (TextView) dialog.findViewById(R.id.title);
                        title.getLayoutParams().height = MainMenu.screenHeight/25;
                        title.setPadding(0, MainMenu.screenWidth/81, 0, MainMenu.screenWidth/81);
                        RelativeLayout.LayoutParams titleParams = (RelativeLayout.LayoutParams) title.getLayoutParams();
                        titleParams.setMargins(0, 0, 0, MainMenu.screenWidth/27);
                        title.setText(R.string.back_title);
                        TextView confirm = (TextView) dialog.findViewById(R.id.confirmation);
                        confirm.getLayoutParams().height = MainMenu.screenHeight/20;
                        RelativeLayout.LayoutParams confirmParams = (RelativeLayout.LayoutParams) confirm.getLayoutParams();
                        confirmParams.setMargins(0, 0, 0, MainMenu.screenWidth/27);
                        confirm.setText(MainActivity.cont.getString(R.string.confirm));
                        // create the yes button
                        Button yes = (Button) dialog.findViewById(R.id.yes);
                        yes.getLayoutParams().height = MainMenu.screenHeight/15;
                        RelativeLayout.LayoutParams yesParams = (RelativeLayout.LayoutParams) yes.getLayoutParams();
                        yesParams.setMargins(MainMenu.screenWidth/22, 0, MainMenu.screenWidth/22, MainMenu.screenWidth/27);
                        TextView yesText = (TextView) dialog.findViewById(R.id.yes_text);
                        yesText.getLayoutParams().height = MainMenu.screenHeight/15;
                        yesText.setPadding(0, MainMenu.screenHeight/45, 0, MainMenu.screenHeight/45);
                        RelativeLayout.LayoutParams yesTextParams = (RelativeLayout.LayoutParams) yesText.getLayoutParams();
                        yesTextParams.setMargins(MainMenu.screenWidth/22, 0, MainMenu.screenWidth/22, MainMenu.screenWidth/27);
                        yesText.setText(MainActivity.cont.getString(R.string.yes));
                        yes.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == MotionEvent.ACTION_UP) {
                                    dialog.dismiss();
                                    Activity act = (Activity) MainActivity.cont;
                                    act.finish();
                                    MainActivity.levelNumber = 1;
                                    if (MainMenu.timeAttackMode) {
                                        MainActivity.downTimer.cancel();
                                    }
                                    if (MainMenu.beatTheClockMode) {
                                        MainActivity.upTimer.cancel();
                                        MainActivity.upTimer = null;
                                    }
                                }
                                super.onTouch(v, event);
                                return false;
                            }

                        });
                        // create the no button
                        Button no = (Button) dialog.findViewById(R.id.no);
                        no.getLayoutParams().height = MainMenu.screenHeight/15;
                        RelativeLayout.LayoutParams noParams = (RelativeLayout.LayoutParams) no.getLayoutParams();
                        noParams.setMargins(MainMenu.screenWidth/22, 0, MainMenu.screenWidth/22, MainMenu.screenWidth/27);
                        TextView noText = (TextView) dialog.findViewById(R.id.no_text);
                        noText.getLayoutParams().height = MainMenu.screenHeight/15;
                        noText.setPadding(0, MainMenu.screenHeight/45, 0, MainMenu.screenHeight/45);
                        RelativeLayout.LayoutParams noTextParams = (RelativeLayout.LayoutParams) noText.getLayoutParams();
                        noTextParams.setMargins(MainMenu.screenWidth/22, 0, MainMenu.screenWidth/22, MainMenu.screenWidth/27);
                        noText.setText(MainActivity.cont.getString(R.string.no));
                        no.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == MotionEvent.ACTION_UP){
                                    dialog.dismiss();
                                }
                                super.onTouch(v,event);
                                return false;
                            }
                        });
                        dialog.show();
                    }
                    super.onTouch(view, event);
                    return false;
                }
            });
            MainActivity.time.setVisibility(View.VISIBLE);
            MainActivity.moveCounter.setText("FINISHED");
            MainActivity.time.setText("You took " + Integer.toString(MainActivity.currTime) + " seconds");
            // if shorted time was achieved
            if (MainActivity.currTime < MainMenu.sp.getInt(MainMenu.gameMode, 999)){
                MainMenu.editor.putInt(MainMenu.gameMode, MainActivity.currTime);
                MainMenu.editor.commit();
                // set the new highscore
                long highScore = MainMenu.sp.getInt(MainMenu.gameMode, 0);
                MainActivity.highscore.setText("Best Time: " + Long.toString(highScore) + " seconds");
            }
        }
    }

    // method to disable move and next level buttons
    public static void DisableButtons(){
        for (int a_counter = 0; a_counter < 4; a_counter++) {
            MainActivity.moves[a_counter].setEnabled(false);
            MainActivity.nextLevel.setEnabled(false);
        }
    }

    // enables all move buttons
    public static void EnableButtons(){
        for (int a_counter = 0; a_counter < 4; a_counter++) {
            MainActivity.moves[a_counter].setEnabled(true);
        }
    }

}
