package com.datrueonejay.canyouescape;


import android.os.Handler;

public class SoundManager {

    static boolean play;
    static Handler handler = new Handler();

    public static void stopPlayingDelayed(){
        play = false;

        // waits 0.1 seconds and stops the music if a new activity has not been started again
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!play)
                    MainMenu.music.pause();
            }
        }, 100);
    }

    public static void continueMusic(){
        play = true;
        MainMenu.music.start();
    }

}
