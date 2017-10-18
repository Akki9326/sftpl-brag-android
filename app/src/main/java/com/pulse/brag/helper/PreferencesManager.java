package com.pulse.brag.helper;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesManager {

    private static final String PREF_NAME = "com.pulse.brag.PREF_NAME";
    private static PreferencesManager sInstance;

    private final SharedPreferences mPref;
    public String POST_QUERY_CONTENT_ID = "postQueryContentId";

    private static final String IS_LOGIN = "isLogin";
    public String SELECTED_CONTENT_CATEGORY_POSITION = "selectedContentPosition";


    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }


    public void setIsLogin(boolean isLogin) {
        mPref.edit().putBoolean(IS_LOGIN, isLogin).commit();
    }

    public boolean isLogin() {
        return mPref.getBoolean(IS_LOGIN, false);
    }

    public void setSELECTED_CONTENT_CATEGORY_POSITION(String value) {
        mPref.edit().putString(SELECTED_CONTENT_CATEGORY_POSITION, value).commit();
    }

    public String getPOST_QUERY_CONTENT_ID() {
        return mPref.getString(POST_QUERY_CONTENT_ID, "1");
    }

    public void setPOST_QUERY_CONTENT_ID(String value) {
        mPref.edit().putString(POST_QUERY_CONTENT_ID, value).commit();
    }

    public String getSELECTED_CONTENT_CATEGORY_POSITION() {
        return mPref.getString(SELECTED_CONTENT_CATEGORY_POSITION, "0");
    }


}
