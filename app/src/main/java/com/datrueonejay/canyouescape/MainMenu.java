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
    public static boolean timeAttackMode;
    public static boolean beatTheClockMode;

    public static String gameMode;

    public static double time;
    public static int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        // find the dimensions of the screen
        this.getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        // find the width of the screen
        screenWidth = dimensions.widthPixels;
        // find the length of the screen
        screenHeight = dimensions.heightPixels;
        // sets padding
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_main_menu);
        layout.setPadding(screenWidth/27, screenWidth/27, screenWidth/27, screenWidth/27);
        // base dialog for any button pressed
        final Dialog dialog = new Dialog(MainMenu.this);
        context = this;
        // create different activities that can be launched
        final Intent game = new Intent(MainMenu.this, MainActivity.class);
        final Intent settingActivity = new Intent(MainMenu.this, Settings.class);
        final Intent unlockablesActivity = new Intent(MainMenu.this, Unlockables.class);
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
            // show beginning screen
            dialog.setContentView(R.layout.activity_settings_instructions);
            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.getLayoutParams().height = MainMenu.screenHeight/22;
            title.setPadding(0, MainMenu.screenWidth/81, 0, MainMenu.screenWidth/81);
            RelativeLayout.LayoutParams titleParams = (RelativeLayout.LayoutParams) title.getLayoutParams();
            titleParams.setMargins(0, 0, 0, MainMenu.screenWidth/27);
            title.setText(R.string.welcome);
            TextView info = (TextView) dialog.findViewById(R.id.info);
            RelativeLayout.LayoutParams infoParams = (RelativeLayout.LayoutParams) info.getLayoutParams();
            infoParams.setMargins(0, 0, 0, MainMenu.screenWidth/54);
            info.getLayoutParams().height = MainMenu.screenHeight/5;
            info.setPadding(MainMenu.screenWidth/27, 0, MainMenu.screenWidth/27, 0);
            info.setText(R.string.info);
            ImageView pic = (ImageView) dialog.findViewById(R.id.pic);
            pic.getLayoutParams().width = MainMenu.screenWidth/2;
            pic.getLayoutParams().height = MainMenu.screenHeight/5;
            RelativeLayout.LayoutParams picParams = (RelativeLayout.LayoutParams) pic.getLayoutParams();
            picParams.setMargins(0, 0, 0, MainMenu.screenWidth/54);
            TextView closing = (TextView) dialog.findViewById(R.id.closing);
            closing.getLayoutParams().height = MainMenu.screenHeight/15;
            closing.setPadding(MainMenu.screenWidth/27, 0, MainMenu.screenWidth/27, 0);
            RelativeLayout.LayoutParams closingParams = (RelativeLayout.LayoutParams) closing.getLayoutParams();
            closingParams.setMargins(0, 0, 0, MainMenu.screenWidth/27);
            closing.setText(R.string.next);
            dialog.show();
        }
        // sets the title
        TextView title = (TextView) findViewById(R.id.title);
        title.getLayoutParams().height = screenHeight/10;
        RelativeLayout.LayoutParams titleParams = (RelativeLayout.LayoutParams) title.getLayoutParams();
        titleParams.setMargins(0, 0, 0, screenHeight/35);
        title.setPadding( MainMenu.screenWidth/135, MainMenu.screenWidth/135,  MainMenu.screenWidth/135, MainMenu.screenWidth/135);
        title.setText(getResources().getString(R.string.app_name));
        // finds and sets the text buttons of the menu
        Button main = (Button) findViewById(R.id.main);
        main.getLayoutParams().height = screenHeight/15;
        RelativeLayout.LayoutParams mainParams = (RelativeLayout.LayoutParams) main.getLayoutParams();
        mainParams.setMargins(0, 0, 0, screenHeight/35);
        TextView mainText = (TextView) findViewById(R.id.main_text);
        mainText.getLayoutParams().height = screenHeight/15;
        RelativeLayout.LayoutParams mainTextParams = (RelativeLayout.LayoutParams) mainText.getLayoutParams();
        mainTextParams.setMargins(0, 0, 0, screenHeight/35);
        mainText.setPadding(0, screenHeight/60, 0, screenHeight/60);
        mainText.setText(getResources().getString(R.string.main));
        Button timeAttack = (Button) findViewById(R.id.time);
        timeAttack.getLayoutParams().height = screenHeight/15;
        final RelativeLayout.LayoutParams timeAttackParams = (RelativeLayout.LayoutParams) timeAttack.getLayoutParams();
        timeAttackParams.setMargins(0, 0, 0, screenHeight/35);
        TextView timeAttackText = (TextView) findViewById(R.id.time_text);
        timeAttackText.getLayoutParams().height = screenHeight/15;
        timeAttackText.setPadding(0, screenHeight/60, 0, screenHeight/60);
        RelativeLayout.LayoutParams timeAttackTextParams = (RelativeLayout.LayoutParams) timeAttackText.getLayoutParams();
        timeAttackTextParams.setMargins(0, 0, 0, screenHeight/35);
        timeAttackText.setText(getResources().getString(R.string.time));
        Button beatTheClock = (Button) findViewById(R.id.levels);
        beatTheClock.getLayoutParams().height = screenHeight/15;
        RelativeLayout.LayoutParams beatTheClockParams = (RelativeLayout.LayoutParams) beatTheClock.getLayoutParams();
        beatTheClockParams.setMargins(0, 0, 0, screenHeight/35);
        TextView beatTheClockText = (TextView) findViewById(R.id.levels_text);
        beatTheClockText.getLayoutParams().height = screenHeight/15;
        beatTheClockText.setPadding(0, screenHeight/60, 0, screenHeight/60);
        RelativeLayout.LayoutParams beatTheClockTextParams = (RelativeLayout.LayoutParams) beatTheClockText.getLayoutParams();
        beatTheClockTextParams.setMargins(0, 0, 0, screenHeight/35);
        beatTheClockText.setText(getString(R.string.levels));
        Button settings = (Button) findViewById(R.id.settings);
        settings.getLayoutParams().height = screenHeight/15;
        RelativeLayout.LayoutParams settingsParams = (RelativeLayout.LayoutParams) settings.getLayoutParams();
        settingsParams.setMargins(0, 0, 0, screenHeight/35);
        TextView settingsText = (TextView) findViewById(R.id.settings_text);
        settingsText.getLayoutParams().height = screenHeight/15;
        settingsText.setPadding(0, screenHeight/60, 0, screenHeight/60);
        RelativeLayout.LayoutParams settingsTextParams = (RelativeLayout.LayoutParams) settingsText.getLayoutParams();
        settingsTextParams.setMargins(0, 0, 0, screenHeight/35);
        settingsText.setText(getResources().getString(R.string.settings));
        Button highscores = (Button) findViewById(R.id.highscores);
        highscores.getLayoutParams().height = screenHeight/15;
        RelativeLayout.LayoutParams highscoresParams = (RelativeLayout.LayoutParams) highscores.getLayoutParams();
        highscoresParams.setMargins(0, 0, 0, screenHeight/35);
        TextView highscoresText = (TextView) findViewById(R.id.highscores_text);
        highscoresText.getLayoutParams().height = screenHeight/15;
        highscoresText.setPadding(0, screenHeight/60, 0, screenHeight/60);
        RelativeLayout.LayoutParams highscoresTextParams = (RelativeLayout.LayoutParams) highscoresText.getLayoutParams();
        highscoresTextParams.setMargins(0, 0, 0, screenHeight/35);
        highscoresText.setText(getResources().getString(R.string.highscores));
        Button unlockables = (Button) findViewById(R.id.unlockables);
        unlockables.getLayoutParams().height = screenHeight/15;
        RelativeLayout.LayoutParams unlockablesParams = (RelativeLayout.LayoutParams) unlockables.getLayoutParams();
        unlockablesParams.setMargins(0, 0, 0, screenHeight/35);
        TextView unlockablesText = (TextView) findViewById(R.id.unlockables_text);
        unlockablesText.getLayoutParams().height = screenHeight/15;
        unlockablesText.setPadding(0, screenHeight/60, 0, screenHeight/60);
        RelativeLayout.LayoutParams unlockablesTextParams = (RelativeLayout.LayoutParams) unlockablesText.getLayoutParams();
        unlockablesTextParams.setMargins(0, 0, 0, screenHeight/35);
        unlockablesText.setText(getResources().getString(R.string.unlockables));
        // button to start non timed game mode
        main.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                // makes the game a regular game
                timeAttackMode = false;
                beatTheClockMode = false;
                gameMode = "main";
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startActivity(game);
                }
                super.onTouch(v, event);
                return false;
            }
        }) ;
        // button to start the timed attack game mode
        timeAttack.setOnTouchListener(new MyCustomButton.ButtonTouchEvent(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // create a pop up box to choose how long they would like to play
                dialog.setContentView(R.layout.pick_time);
                TextView instruction = (TextView) dialog.findViewById(R.id.instructions);
                instruction.getLayoutParams().height = screenHeight / 10;
                instruction.setText(getString(R.string.time_instructions));
                Button half = (Button) dialog.findViewById(R.id.half);
                half.getLayoutParams().height = screenHeight / 12;
                TextView halfText = (TextView) dialog.findViewById(R.id.half_text);
                halfText.getLayoutParams().height = screenHeight / 12;
                halfText.setPadding(0, screenHeight/36, 0, screenHeight/36);
                halfText.setText(getString(R.string.half));
                Button one = (Button) dialog.findViewById(R.id.one);
                one.getLayoutParams().height = screenHeight / 12;
                TextView oneText = (TextView) dialog.findViewById(R.id.one_text);
                oneText.getLayoutParams().height = screenHeight / 12;
                oneText.setPadding(0, screenHeight/36, 0, screenHeight/36);
                oneText.setText(getString(R.string.one));
                Button oneHalf = (Button) dialog.findViewById(R.id.one_half);
                oneHalf.getLayoutParams().height = screenHeight / 12;
                TextView oneHalfText = (TextView) dialog.findViewById(R.id.one_half_text);
                oneHalfText.getLayoutParams().height = screenHeight / 12;
                oneHalfText.setPadding(0, screenHeight/36, 0, screenHeight/36);
                oneHalfText.setText(getString(R.string.one_half));
                Button two = (Button) dialog.findViewById(R.id.two);
                two.getLayoutParams().height = screenHeight / 12;
                TextView twoText = (TextView) dialog.findViewById(R.id.two_text);
                twoText.getLayoutParams().height = screenHeight / 12;
                twoText.setPadding(0, screenHeight/36, 0, screenHeight/36);
                twoText.setText(getString(R.string.two));
                // makes the game a time attack mode
                timeAttackMode = true;
                beatTheClockMode = false;
                // set time chosen
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
                oneHalf.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        time = 1.5;
                        gameMode = "oneHalf";
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
        // set button for beat the clock game mdoe
        beatTheClock.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    dialog.setContentView(R.layout.pick_time);
                    TextView instruction = (TextView) dialog.findViewById(R.id.instructions);
                    instruction.getLayoutParams().height = screenHeight / 10;
                    instruction.setText(getString(R.string.level_instructions));
                    Button five = (Button) dialog.findViewById(R.id.half);
                    five.getLayoutParams().height = screenHeight / 12;
                    TextView fiveText = (TextView) dialog.findViewById(R.id.half_text);
                    fiveText.getLayoutParams().height = screenHeight / 12;
                    fiveText.setPadding(0, screenHeight/36, 0, screenHeight/36);
                    fiveText.setText(getString(R.string.five));
                    Button seven = (Button) dialog.findViewById(R.id.one);
                    seven.getLayoutParams().height = screenHeight / 12;
                    TextView sevenText = (TextView) dialog.findViewById(R.id.one_text);
                    sevenText.getLayoutParams().height = screenHeight / 12;
                    sevenText.setPadding(0, screenHeight/36, 0, screenHeight/36);
                    sevenText.setText(getString(R.string.seven));
                    Button ten = (Button) dialog.findViewById(R.id.one_half);
                    ten.getLayoutParams().height = screenHeight / 12;
                    TextView tenText = (TextView) dialog.findViewById(R.id.one_half_text);
                    tenText.getLayoutParams().height = screenHeight / 12;
                    tenText.setPadding(0, screenHeight/36, 0, screenHeight/36);
                    tenText.setText(getString(R.string.ten));
                    Button fifteen = (Button) dialog.findViewById(R.id.two);
                    fifteen.getLayoutParams().height = screenHeight / 12;
                    TextView fifteenText = (TextView) dialog.findViewById(R.id.two_text);
                    fifteenText.getLayoutParams().height = screenHeight / 12;
                    fifteenText.setPadding(0, screenHeight/36, 0, screenHeight/36);
                    fifteenText.setText(getString(R.string.fifteen));
                    dialog.show();
                    beatTheClockMode = true;
                    timeAttackMode = false;
                    // pick max level
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
                title.getLayoutParams().height = MainMenu.screenHeight/27;
                title.setPadding(0, MainMenu.screenWidth/135, 0, MainMenu.screenWidth/135);
                RelativeLayout.LayoutParams titleParams = (RelativeLayout.LayoutParams) title.getLayoutParams();
                titleParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView mainScore = (TextView) dialog.findViewById(R.id.main_score);
                mainScore.getLayoutParams().height = MainMenu.screenHeight/40;
                RelativeLayout.LayoutParams mainScoreParams = (RelativeLayout.LayoutParams) mainScore.getLayoutParams();
                mainScoreParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView timed = (TextView) dialog.findViewById(R.id.timed);
                timed.getLayoutParams().height = MainMenu.screenHeight/27;
                timed.setPadding(0, MainMenu.screenWidth/135, 0, MainMenu.screenWidth/135);
                RelativeLayout.LayoutParams timedParams = (RelativeLayout.LayoutParams) timed.getLayoutParams();
                timedParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView timedHalfScore = (TextView) dialog.findViewById(R.id.timed_half_score);
                timedHalfScore.getLayoutParams().height = MainMenu.screenHeight/40;
                RelativeLayout.LayoutParams timedHalfScoreParams = (RelativeLayout.LayoutParams) timedHalfScore.getLayoutParams();
                timedHalfScoreParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView timedOneScore = (TextView) dialog.findViewById(R.id.timed_one_score);
                timedOneScore.getLayoutParams().height = MainMenu.screenHeight/40;
                RelativeLayout.LayoutParams timedOneScoreParams = (RelativeLayout.LayoutParams) timedOneScore.getLayoutParams();
                timedOneScoreParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView timedOneHalfScore = (TextView) dialog.findViewById(R.id.timed_one_half_score);
                timedOneHalfScore.getLayoutParams().height = MainMenu.screenHeight/40;
                RelativeLayout.LayoutParams timedOneHalfScoreParams = (RelativeLayout.LayoutParams) timedOneHalfScore.getLayoutParams();
                timedOneHalfScoreParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView timedTwoScore = (TextView) dialog.findViewById(R.id.timed_two_score);
                timedTwoScore.getLayoutParams().height = MainMenu.screenHeight/40;
                RelativeLayout.LayoutParams timedTwoScoreParams = (RelativeLayout.LayoutParams) timedTwoScore.getLayoutParams();
                timedTwoScoreParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView beatTheClock = (TextView) dialog.findViewById(R.id.levels);
                beatTheClock.getLayoutParams().height = MainMenu.screenHeight/27;
                beatTheClock.setPadding(0, MainMenu.screenWidth/135, 0, MainMenu.screenWidth/135);
                RelativeLayout.LayoutParams beatTheClockParams = (RelativeLayout.LayoutParams) beatTheClock.getLayoutParams();
                beatTheClockParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView fiveScore = (TextView) dialog.findViewById(R.id.five_score);
                fiveScore.getLayoutParams().height = MainMenu.screenHeight/40;
                RelativeLayout.LayoutParams fiveScoreParams = (RelativeLayout.LayoutParams) fiveScore.getLayoutParams();
                fiveScoreParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView sevenScore = (TextView) dialog.findViewById(R.id.seven_score);
                sevenScore.getLayoutParams().height = MainMenu.screenHeight/40;
                RelativeLayout.LayoutParams sevenScoreParams = (RelativeLayout.LayoutParams) sevenScore.getLayoutParams();
                sevenScoreParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView tenScore = (TextView) dialog.findViewById(R.id.ten_score);
                tenScore.getLayoutParams().height = MainMenu.screenHeight/40;
                RelativeLayout.LayoutParams tenScoreParams = (RelativeLayout.LayoutParams) tenScore.getLayoutParams();
                tenScoreParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                TextView fifteenScore = (TextView) dialog.findViewById(R.id.fifteen_score);
                fifteenScore.getLayoutParams().height = MainMenu.screenHeight/40;
                RelativeLayout.LayoutParams fifteenScoreParams = (RelativeLayout.LayoutParams) fifteenScore.getLayoutParams();
                fifteenScoreParams.setMargins(0, 0, 0, MainMenu.screenHeight/50);
                title.setText(getString(R.string.main));
                mainScore.setText(getString(R.string.highest_level) + " " + getString (R.string.level) + " " + Integer.toString(MainMenu.sp.getInt("main", 0)));
                timed.setText(getString(R.string.timed));
                timedHalfScore.setText(getString(R.string.half) +  ": " + getString (R.string.level) + " " +(Integer.toString(MainMenu.sp.getInt("half", 0))));
                timedOneScore.setText(getString(R.string.one) +  ": " + getString (R.string.level) + " " +(Integer.toString(MainMenu.sp.getInt("one_minute", 0))));
                timedOneHalfScore.setText(getString(R.string.one_half) +  ": " + getString (R.string.level) + " " +(Integer.toString(MainMenu.sp.getInt("oneHalf", 0))));
                timedTwoScore.setText(getString(R.string.two) +  ": " + getString (R.string.level) + " " +(Integer.toString(MainMenu.sp.getInt("two_minutes", 0))));
                beatTheClock.setText(getString(R.string.levels));
                fiveScore.setText(getString(R.string.five) + ": " + Integer.toString(MainMenu.sp.getInt("five", 999)) + " seconds");
                sevenScore.setText(getString(R.string.seven) + ": " + Integer.toString(MainMenu.sp.getInt("seven", 999)) + " seconds");
                tenScore.setText(getString(R.string.ten) + ": " + Integer.toString(MainMenu.sp.getInt("ten", 999)) + " seconds");
                fifteenScore.setText(getString(R.string.fifteen) + ": " + Integer.toString(MainMenu.sp.getInt("fifteen", 999)) + " seconds");
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

    // used to create the countdown dialog before time attack and beat the clock
    private void createDialog() {
        final Dialog countdownDialog = new Dialog(MainMenu.this);
        countdownDialog.setContentView(R.layout.activity_count_down);
        RelativeLayout layout = (RelativeLayout) countdownDialog.findViewById(R.id.activity_count_down);
        layout.setPadding(MainMenu.screenWidth/27, MainMenu.screenWidth/27, MainMenu.screenWidth/27, MainMenu.screenWidth/27);
        final CountDownTimer countdown;
        TextView begins;
        final TextView time;
        // sets the title
        begins = (TextView) countdownDialog.findViewById(R.id.title);
        begins.getLayoutParams().height = screenHeight/7;
        RelativeLayout.LayoutParams beginsParams = (RelativeLayout.LayoutParams) begins.getLayoutParams();
        beginsParams.setMargins(0, 0, 0, screenWidth/27);
        begins.setText(getString(R.string.begins));
        time = (TextView) countdownDialog.findViewById(R.id.counter);
        time.getLayoutParams().height = screenHeight/10;
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
