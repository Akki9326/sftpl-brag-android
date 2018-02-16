package com.pulse.brag;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Activity;
import android.app.Application;

import com.pulse.brag.di.component.DaggerAppComponent;
import com.pulse.brag.utils.PreferencesManager;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class BragApp extends Application implements HasActivityInjector{

    static BragApp mInstance;
    public static int CartNumber = 0;
    public static int NotificationNumber = 0;

    public static boolean isProductViewAsList = false;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder().application(this).build().inject(this);
        mInstance = this;
        PreferencesManager.initializeInstance(mInstance);
    }

    public static synchronized BragApp getInstance() {
        return mInstance;
    }


    public static String getCartNumber() {
        if (CartNumber > 0 && CartNumber <= 99) {
            return "" + CartNumber;
        } else {
            return "99+";
        }
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
