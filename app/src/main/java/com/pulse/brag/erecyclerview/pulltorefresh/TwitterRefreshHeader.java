package com.pulse.brag.erecyclerview.pulltorefresh;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pulse.brag.R;


/**
 * Created by Emre Eran on 16/12/15.
 */
public class TwitterRefreshHeader {
    public static int getResource() {
        return R.layout.layout_twitter_header;
    }

    public static RefreshViewStateListener getListener() {
        return new RefreshViewStateListener() {
            private ImageView ivArrow;

            private ImageView ivSuccess;

            private TextView tvRefresh;

            private ProgressBar progressBar;

            private Animation rotateUp;

            private Animation rotateDown;

            @Override
            public void onViewCreated(View view) {
                rotateUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_up);
                rotateDown = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_down);
                tvRefresh = (TextView) view.findViewById(R.id.tvRefresh);
                ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
                ivSuccess = (ImageView) view.findViewById(R.id.ivSuccess);
                progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
                progressBar.setVisibility(View.GONE);
                ivSuccess.setVisibility(View.GONE);
                ivArrow.setVisibility(View.GONE);
            }

            @Override
            public void onSwipe(int state, float deltaY) {
                if (state != STATE_REFRESHING) {
                    ivArrow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReleaseToRefresh() {
                ivArrow.clearAnimation();
                ivArrow.startAnimation(rotateUp);
                tvRefresh.setText("RELEASE TO REFRESH");
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onRefreshing() {
                ivSuccess.setVisibility(View.GONE);
                ivArrow.clearAnimation();
                ivArrow.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tvRefresh.setText("REFRESHING");
            }

            @Override
            public void onComplete() {
                ivSuccess.setVisibility(View.VISIBLE);
                ivArrow.clearAnimation();
                ivArrow.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                tvRefresh.setText("COMPLETE");
            }

            @Override
            public void onReset() {
                ivSuccess.setVisibility(View.GONE);
                ivArrow.clearAnimation();
                ivArrow.startAnimation(rotateDown);
                progressBar.setVisibility(View.GONE);
                tvRefresh.setText("SWIPE TO REFRESH");
            }
        };
    }
}
