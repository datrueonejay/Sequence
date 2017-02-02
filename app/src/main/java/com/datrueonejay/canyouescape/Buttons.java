package com.datrueonejay.canyouescape;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Buttons {

    public static void create_button(int button_counter) {
        final int copy_counter = button_counter;
        // creates the button for current direction
        MainActivity.moves[button_counter - 1].setOnClickListener(new View.OnClickListener() {
            @Override
            // create the listener
            public void onClick(View view) {
                // tries to input the move
                MainActivity.current_sequence.input_move(copy_counter);
                try {
                    // tries to check if the move is correct or wrong
                    boolean correct = MainActivity.current_sequence.check_move();
                    // if correct
                    if (correct && MainActivity.current_sequence.can_move()) {
                        // disables the four buttons
                        for (int a_counter = 0; a_counter < 4; a_counter++) {
                            MainActivity.moves[a_counter].setEnabled(false);
                        }

                        // checks if the sound should be played
                        if (MainMenu.sounds_on) {
                            // creates a new thread to play the sound
                            MainActivity.sounds.play(MainActivity.correct_sound, 1, 1, 0, 0, 1);
                        }

                        // checks if it should use the background as the indicator or the box
                        if (MainMenu.indicator_box){
                            // ensures the box is visible
                            MainActivity.rightOrWrong.setVisibility(View.VISIBLE);
                            // sets box to green
                            MainActivity.rightOrWrong.setImageResource(R.drawable.green);
                        }
                        else if (!MainMenu.indicator_box){
                            // ensures the box is not visible
                            MainActivity.rightOrWrong.setVisibility(View.GONE);
                            // sets screen to green
                            MainActivity.layout.setBackgroundResource(R.drawable.green);
                        }

                        // waits 0.01 seconds
                        MainActivity.handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // sets box back to white
                                if (MainMenu.indicator_box) {
                                    MainActivity.rightOrWrong.setImageResource(R.drawable.white);
                                }
                                // sets background to white
                                else if (!MainMenu.indicator_box) {
                                    MainActivity.layout.setBackgroundResource(R.drawable.white);
                                }
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
                            for (int d_counter = 0; d_counter < 4; d_counter++) {
                                MainActivity.moves[d_counter].setEnabled(false);
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
                        // sets box to red
                        if (MainMenu.indicator_box) {
                            // ensures the box is visible
                            MainActivity.rightOrWrong.setVisibility(View.VISIBLE);
                            MainActivity.rightOrWrong.setImageResource(R.drawable.red);
                        }
                        // sets background to red
                        else if (!MainMenu.indicator_box) {
                            // ensures the box is not visible
                            MainActivity.rightOrWrong.setVisibility(View.GONE);
                            MainActivity.layout.setBackgroundResource(R.drawable.red);
                        }
                        MainActivity.current_sequence.incorrect();
                        // resets the square to white
                        MainActivity.handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // sets box back to white
                                if (MainMenu.indicator_box) {
                                    MainActivity.rightOrWrong.setImageResource(R.drawable.white);
                                }
                                else {
                                    MainActivity.layout.setBackgroundResource(R.drawable.white);
                                }
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
    }

    public static void DisableButtons(){
        for (int a_counter = 0; a_counter < 4; a_counter++) {
            MainActivity.moves[a_counter].setEnabled(false);
            MainActivity.next_level.setEnabled(false);
        }
    }
}
