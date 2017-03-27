package com.datrueonejay.canyouescape;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    public static Context context;
    Boolean firstTime;

    public final static DisplayMetrics dimensions = new DisplayMetrics();
    public static int screenWidth;
    public static int screenHeight;
    public static MediaPlayer music;

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;

    public static boolean musicOn;
    public static boolean soundsOn;
    public static boolean timedGame;
    public static boolean timedUpGame;

    public static String gameMode;

    public static double time;
    public static int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Dialog dialog = new Dialog(MainMenu.this);

        context = this;

        final Intent game = new Intent(MainMenu.this, MainActivity.class);
        final Intent settingActivity = new Intent(MainMenu.this, Settings.class);
        final Intent unlockablesActivity = new Intent(MainMenu.this, Unlockables.class);

        // find the dimensions of the screen
        this.getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        // find the width of the screen
        screenWidth = dimensions.widthPixels;
        // find the length of the screen
        screenHeight = dimensions.heightPixels;

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // create a shared preferences to save data
        sp = MainMenu.this.getPreferences(Context.MODE_PRIVATE);

        // create the editor for shared preferences
        editor = sp.edit();

        // find the background music track
        music = MediaPlayer.create(getApplicationContext(), R.raw.airport_lounge);

        // checks if volume is on
        musicOn = sp.getBoolean("musicOn", true);
        if (!musicOn ){
            music.setVolume(0, 0);
        }
        else {
            music.setVolume(0.75f, 0.75f);
        }

        // loops the music
        music.setLooping(true);
        music.start();

        // check if the in game sounds are on
        soundsOn = sp.getBoolean("soundsOn", true);

        // checks if this is the first time the app has been opened up
        firstTime = sp.getBoolean("firstTime", true);

        // shows instructions if first time
        if (firstTime){
            // set that it is no longer the first time
            editor.putBoolean("firstTime", false);
            editor.commit();

            dialog.setContentView(R.layout.activity_settings_instructions);

            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText(R.string.welcome);

            TextView info = (TextView) dialog.findViewById(R.id.info);
            info.getLayoutParams().height = MainMenu.screenHeight/4;
            info.setText(R.string.info);

            ImageView pic = (ImageView) dialog.findViewById(R.id.pic);
            pic.getLayoutParams().height = MainMenu.screenWidth/4;

            TextView closing = (TextView) dialog.findViewById(R.id.closing);
            closing.setText(R.string.next);

            dialog.show();
        }

        // gets relative layour one and two, second one contains texts
        RelativeLayout one = (RelativeLayout) findViewById(R.id.one);
        RelativeLayout two = (RelativeLayout) findViewById(R.id.two);

        // sets the title
        TextView title = (TextView) one.findViewById(R.id.title);
        title.getLayoutParams().height = screenHeight/8;
        title.setText(getResources().getString(R.string.title));

        TextView titleTwo = (TextView) two.findViewById(R.id.title_two);
        titleTwo.getLayoutParams().height = screenHeight/8;
        titleTwo.setText(getResources().getString(R.string.title));

        // finds and sets the text buttons of the menu
        Button main = (Button) one.findViewById(R.id.main);
        main.getLayoutParams().height = screenHeight/15;

        TextView mainText = (TextView) two.findViewById(R.id.main_text);
        mainText.getLayoutParams().height = screenHeight/15;
        mainText.setText(getResources().getString(R.string.main));

        Button timeAttack = (Button) one.findViewById(R.id.time);
        timeAttack.getLayoutParams().height = screenHeight/15;

        TextView timeAttackText = (TextView) two.findViewById(R.id.time_text);
        timeAttackText.getLayoutParams().height = screenHeight/15;
        timeAttackText.setText(getResources().getString(R.string.time));

        Button levels = (Button) one.findViewById(R.id.levels);
        levels.getLayoutParams().height = screenHeight/15;

        TextView levelsText = (TextView) two.findViewById(R.id.levels_text);
        levelsText.getLayoutParams().height = screenHeight/15;
        levelsText.setText(getString(R.string.levels));

        Button settings = (Button) one.findViewById(R.id.settings);
        settings.getLayoutParams().height = screenHeight/15;

        TextView settingsText = (TextView) two.findViewById(R.id.settings_text);
        settingsText.getLayoutParams().height = screenHeight/15;
        settingsText.setText(getResources().getString(R.string.settings));

        Button highscores = (Button) one.findViewById(R.id.highscores);
        highscores.getLayoutParams().height = screenHeight/15;

        TextView highscoresText = (TextView) two.findViewById(R.id.highscores_text);
        highscoresText.getLayoutParams().height = screenHeight/15;
        highscoresText.setText(getResources().getString(R.string.highscores));

        Button unlockables = (Button) one.findViewById(R.id.unlockables);
        unlockables.getLayoutParams().height = screenHeight/15;

        TextView unlockablesText = (TextView) two.findViewById(R.id.unlockables_text);
        unlockablesText.getLayoutParams().height = screenHeight/15;
        unlockablesText.setText(getResources().getString(R.string.unlockables));

        // button to start non timed game mode with colour change
        main.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                // makes the game a regular game
                timedGame = false;
                timedUpGame= false;
                gameMode = "main";
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startActivity(game);
                }
                super.onTouch(v, event);
                return false;
            }
        }) ;

        // button to start the timed game mode
        timeAttack.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // create a pop up box to choose how long they would like to play
                dialog.setContentView(R.layout.pick_time);
                TextView instruction = (TextView) dialog.findViewById(R.id.instructions);
                instruction.setText(getString(R.string.time_instructions));
                Button half = (Button) dialog.findViewById(R.id.half);
                half.setText(getString(R.string.half));
                Button one = (Button) dialog.findViewById(R.id.one);
                one.setText(getString(R.string.one));
                Button one_half = (Button) dialog.findViewById(R.id.one_half);
                one_half.setText(getString(R.string.one_half));
                Button two = (Button) dialog.findViewById(R.id.two);
                two.setText(getString(R.string.two));

                // makes the game a timed game mode
                timedGame = true;
                timedUpGame= false;

                half.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        time = 0.5;
                        gameMode = "half";
                        // create the timed game
                        if (event.getAction() == MotionEvent.ACTION_UP){
                            dialog.dismiss();
                            createDialog();
                        }
                        super.onTouch(v, event);
                        return false;
                    }
                });

                one.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        time = 1;
                        gameMode = "one_minute";
                        // create the timed game
                        if (event.getAction() == MotionEvent.ACTION_UP){
                            dialog.dismiss();
                            createDialog();
                        }
                        super.onTouch(v, event);
                        return false;
                    }
                });

                one_half.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        time = 1.5;
                        gameMode = "one_half";
                        // create the timed game
                        if (event.getAction() == MotionEvent.ACTION_UP){
                            dialog.dismiss();
                            createDialog();
                        }
                        super.onTouch(v, event);
                        return false;
                    }
                });

                two.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        time = 2;
                        gameMode = "two_minutes";
                        // create the timed game
                        if (event.getAction() == MotionEvent.ACTION_UP){
                            dialog.dismiss();
                            createDialog();
                        }
                        super.onTouch(v, event);
                        return false;
                    }
                });

                if (event.getAction() == MotionEvent.ACTION_UP){
                    dialog.show();
                }
                super.onTouch(v, event);
                return false;
            }
        });

        levels.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    dialog.setContentView(R.layout.pick_time);
                    TextView instruction = (TextView) dialog.findViewById(R.id.instructions);
                    instruction.setText(getString(R.string.level_instructions));
                    Button five = (Button) dialog.findViewById(R.id.half);
                    five.setText(getString(R.string.five));
                    Button seven = (Button) dialog.findViewById(R.id.one);
                    seven.setText(getString(R.string.seven));
                    Button ten = (Button) dialog.findViewById(R.id.one_half);
                    ten.setText(getString(R.string.ten));
                    Button fifteen = (Button) dialog.findViewById(R.id.two);
                    fifteen.setText(getString(R.string.fifteen));

                    dialog.show();
                    timedUpGame = true;
                    timedGame = false;

                    five.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gameMode = "five";
                            level = 5;
                            if (event.getAction() == MotionEvent.ACTION_UP){
                                dialog.dismiss();
                                createDialog();
                            }
                            super.onTouch(v, event);
                            return false;
                        }
                    });

                    seven.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gameMode = "seven";
                            level = 7;
                            if (event.getAction() == MotionEvent.ACTION_UP){
                                dialog.dismiss();
                                createDialog();
                            }
                            super.onTouch(v, event);
                            return false;
                        }
                    });

                    ten.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gameMode = "ten";
                            level = 10;
                            if (event.getAction() == MotionEvent.ACTION_UP){
                                dialog.dismiss();
                                createDialog();
                            }
                            super.onTouch(v, event);
                            return false;
                        }
                    });

                    fifteen.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gameMode = "fifteen";
                            level = 15;
                            if (event.getAction() == MotionEvent.ACTION_UP){
                                dialog.dismiss();
                                createDialog();
                            }
                            super.onTouch(v, event);
                            return false;
                        }
                    });
                }
                super.onTouch(v, event);
                return false;
            }
        });

        // set the settings button
        settings.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // create the settings activity
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startActivity(settingActivity);
                }
                super.onTouch(v, event);
                return false;
            }
        });

        // set the highscores button to show a pop up box of the scores
        highscores.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                // create the highscore screen
                Dialog dialog = new Dialog(MainMenu.this);
                dialog.setContentView(R.layout.activity_highscores);

                TextView title = (TextView) dialog.findViewById(R.id.title);
                TextView main_score = (TextView) dialog.findViewById(R.id.main_score);
                TextView timed = (TextView) dialog.findViewById(R.id.timed);
                TextView timed_half_score = (TextView) dialog.findViewById(R.id.timed_half_score);
                TextView timed_one_score = (TextView) dialog.findViewById(R.id.timed_one_score);
                TextView timed_one_half_score = (TextView) dialog.findViewById(R.id.timed_one_half_score);
                TextView timed_two_score = (TextView) dialog.findViewById(R.id.timed_two_score);
                TextView levels = (TextView) dialog.findViewById(R.id.levels);
                TextView five_score = (TextView) dialog.findViewById(R.id.five_score);
                TextView seven_score = (TextView) dialog.findViewById(R.id.seven_score);
                TextView ten_score = (TextView) dialog.findViewById(R.id.ten_score);
                TextView fifteen_score = (TextView) dialog.findViewById(R.id.fifteen_score);

                title.setText(getString(R.string.main));
                main_score.setText(getString(R.string.highest_level) + " " + getString (R.string.level) + " " + Integer.toString(MainMenu.sp.getInt("main", 0)));
                timed.setText(getString(R.string.timed));
                timed_half_score.setText(getString(R.string.half) +  ": " + getString (R.string.level) + " " +(Integer.toString(MainMenu.sp.getInt("half", 0))));
                timed_one_score.setText(getString(R.string.one) +  ": " + getString (R.string.level) + " " +(Integer.toString(MainMenu.sp.getInt("one_minute", 0))));
                timed_one_half_score.setText(getString(R.string.one_half) +  ": " + getString (R.string.level) + " " +(Integer.toString(MainMenu.sp.getInt("one_half", 0))));
                timed_two_score.setText(getString(R.string.two) +  ": " + getString (R.string.level) + " " +(Integer.toString(MainMenu.sp.getInt("two_minutes", 0))));
                levels.setText(getString(R.string.levels));
                five_score.setText(getString(R.string.five) + ": " + Integer.toString(MainMenu.sp.getInt("five", 9999)) + " seconds");
                seven_score.setText(getString(R.string.seven) + ": " + Integer.toString(MainMenu.sp.getInt("seven", 9999)) + " seconds");
                ten_score.setText(getString(R.string.ten) + ": " + Integer.toString(MainMenu.sp.getInt("ten", 9999)) + " seconds");
                fifteen_score.setText(getString(R.string.fifteen) + ": " + Integer.toString(MainMenu.sp.getInt("fifteen", 9999)) + " seconds");

                if (event.getAction() == MotionEvent.ACTION_UP){
                    dialog.show();
                }
                super.onTouch(v, event);
                return false;
            }

        });

        // set the unlockables button
        unlockables.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startActivity(unlockablesActivity);
                }
                super.onTouch(v, event);
                return false;
            }

        });
    }


    @Override
    protected void onStop(){
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        SoundManager.stopPlayingDelayed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.continueMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainMenu.music.stop();
        MainMenu.music.release();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void createDialog() {

        final Dialog countdownDialog = new Dialog(MainMenu.this);
        countdownDialog.setContentView(R.layout.activity_count_down);

        final CountDownTimer countdown;
        TextView begins;
        final TextView time;

        // sets the title
        begins = (TextView) countdownDialog.findViewById(R.id.title);
        begins.setText(getString(R.string.begins));

        time = (TextView) countdownDialog.findViewById(R.id.counter);
        final Intent game = new Intent(MainMenu.this, MainActivity.class);

        // sets the timer for 5 seconds
        countdown = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished/1000)==1){
                    startActivity(game);
                    countdownDialog.dismiss();
                }
                else{
                    time.setText(String.valueOf((millisUntilFinished/1000)-1));
                }
            }

            @Override
            public void onFinish() {

            }

        }.start();

        countdownDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                countdown.cancel();
            }
        });
        countdownDialog.show();
    }

}
