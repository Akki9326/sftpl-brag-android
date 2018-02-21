package com.pulse.brag.views.erecyclerview.loadmore;

import android.view.View;

/**
 * Created by Emre Eran on 12/12/15.
 */
public interface LoadMoreViewStateListener {
    void onViewCreated(View view);

    void onLoad();

    void onComplete();
}
