package com.ragtagger.brag;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Activity;
import android.app.Application;

import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.google.firebase.FirebaseApp;
import com.ragtagger.brag.di.component.AppComponent;
import com.ragtagger.brag.di.component.DaggerAppComponent;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class BragApp extends Application implements HasActivityInjector {

    static BragApp mInstance;
    public static int CartNumber = 0;
    public static int NotificationNumber = 0;

    public HashMap<String, String> mMapSizeGuide;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder().application(this).build().inject(this);
        mInstance = this;
        //PreferencesManager.initializeInstance(mInstance);

        // Enabling database for resume support even after the application is killed:
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);

        FirebaseApp.initializeApp(this);

    }

    public static synchronized BragApp getInstance() {
        return mInstance;
    }

    public String getSizeGuide(String key) {
        if (mMapSizeGuide != null && mMapSizeGuide.containsKey(key))
            return mMapSizeGuide.get(key);

        else return null;
    }

    public void setMapSizeGuide(String key, String value) {
        if (mMapSizeGuide == null) {
            mMapSizeGuide = new HashMap<>();
            mMapSizeGuide.put(key, value);
        } else if (!mMapSizeGuide.containsKey(key))
            mMapSizeGuide.put(key, value);
    }


    public AppComponent getAppComponent() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
