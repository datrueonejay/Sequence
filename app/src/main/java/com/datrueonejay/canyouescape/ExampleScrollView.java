package com.datrueonejay.canyouescape;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ExampleScrollView extends HorizontalScrollView {
    public ExampleScrollView(Context context) {
        super(context);
    }

    public ExampleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExampleScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private OnScrollViewListener mOnScrollViewListener;

    public void setOnScrollViewListener(OnScrollViewListener l) {
        this.mOnScrollViewListener = l;
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        mOnScrollViewListener.onScrollChanged( this, l, t, oldl, oldt );
        super.onScrollChanged( l, t, oldl, oldt );
    }

    public interface OnScrollViewListener {
        void onScrollChanged( ExampleScrollView v, int l, int t, int oldl, int oldt );
    }
}
