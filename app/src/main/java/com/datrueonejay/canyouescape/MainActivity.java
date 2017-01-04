package com.datrueonejay.canyouescape;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    /**
     * use 1 as up
     * 2 as left
     * 3 as down
     * 4 as right
     */

    ImageButton move_up;
    ImageButton move_left;
    ImageButton move_down;
    ImageButton move_right;
    //ImageView a;
    //ImageView b;
    //ImageView c;
    //ImageView d;
    ImageView rightOrWrong;
    //Button reset;
    Button next_level;
    TextView level;
    //TextView sequence;
    TextView move_counter;
    LevelSequence current_sequence;
    int level_number = 1;
    // timer to flash green if correct
    Timer timer;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // timer to time between green flash and reset
        timer = new Timer();
        // create the buttons upon opening the app
        move_up = (ImageButton) findViewById(R.id.upButton);
        move_left = (ImageButton) findViewById(R.id.leftButton);
        move_down = (ImageButton) findViewById(R.id.downButton);
        move_right = (ImageButton) findViewById(R.id.rightButton);
        final ImageButton [] moves = {move_up, move_left, move_down, move_right};

        // create the next level button
        next_level = (Button) findViewById(R.id.nextLevel);
        // disables the next level button at first
        next_level.setEnabled(false);

        // create the level 1 sequence
        current_sequence = new LevelSequence(level_number);

        // create the text for the move counter
        move_counter = (TextView) findViewById(R.id.moveCounter);
        move_counter.setText("Move " + Integer.toString(current_sequence.move_counter() + 1));

        // shows answer
        //sequence = (TextView) findViewById(R.id.sequence);
        //sequence.setText(Arrays.toString(current_sequence.level_sequence()));

        // create the rights and wrongs
        //a = (ImageView) findViewById(R.id.a);
        //b = (ImageView) findViewById(R.id.b);
        //c = (ImageView) findViewById(R.id.c);
        //d = (ImageView) findViewById(R.id.d);
        //final ImageView [] tries = {a, b, c, d};
        rightOrWrong = (ImageView) findViewById(R.id.rightOrWrong);

        // create level text
        level = (TextView) findViewById(R.id.level);
        level.setText(("Level " + Integer.toString(level_number)));

        // create the four buttons
        for (int button_counter = 1; button_counter < 5; button_counter++){
            // creates a copy of the button_counter
            final int copy_counter = button_counter;
            // creates the button for current direction
            moves[button_counter - 1].setOnClickListener(new View.OnClickListener(){
                @Override
                // create the listener
                public void onClick(View view){
                    // tries to input the move
                    current_sequence.input_move(copy_counter);
                    try{
                        // tries to check if the move is correct or wrong
                        boolean correct = current_sequence.check_move();
                        if (correct && current_sequence.can_move()) {
                            for (int a_counter = 0; a_counter < 4; a_counter ++){
                                   moves[a_counter].setEnabled(false);
                            }
                            // sets to green
                            rightOrWrong.setImageResource(R.drawable.green);
                            // waits 0.01 seconds
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // sets it back to white
                                    rightOrWrong.setImageResource(R.drawable.white);
                                    // re enables all the buttons
                                    for (int b_counter = 0; b_counter < 4; b_counter ++)
                                        moves[b_counter].setEnabled(true);
                                }
                                }, 100);
                            // increases the move
                            current_sequence.increase_move();
                            // checks if the user sequence matches the level sequence
                            if (current_sequence.check_sequence()){
                                // create the text for the move counter
                                move_counter.setText("PROCEED");
                                // sets the next level text as visible
                                next_level.setTextColor(Color.BLACK);
                                // sets the background of the button to grey
                                next_level.setBackgroundColor(Color.LTGRAY);
                                // disables the arrow buttons
                                for (int d_counter = 0; d_counter < 4; d_counter ++){
                                        moves[d_counter].setEnabled(false);
                                // enables the next level button
                                    next_level.setEnabled(true);
                                }
                            }
                            else if (!current_sequence.check_sequence()){
                                // create the text for the move counter
                                move_counter.setText("Move " + Integer.toString(current_sequence.move_counter() + 1));
                            }

                        }
                        else if (!correct && current_sequence.can_move()){
                            for (int c_counter = 0; c_counter < 4; c_counter ++) {
                                    moves[c_counter].setEnabled(false);
                            }
                                rightOrWrong.setImageResource(R.drawable.red);
                                current_sequence.incorrect();
                                // resets the square to white
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // sets it back to white
                                        rightOrWrong.setImageResource(R.drawable.white);
                                        // re enables all the arrow buttons
                                        for (int d_counter = 0; d_counter < 4; d_counter++)
                                            moves[d_counter].setEnabled(true);
                                    }
                                }, 100);
                            // resets the users inputs
                            current_sequence.reset();
                            // create the text for the move counter
                            move_counter.setText("Move " + Integer.toString(current_sequence.move_counter() + 1));
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e) {
                    }

                }
            });
        }

        // create the next level button
        next_level.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // ensures that the users sequence matches the level sequence
                if (current_sequence.check_sequence()){
                    // set the next_level text and background as white again
                    next_level.setTextColor(Color.WHITE);
                    next_level.setBackgroundColor(Color.WHITE);
                    // increase the level
                    level_number++;
                    // set the new level text
                    level.setText(("Level " + Integer.toString(level_number)));
                    // create a new level sequence
                    current_sequence = new LevelSequence(level_number);
                    // create the text for the move counter
                    move_counter = (TextView) findViewById(R.id.moveCounter);
                    move_counter.setText("Move " + Integer.toString(current_sequence.move_counter() + 1));
                    // shows the answer
                    //sequence = (TextView) findViewById(R.id.sequence);
                    //sequence.setText(Arrays.toString(current_sequence.level_sequence()));
                    // disables the next level button for new level
                    next_level.setEnabled(false);
                }

            }
        });


    }
}

