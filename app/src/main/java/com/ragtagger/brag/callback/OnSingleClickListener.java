package com.ragtagger.brag.callback;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.SystemClock;
import android.view.View;

/**
 * Created by nikhil.vadoliya on 11-10-2017.
 */

/*
* Processing fast on a control double-click 2 times (or many times) will cause onClick to be triggered 2 times (or multiple times)
  Filter by judging the time interval of the second click event

  Subclasses respond to click events by implementing {@link #onSingleClick}

  */
public abstract class OnSingleClickListener implements View.OnClickListener {

    private static final long MIN_CLICK_INTERVAL=600;
    private long mLastClickTime;
    public abstract void onSingleClick(View v);

    @Override
    public final void onClick(View v) {
        long currentClickTime= SystemClock.uptimeMillis();
        long elapsedTime=currentClickTime-mLastClickTime;
        mLastClickTime=currentClickTime;

        if(elapsedTime<=MIN_CLICK_INTERVAL)
            return;

        onSingleClick(v);
    }
}
