package com.pulse.brag.views.erecyclerview.pulltorefresh;

import android.view.View;

/**
 * Created by Emre Eran on 11/12/15.
 */
public interface RefreshViewStateListener {
    int STATE_IDLE = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onViewCreated(View view);

    void onSwipe(int state, float deltaY);

    void onReleaseToRefresh();

    void onRelease();

    void onRefreshing();

    void onComplete();

    void onReset();
}