class LevelSequence{
    private int[] level_sequence;
    private int[] user_sequence;
    private int current_move;
    private boolean can_move;

    LevelSequence(int level_num) {
        // variable to hold level_num
        int lvl_num = level_num;
        // variable to hold the sequence of correct moves
        level_sequence = new int[lvl_num];
        // variable to hold the users guesses at the sequence
        user_sequence = new int[lvl_num];
        // variable to hold the current move number
        current_move = 0;
        // assigns can_move as true
        can_move = true;

        // loops through each move in the array, creating a new value for the current number
        for (int move_counter = 0; move_counter < level_sequence.length; move_counter ++) {
            // create a random number between 1-4
            Random random_number = new Random();
            int move = random_number.nextInt(100) % 4;
            //int move = (int) (Math.random()*(3) + 1);
            // assigns the move number to the sequence of moves
            level_sequence[move_counter] = move + 1;
        }
    }

    void incorrect() {
        // a method to signal an incorrect move, allowing no more moves
        can_move = false;
    }

    private void does_not_exceed_length() {
        // method to ensure that a new move will not exceed the length of the current sequence
        if (current_move >= level_sequence.length)
            can_move = false;
    }

    void input_move(int move) {
        /** (int) -> bool
         * REQ: move must be either 0, 1, 2, or 3
         * Given an integer representing a move, if the integers matches the current integer of the
         * move sequence, the method returns True. Other wise False.
         */
        // ensures that the move does not exceed the length of the level sequence
        does_not_exceed_length();
        if (can_move)
            user_sequence[current_move] = move;
    }

    void increase_move() {
        // method used to move to the next move
        current_move++;
        // ensures a move can still be made
        does_not_exceed_length();
    }

    int move_counter() {
        // method to return the integer of the move in the sequence
        return current_move;
    }

    boolean check_move() {
        //checks if the users move matches the sequence
        boolean matches = false;
        if (user_sequence[current_move] == level_sequence[current_move])
            matches = true;
        return matches;
    }

    boolean check_sequence() {
        // checks if all the users moves matches the level sequence
        boolean matches = false;
        if (Arrays.equals(user_sequence, level_sequence))
            matches = true;
        return matches;
    }

    boolean can_move() {
        // a method to check to ensure a move can be made
        return can_move;
    }

    int [] level_sequence(){
        return level_sequence;
    }

    void reset() {
        /** (Null) -> Null
         * This method resets the users moves.
         */
        // resets the users move
        current_move = 0;
        // ensures that moves can be made again
        can_move = true;
    }


}
