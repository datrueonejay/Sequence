package com.datrueonejay.canyouescape;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Unlockables extends AppCompatActivity {

    public static Context cont;

    public static String[] howTo;
    public static String[] descriptions;
    public static String[] skin = new String[Skin.numSkins()];


    public static Boolean[] conditions = new Boolean[Skin.numSkins()];
    public static RelativeLayout[] pages = new RelativeLayout[Skin.numSkins()];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unlockables_view_pager);

        cont = this;

        // resumes music
        MainMenu.music.start();

        // keeps the app in portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // finds all the scores to check conditions
        int a = MainMenu.sp.getInt("main", 0);
        int b = MainMenu.sp.getInt("half", 0);
        int c = MainMenu.sp.getInt("one_minute", 0);
        int d = MainMenu.sp.getInt("one_half", 0);
        int e = MainMenu.sp.getInt("two_minutes", 0);
        int f = MainMenu.sp.getInt("three_minutes", 0);
        int g = MainMenu.sp.getInt("five", 9999);
        int h = MainMenu.sp.getInt("seven", 9999);
        int i = MainMenu.sp.getInt("ten", 9999);
        int k = MainMenu.sp.getInt("fifteen", 9999);



        if (MainMenu.sp.getBoolean("dev", false)){
            for (int count = 0; count < Skin.numSkins(); count++){
                conditions[count] = true;
            }
        }
        else{
            // sets condition to unlock
            conditions[0] = true;
            conditions[1] = a > 4 || b > 4 || c > 4 || d > 4 || e > 4 || f > 4;
            conditions[2] = a > 9 || b > 9 || c > 9 || d > 9 || e > 9 || f > 9;
            conditions[3] = a > 14;
            conditions[4] = a > 19;
            conditions[5] = b > 9 || c > 9 || d > 9 || e > 9 || f > 9;
            conditions[6] = b > 9 || c > 9 || d > 9 || e > 9 || f > 9;
            conditions[7] = g < 20;
            conditions[8] = h < 40;
        }


        // finds the strings for descriptions
        descriptions = getResources().getStringArray(R.array.arrows);
        // finds the strings for how to unlock
        howTo = getResources().getStringArray(R.array.howto);
        // the skins in the game
        skin[0] = "classic";
        skin[1] = "invert";
        skin[2] = "invisible";
        skin[3] = "simon";
        skin[4] = "pc";
        skin[5] = "xbox";
        skin[6] = "playstation";
        skin[7] = "cards";
        skin[8] = "ddr";

        // create the view pager adapter
        final MyPagerAdapter adapter = new MyPagerAdapter();
        // set up the view pager
        ViewPager myPager = (ViewPager) findViewById(R.id.pages);
        myPager.setAdapter(adapter);

        // create the pages
        for (int counter = 0; counter < Skin.numSkins(); counter ++){
            Skin skins = new Skin();
            skins.LoadSkin(Unlockables.skin[counter], Unlockables.cont);
            // find the current page
            RelativeLayout page = (RelativeLayout) getLayoutInflater().inflate(R.layout.unlockables_page, null);

            TextView title = (TextView) page.findViewById(R.id.title);
            title.getLayoutParams().height = MainMenu.screenHeight/20;
            title.setText(cont.getString(R.string.unlockables));

            // finds how to unlock and sets it for current one
            TextView how = (TextView) page.findViewById(R.id.how);
            how.getLayoutParams().height = MainMenu.screenHeight/12;
            how.setText(howTo[counter]);

            Button use = (Button) page.findViewById(R.id.use);

            // sets the locked button if it is locked
            if (!Unlockables.conditions[counter]){
                ImageView lock = (ImageView) page.findViewById(R.id.lock);
                lock.setBackground(cont.getResources().getDrawable(R.drawable.locked));
                use.setVisibility(View.INVISIBLE);
            }
            else {
                // sets size of mid space
                View mid = page.findViewById(R.id.mid);
                mid.getLayoutParams().width = MainMenu.screenWidth / 5;
                mid.getLayoutParams().height = MainMenu.screenWidth / 5;
                // sets the picture for the up button
                ImageView up = (ImageView) page.findViewById(R.id.upButton);
                up.setBackground(skins.GetUp());
                up.getLayoutParams().width = MainMenu.screenWidth / 5;
                up.getLayoutParams().height = MainMenu.screenWidth / 5;
                // sets the picture for the up button
                ImageView left = (ImageView) page.findViewById(R.id.leftButton);
                left.setBackground(skins.GetLeft());
                left.getLayoutParams().width = MainMenu.screenWidth / 5;
                left.getLayoutParams().height = MainMenu.screenWidth / 5;
                // sets the picture for the up button
                ImageView down = (ImageView) page.findViewById(R.id.downButton);
                down.setBackground(skins.GetDown());
                down.getLayoutParams().width = MainMenu.screenWidth / 5;
                down.getLayoutParams().height = MainMenu.screenWidth / 5;
                // sets the picture for the up button
                ImageView right = (ImageView) page.findViewById(R.id.rightButton);
                right.setBackground(skins.GetRight());
                right.getLayoutParams().width = MainMenu.screenWidth / 5;
                right.getLayoutParams().height = MainMenu.screenWidth / 5;
                // sets the picture for the up button
                ImageView correct = (ImageView) page.findViewById(R.id.correct);
                correct.setBackground(skins.GetCorrect());
                correct.getLayoutParams().width = MainMenu.screenWidth / 6;
                correct.getLayoutParams().height = MainMenu.screenWidth / 6;
                // sets the picture for the up button
                ImageView incorrect = (ImageView) page.findViewById(R.id.incorrect);
                incorrect.setBackground(skins.GetIncorrect());
                incorrect.getLayoutParams().width = MainMenu.screenWidth / 6;
                incorrect.getLayoutParams().height = MainMenu.screenWidth / 6;

                // find the use button for the current page
                use = (Button) page.findViewById(R.id.use);
                use.setVisibility(View.VISIBLE);
                if (MainMenu.sp.getString("skin", "classic").equals(Unlockables.skin[counter])){
                    use.setText(Unlockables.cont.getString(R.string.selected));
                    use.setEnabled(false);
                    use.setBackgroundColor(getResources().getColor(R.color.darkblue));
                }
                else {
                    use.setText(Unlockables.cont.getString(R.string.use));
                    use.setEnabled(true);
                    use.setBackgroundColor(getResources().getColor(R.color.blue));
                }

                // finds description and sets it for current one
                final TextView description = (TextView) page.findViewById(R.id.description);
                description.getLayoutParams().height = MainMenu.screenHeight/10;
                description.setText(Unlockables.descriptions[counter]);

                // copies the counter
                final int copy = counter;
                // set what happens when the button is clicked
                use.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN){
                            // says the new skin to be used
                            MainMenu.editor.putString("skin", Unlockables.skin[copy]);
                            MainMenu.editor.commit();
                            for (int counter = 0; counter < 9; counter ++){
                                Button use = (Button) pages[counter].findViewById(R.id.use);
                                if (counter == copy){
                                    use.setText(R.string.selected);
                                    use.setEnabled(false);
                                    use.setBackgroundColor(getResources().getColor(R.color.darkblue));
                                }
                                else {
                                    use.setText(R.string.use);
                                    use.setEnabled(true);
                                    use.setBackgroundColor(getResources().getColor(R.color.blue));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                        return false;
                    }
                });

            }
            pages[counter] = page;
        }

        myPager.setCurrentItem(0);

    }

    @Override
    protected void onPause(){
        super.onPause();
        SoundManager.stopPlayingDelayed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.continueMusic();
    }

}
