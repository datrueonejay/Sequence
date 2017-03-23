package com.datrueonejay.canyouescape;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

public class MyPagerAdapter extends PagerAdapter{

    View views;
    int viewNum;

    public int getCount() {
        return 9;
    }

    public Object instantiateItem(View collection, final int position) {
        RelativeLayout page = Unlockables.pages[position];
        ((ViewPager) collection).addView(page, 0);
        this.views = collection;
        this.viewNum = position;
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

    public void restart(){
        views.invalidate();
        notifyDataSetChanged();
    }
}
