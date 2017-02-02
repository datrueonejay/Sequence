package com.datrueonejay.canyouescape;


import java.util.Arrays;
import java.util.Random;

public class LevelSequence{
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

