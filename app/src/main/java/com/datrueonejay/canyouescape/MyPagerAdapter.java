package com.datrueonejay.canyouescape;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyPagerAdapter extends PagerAdapter{

    LinearLayout layout;
    Button use;

    public int getCount() {
        return 9;
    }

    public Object instantiateItem(View collection, final int position) {

        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        Skin skins = new Skin();
        skins.LoadSkin(Unlockables.skin[position], Unlockables.cont);
        // find the current page
        final RelativeLayout page = (RelativeLayout) inflater.inflate(R.layout.unlockables_page, null);

        //page.getLayoutParams().width = MainMenu.screenWidth;
        TextView title = (TextView) page.findViewById(R.id.title);
        title.setText(Unlockables.cont.getString(R.string.unlockables));

        // finds how to unlock and sets it for current one
        TextView how = (TextView) page.findViewById(R.id.how);
        how.setText(Unlockables.howTo[position]);

        // sets the locked button if it is locked
        if (!Unlockables.conditions[position]){
            ImageView lock = (ImageView) page.findViewById(R.id.lock);
            lock.setBackground(Unlockables.cont.getResources().getDrawable(R.drawable.locked));
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
            use.setText(Unlockables.cont.getString(R.string.use));
            use.setVisibility(View.VISIBLE);
            if (MainMenu.sp.getString("skin", "classic").equals(Unlockables.skin[position])){
                use.setEnabled(false);
                use.setVisibility(View.INVISIBLE);
            }

            // finds description and sets it for current one
            final TextView description = (TextView) page.findViewById(R.id.description);
            description.getLayoutParams().height = MainMenu.screenHeight/8;
            description.setText(Unlockables.descriptions[position]);

            // set what happens when the button is clicked
            use.setOnTouchListener(new MyCustomButton.ButtonTouchEvent() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN){
                        // says the new skin to be used
                        MainMenu.editor.putString("skin", Unlockables.skin[position]);
                        MainMenu.editor.commit();
                        // hide the current button
                        use.setVisibility(View.INVISIBLE);
                        notifyDataSetChanged();
                    }
                    return false;
                }
            });

        }



        ((ViewPager) collection).addView(page, 0);

        return page;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
