package com.pulse.brag.erecyclerview.pulltorefresh;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.pulse.brag.erecyclerview.SwipeLayout;


/**
 * Base class for refresh views, all views used as pull to refresh items must extend this class
 * Created by Emre Eran on 10/12/15.
 */
public class SwipeRefreshHeaderView extends SwipeLayout {

    protected OnRefreshListener mOnRefreshListener;
    private RefreshViewStateListener mLayoutListener;

    public SwipeRefreshHeaderView(Context context) {
        super(context);
    }

    public void onRefresh() {
        setState(RefreshViewStateListener.STATE_REFRESHING);
        mLayoutListener.onRefreshing();
    }

    public SwipeRefreshHeaderView createView(int resourceId, @Nullable RefreshViewStateListener listener) {
        mView = LayoutInflater.from(getContext()).inflate(resourceId, this, false);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);
        addView(mView);
        measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mMeasuredHeight = getMeasuredHeight();
        mState = RefreshViewStateListener.STATE_IDLE;
        mLayoutListener = listener;

        if (mLayoutListener != null) {
            mLayoutListener.onViewCreated(mView);
        }
        setVisibleHeight(0);
        return this;
    }

    public void onComplete() {
        setState(RefreshViewStateListener.STATE_DONE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                smoothScrollTo(0);
                onReset();
            }
        }, 500);
        if (mLayoutListener != null) {
            mLayoutListener.onComplete();
        }
    }

    public void onSwipe(float delta) {
        if (mState != RefreshViewStateListener.STATE_REFRESHING) {
            if (getVisibleHeight() > 0 || delta > 0) {
                setVisibleHeight((int) delta + getVisibleHeight());
                // Refresh header height is past refresh threshold, release will result on refresh
                if (mState == RefreshViewStateListener.STATE_IDLE && getVisibleHeight() >= mMeasuredHeight) {
                    onReleaseToRefresh();
                    // Refresh header height is below refresh threshold, release will result on header view collapsing
                } else if (mState == RefreshViewStateListener.STATE_RELEASE_TO_REFRESH && getVisibleHeight() < mMeasuredHeight) {
                    onReset();
                }
            }
        }
        if (mLayoutListener != null) {
            mLayoutListener.onSwipe(mState, delta);
        }
    }

    public void onReleaseToRefresh() {
        setState(RefreshViewStateListener.STATE_RELEASE_TO_REFRESH);
        mLayoutListener.onReleaseToRefresh();
    }

    public void onRelease() {
        int destHeight;
        // Check if is past refresh start threshold
        if (getVisibleHeight() > mMeasuredHeight && mState < RefreshViewStateListener.STATE_REFRESHING) {
            onRefresh();
            if (mOnRefreshListener != null) {
                mOnRefreshListener.onRefresh();
            }
        }

        if (mState == RefreshViewStateListener.STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        } else {
            destHeight = 0;
        }
        smoothScrollTo(destHeight);
        if (mLayoutListener != null) {
            mLayoutListener.onRelease();
        }
    }

    public void onReset() {
        setState(RefreshViewStateListener.STATE_IDLE);
        if (mLayoutListener != null) {
            mLayoutListener.onReset();
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }
}
