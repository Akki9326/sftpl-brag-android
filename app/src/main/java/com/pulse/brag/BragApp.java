package com.pulse.brag;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Application;

import com.pulse.brag.helper.PreferencesManager;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class BragApp extends Application {

    static BragApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        PreferencesManager.initializeInstance(mInstance);
    }

    public static synchronized BragApp getInstance() {
        return mInstance;
    }
}
