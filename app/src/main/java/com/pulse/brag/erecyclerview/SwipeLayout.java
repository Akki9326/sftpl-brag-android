package com.pulse.brag.erecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Emre Eran on 12/12/15.
 */
public abstract class SwipeLayout extends FrameLayout {

    protected int mState;
    protected View mView;
    protected int mMeasuredHeight;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void setVisibleHeight(int height) {
        if (height < 0) {
            height = 0;
        }

        LayoutParams layoutParams = (LayoutParams) mView.getLayoutParams();
        layoutParams.height = height;
        mView.setLayoutParams(layoutParams);
    }

    public int getVisibleHeight() {
        return ((LayoutParams) mView.getLayoutParams()).height;
    }

    public void setState(int state) {
        mState = state;
    }

    public int getState() {
        return mState;
    }
}
